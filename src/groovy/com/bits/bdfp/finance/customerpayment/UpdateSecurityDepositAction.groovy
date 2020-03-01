package com.bits.bdfp.finance.customerpayment

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.accounts.Mushak
import com.bits.bdfp.accounts.SubLedger
import com.bits.bdfp.finance.CustomerPayment
import com.bits.bdfp.finance.CustomerPaymentService
import com.bits.bdfp.finance.SecurityDeposit
import com.bits.bdfp.finance.WithdrawSecurityDeposit
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

/**
 * Created by liton.miah on 1/18/2016.
 */

@Component("updateSecurityDepositAction")
class UpdateSecurityDepositAction extends Action {
    private final String MESSAGE_HEADER = 'Update Security Deposit'
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerPaymentService customerPaymentService
    Message message

    @Autowired
    SessionFactory sessionFactory

    public Object preCondition(Object params, Object object) {
        try{
            Map map = (Map) object
            SecurityDeposit securityDeposit = map.get("securityDeposit")
            CustomerPayment customerPayment = map.get("customerPayment")
            //List<SubLedger> subLedgers = map.get("subLedgers")
            Journal journalHead=map.get("journalHead")
            List<JournalDetails> journalDetails = map.get("journalDetailsList")
            boolean isValidate = true

            if(journalDetails.size() > 0){
                journalDetails.each {
                    if (!it.validate()) {
                        isValidate = false
                        message = this.getValidationErrorMessage(it)
                    }
                }
            }
//
//            if(subLedgers.size() > 0){
//                subLedgers.each {
//                    if (!it.validate()) {
//                        isValidate = false
//                        message = this.getValidationErrorMessage(it)
//                    }
//                }
//            }


            if (!journalHead.validate()) {
                isValidate = false
                message = this.getValidationErrorMessage(journalHead)
            }

            if (!customerPayment.validate()) {
                isValidate = false
                message = this.getValidationErrorMessage(customerPayment)
            }

            if (!securityDeposit.validate()) {
                isValidate = false
                message = this.getValidationErrorMessage(securityDeposit)
            }

            if(isValidate){
                message = this.getMessage("Security Deposit", Message.SUCCESS, SUCCESS_SAVE)
            }

            return message
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object execute(Object params, Object object) {
        try {
            Float updateAmount = Float.parseFloat(params?.updateAmount)
            Long depositId = Long.parseLong(params?.depositId)

            ApplicationUser applicationUser = (ApplicationUser) object

            SecurityDeposit securityDeposit = SecurityDeposit.read(depositId)
            securityDeposit.deposited=updateAmount


            CustomerPayment customerPayment = CustomerPayment.read(securityDeposit.customerPayment.id)
            customerPayment.amount=updateAmount
            customerPayment.dateUpdated=new Date()
            customerPayment.userUpdated=applicationUser

            EnterpriseConfiguration enterpriseConfiguration = EnterpriseConfiguration.read(customerPayment.customerMaster.enterpriseConfiguration.id)

            Date dateNow = new Date()
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
            String currentDay = formatDay.format(dateNow)


            /***************************** Inactive journal  ***************************/
       // try {
            //List<Journal> listExistJournal = Journal.findAllByTransactionNoAndTableName(customerPayment.transNo,sessionFactory.getClassMetadata(CustomerPayment).tableName)
            List<Journal> listExistJournal = Journal.findAllByTransactionNoAndModuleName(customerPayment.transNo,ApplicationConstants.MODULE_WITHDRAW_SECURITY_DEPOSIT)

//            if(listExistJournal.count()>0)
                listExistJournal.each { journal ->
                    List<JournalDetails> listJournalDetailsExisting=JournalDetails.findAllByJournal( journal).toList()
                        listJournalDetailsExisting.each { journalDetails ->
                            journalDetails.delete()
                }
                journal.delete()
            }
//
//        }
//        catch(Exception ex )
//        {
//            return this.getMessage(MESSAGE_HEADER, Message.ERROR, ex.message)
//        }
            /***************************** COA Entry Start ***************************/
            List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()
            JournalDetails journalDetails = null
            ChartOfAccountsMapping chartOfAccountsMapping = null
            Journal journalHead = new Journal(enterprise: enterpriseConfiguration, userCreated: applicationUser, isActive: true)
            journalHead.tableName =sessionFactory.getClassMetadata(WithdrawSecurityDeposit).tableName
//                    GrailsDomainBinder.getMapping(CustomerPayment).table.name
            journalHead.transactionDate = new Date()
            journalHead.journalStatus = JournalStatus.CHECKED
            journalHead.transactionNo = customerPayment.transNo
            journalHead.journalNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
            journalHead.moduleName = ApplicationConstants.MODULE_WITHDRAW_SECURITY_DEPOSIT
            if(!journalHead.validate()){
                return this.getValidationErrorMessage(journalHead)
            }

            String particular = ""
            journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
            if (customerPayment.paymentMode == 'Bank') {
                chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.BANK)
                if(!chartOfAccountsMapping){
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Bank head is not mapped with Chart of Accounts")
                }
                particular = ApplicationConstants.CHECK_RECEIVED
            } else {
                chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.CASH)
                if(!chartOfAccountsMapping){
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Cash head is not mapped with Chart of Accounts")
                }
                particular = ApplicationConstants.CASH_RECEIVED
            }

            journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
            journalDetails.prefixCode = customerPayment.distributionPoint.code
            journalDetails.debitAmount =Float.parseFloat(params?.updateAmount)
            journalDetails.creditAmount = 0.00
            journalDetails.particular = particular + " from Customer: [" + customerPayment.customerMaster.code + "] " + customerPayment.customerMaster.name
            if(!journalDetails.validate()){
                return this.getValidationErrorMessage(journalDetails)
            }
            journalDetailsList.add(journalDetails)


                // Security Deposit
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.SECURITY_DEPOSIT)
                if(!chartOfAccountsMapping){
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Security Deposit head is not mapped with Chart of Accounts")
                }
                journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                journalDetails.prefixCode = customerPayment.customerMaster.code
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = Float.parseFloat(params?.updateAmount)
                journalDetails.particular = ApplicationConstants.CASH_RECEIVED + " from Customer: [" + customerPayment.customerMaster.code + "] " + customerPayment.customerMaster.name
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)




//            List<SubLedger> subLedgers = []
//            SubLedger subLedger

//            if (customerPayment.isSecurityDeposit) {
//                //            Revert
//
//                subLedger = new SubLedger()
//                subLedger.accCode = params.secHead
//                subLedger.description = "Security deposit revert"
//                subLedger.credit = 0
//                subLedger.debit = securityDeposit.deposited
//                subLedger.transactionNo = customerPayment.transNo
//                subLedger.transactionType = 2
//                subLedger.vin = CodeGenerationUtil.instance.getCode(enterpriseConfiguration, "VOUCHER", enterpriseConfiguration.code, "", "", "", "", "", currentYear, currentMonth, "", "")
//                subLedger.dateCreated = dateNow
//                subLedger.userCreated = applicationUser
//                subLedger.isActive = true
//                subLedgers.add(subLedger)
//
//                subLedger = new SubLedger()
//                if (customerPayment.bankAccount) {
//                    subLedger.accCode = customerPayment.bankAccount.ledgerAccountCode
//                    subLedger.description = "Paid from bank"
//                } else {
//                    subLedger.accCode = customerPayment.cashPool.accountNo
//                    subLedger.description = "Paid by cash"
//                }
//                subLedger.debit = 0
//                subLedger.credit = securityDeposit.deposited
//                subLedger.transactionNo = customerPayment.transNo
//                subLedger.transactionType = 2
//                subLedger.vin = CodeGenerationUtil.instance.getCode(enterpriseConfiguration, "VOUCHER", enterpriseConfiguration.code, "", "", "", "", "", currentYear, currentMonth, "", "")
//                subLedger.dateCreated = dateNow
//                subLedger.userCreated = applicationUser
//                subLedger.isActive = true
//                subLedgers.add(subLedger)
//
//                //            Update
//
//                subLedger = new SubLedger()
//                subLedger.accCode = params.secHead
//                subLedger.description = "Security deposit updated"
//                subLedger.credit = updateAmount
//                subLedger.debit = 0
//                subLedger.transactionNo = customerPayment.transNo
//                subLedger.transactionType = 2
//                subLedger.vin = CodeGenerationUtil.instance.getCode(enterpriseConfiguration, "VOUCHER", enterpriseConfiguration.code, "", "", "", "", "", currentYear, currentMonth, "", "")
//                subLedger.dateCreated = dateNow
//                subLedger.userCreated = applicationUser
//                subLedger.isActive = true
//                subLedgers.add(subLedger)
//
//                subLedger = new SubLedger()
//                if (customerPayment.bankAccount) {
//                    subLedger.accCode = customerPayment.bankAccount.ledgerAccountCode
//                    subLedger.description = "Paid from bank"
//                } else {
//                    subLedger.accCode = customerPayment.cashPool.accountNo
//                    subLedger.description = "Paid by cash"
//                }
//                subLedger.debit = updateAmount
//                subLedger.credit = 0
//                subLedger.transactionNo = customerPayment.transNo
//                subLedger.transactionType = 2
//                subLedger.vin = CodeGenerationUtil.instance.getCode(enterpriseConfiguration, "VOUCHER", enterpriseConfiguration.code, "", "", "", "", "", currentYear, currentMonth, "", "")
//                subLedger.dateCreated = dateNow
//                subLedger.userCreated = applicationUser
//                subLedger.isActive = true
//                subLedgers.add(subLedger)
//
//                customerPayment.amount = updateAmount
//                customerPayment.confirmAmount = updateAmount
//                customerPayment.userUpdated = applicationUser
//                customerPayment.dateUpdated = new Date()
//
//                securityDeposit.deposited = updateAmount
//                securityDeposit.userUpdated = applicationUser
//                securityDeposit.dateUpdated = new Date()
//            }

            Map map = [:]
            map.put("listExistJournal", listExistJournal)
//            map.put("subLedgers", subLedgers)
            map.put("journalDetailsList", journalDetailsList)
            map.put("journalHead", journalHead)

            journalDetails = null
            map.put("customerPayment", customerPayment)
            map.put("securityDeposit", securityDeposit)

            message = this.preCondition(params, map)
            if (message.type == 1) {
                int noOfRows = customerPaymentService.updateSecurityDeposit(map)
                if (noOfRows > 0) {
                    message = this.getMessage("Security Deposit", Message.SUCCESS, "Security Deposit Updated Successfully.")
                } else {
                    message = this.getMessage("Security Deposit", Message.ERROR, FAIL_SAVE)
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}
