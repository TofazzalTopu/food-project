package com.bits.bdfp.finance.withdrawSecurityDeposit

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.common.BankBranch
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.finance.CustomerPayment
import com.bits.bdfp.finance.CustomerPaymentService
import com.bits.bdfp.finance.SecurityDeposit
import com.bits.bdfp.finance.SecurityDepositInterest
import com.bits.bdfp.finance.SecurityDepositService
import com.bits.bdfp.finance.WithdrawSecurityDeposit
import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.inventory.sales.DistributionPoint
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
 * Created by depok.chakma on 6/19/2016.
 */
@Component("createWithdrawSecurityDepositAction")
class CreateWithdrawSecurityDepositAction extends Action {
    private static final Log log = LogFactory.getLog(this)

    @Autowired
    CustomerPaymentService customerPaymentService

    @Autowired
    SecurityDepositService securityDepositService

    @Autowired
    SessionFactory sessionFactory
    Message message

    public Object preCondition(Object user, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) user
            WithdrawSecurityDeposit withdrawSecurityDeposit = (WithdrawSecurityDeposit) object
            withdrawSecurityDeposit.userCreated = applicationUser
            withdrawSecurityDeposit.dateCreated = new Date()
//        if (!withdrawSecurityDeposit.validate()) {
//            return null
//        }
            return withdrawSecurityDeposit
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }


    public List<WithdrawSecurityDeposit> withdrawSecurityDepositList(Object params, Object applicationUser)
    {
        try{


        List<WithdrawSecurityDeposit> withdrawSecurityDepositArrayList = new ArrayList<WithdrawSecurityDeposit>()
        WithdrawSecurityDeposit withdrawSecurityDeposit = null

        for (int i=0;i<=1;i++)
        {
            withdrawSecurityDeposit=new WithdrawSecurityDeposit(params)
            withdrawSecurityDeposit.code=CodeGenerationUtil.generate(8).toString()
            withdrawSecurityDeposit.enterpriseConfiguration=EnterpriseConfiguration.read(params.idEnterprise)
            withdrawSecurityDeposit.distributionPoint=DistributionPoint.read(params.distributionPoint)
            withdrawSecurityDeposit.territoryConfiguration=TerritoryConfiguration.read(params.territory)
            withdrawSecurityDeposit.userCreated=applicationUser
            withdrawSecurityDeposit.dateCreated=new Date()
            if(i==0) {
                withdrawSecurityDeposit.customerMaster = CustomerMaster.read(params.defaultCustomerId)
            }
            else if (i==1)
            {
                withdrawSecurityDeposit.customerMaster = CustomerMaster.read(params.tpId)
            }
            if(params.paymentType=="bank")
            {
                withdrawSecurityDeposit.isBankPayment=true
                withdrawSecurityDeposit.isCashPayment=false
                withdrawSecurityDeposit.bankRef=params.bankRef
            }
            else if(params.paymentType=="cash")
            {
                withdrawSecurityDeposit.isCashPayment=true
                withdrawSecurityDeposit.isBankPayment=false
            }
            withdrawSecurityDepositArrayList.add(withdrawSecurityDeposit)
        }
        return withdrawSecurityDepositArrayList
    }
    catch(Exception ex)
    {
        log.error(ex.message)
        throw new RuntimeException(ex.message)
    }
    }

    public Object execute(Object params, Object applicationUser) {
        try {
            List<WithdrawSecurityDeposit> withdrawSecurityDepositList = withdrawSecurityDepositList(params,applicationUser)
            CustomerMaster customerMaster = CustomerMaster.read(params.defaultCustomerId)
            //SecurityDeposit securityDeposit=SecurityDeposit.findByCustomerMaster(customerMaster).list()
            CustomerMaster customerMasterSalesMan=CustomerMaster.read(params.tpId)
            List<SecurityDeposit> securityDepositList = SecurityDeposit.findAllByCustomerMaster(customerMasterSalesMan)
            Double withdrawalAmount = Float.parseFloat(params.withdrawalAmount)

            securityDepositList.each {
                if(withdrawalAmount > 0 && it.deposited >= withdrawalAmount){
                    it.withdrawn += withdrawalAmount
                    withdrawalAmount = 0
                }else{
                    withdrawalAmount -= it.deposited
                    it.withdrawn += it.deposited
                }
            }

//            SecurityDeposit securityDeposit1 = SecurityDeposit.findById(securityDeposit[0].id)

//            securityDeposit1.withdrawn = securityDeposit1.withdrawn + Float.parseFloat(params.withdrawalAmount)


            DistributionPoint distributionPoint = DistributionPoint.read(params.distributionPoint)
            DistributionPoint factoryDp = DistributionPoint.findByEnterpriseConfigurationAndIsFactory(distributionPoint.enterpriseConfiguration, true)

            //ApplicationUser applicationUser=session?.applicationUser
            /***************************** COA Entry Start ***************************/

            Date dateNow = new Date()
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            SimpleDateFormat formatDay = new SimpleDateFormat("DD")
            String currentDay = formatDay.format(dateNow)


            List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()
            JournalDetails journalDetails = null
            //ChartOfAccountsMapping chartOfAccountsMapping = null
            Journal journalHead = new Journal(enterprise: customerMaster.enterpriseConfiguration, userCreated: applicationUser, isActive: true)
            journalHead.tableName = sessionFactory.getClassMetadata(WithdrawSecurityDeposit).tableName
            journalHead.transactionDate = new Date()
            journalHead.journalStatus = JournalStatus.CHECKED
            journalHead.transactionNo = CodeGenerationUtil.generate(8).toString()
            journalHead.journalNo = CodeGenerationUtil.instance.generateCode(customerMaster.enterpriseConfiguration, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
            journalHead.moduleName = ApplicationConstants.MODULE_WITHDRAW_SECURITY_DEPOSIT
            if (!journalHead.validate()) {
                return this.getValidationErrorMessage(journalHead)
            }

            // DR Journal HO Factory

            ChartOfAccountsMapping chartOfAccountsMappingSecurityDeposit = ChartOfAccountsMapping.findByCoaType(COAType.SECURITY_DEPOSIT)
            if (!chartOfAccountsMappingSecurityDeposit) {
                return this.getMessage("Withdraw SD", Message.ERROR, "Deposit Refundable Head is not mapped with Chart of Accounts")
            }

            journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
            journalDetails.chartOfAccounts = chartOfAccountsMappingSecurityDeposit.chartOfAccounts
            journalDetails.prefixCode = customerMaster.code
            journalDetails.postfixCode = factoryDp.code
            journalDetails.debitAmount = Float.parseFloat(params.withdrawalAmount)
            journalDetails.creditAmount = 0.00
            journalDetails.particular = "Deposit Refundable of the customer [" + customerMaster.code + "]"
            journalDetails.journal = journalHead
            journalDetails.dateCreated = dateNow
            if (!journalDetails.validate()) {
                return this.getValidationErrorMessage(journalDetails)
            }
            journalDetailsList.add(journalDetails)

            // CR Journal HO Factory
            journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
            String particulars = ""
            if (params.paymentType == "cash") {
                ChartOfAccountsMapping chartOfAccountsMappingCash = ChartOfAccountsMapping.findByCoaType(COAType.CASH)
                if (!chartOfAccountsMappingCash) {
                    return this.getMessage("Withdraw SD", Message.ERROR, "Cash Head is not mapped with Chart of Accounts")
                }
                particulars = "Security deposit withdrawal to Sales man [" + customerMaster.code + "] through cash"
                journalDetails.chartOfAccounts = chartOfAccountsMappingCash.chartOfAccounts
            } else if (params.paymentType == "bank") {
                ChartOfAccountsMapping chartOfAccountsMappingCash = ChartOfAccountsMapping.findByCoaType(COAType.BANK)
                if (!chartOfAccountsMappingCash) {
                    return this.getMessage("Withdraw SD", Message.ERROR, "Bank Head is not mapped with Chart of Accounts")
                }
                particulars = "Security deposit withdrawal to Sales man [" + customerMaster.code + "] through cash"
                journalDetails.chartOfAccounts = chartOfAccountsMappingCash.chartOfAccounts
            }



            journalDetails.prefixCode = ""
            journalDetails.postfixCode = factoryDp.code
            journalDetails.debitAmount = 0
            journalDetails.creditAmount = Float.parseFloat(params.withdrawalAmount)
            journalDetails.particular = particulars
            journalDetails.journal = journalHead
            journalDetails.dateCreated = dateNow
            journalDetails.userCreated = applicationUser
            if (!journalDetails.validate()) {
                return this.getValidationErrorMessage(journalDetails)
            }
            journalDetailsList.add(journalDetails)

            // DR Journal BRANCH
//            ChartOfAccountsMapping chartOfAccountsMapping = ChartOfAccountsMapping.findByCoaType(COAType.SECURITY_DEPOSIT)
//            if(!chartOfAccountsMapping){
//                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Deposit Refundable Head is not mapped with Chart of Accounts")
//            }
            journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
            journalDetails.chartOfAccounts = chartOfAccountsMappingSecurityDeposit.chartOfAccounts
            journalDetails.prefixCode = customerMasterSalesMan.code
            journalDetails.postfixCode = factoryDp.code
            journalDetails.debitAmount = Float.parseFloat(params.withdrawalAmount)
            journalDetails.creditAmount = 0.00
            journalDetails.particular = "Deposit Refundable of the customer [" + customerMaster.code + "]"
            journalDetails.journal = journalHead
            journalDetails.dateCreated = dateNow
            if (!journalDetails.validate()) {
                return this.getValidationErrorMessage(journalDetails)
            }
            journalDetailsList.add(journalDetails)

            // CR Journal HO BRANCH
            ChartOfAccountsMapping chartOfAccountsMappingDepositToHO = ChartOfAccountsMapping.findByCoaType(COAType.DEPOSIT_TO_HO)
            if (!chartOfAccountsMappingDepositToHO) {
                return this.getMessage("Withdraw SD", Message.ERROR, "Deposit to HO Head is not mapped with Chart of Accounts")
            }
            journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
            journalDetails.chartOfAccounts = chartOfAccountsMappingDepositToHO.chartOfAccounts
            journalDetails.prefixCode = ""
            journalDetails.postfixCode = factoryDp.code
            journalDetails.debitAmount = 0
            journalDetails.creditAmount = Float.parseFloat(params.withdrawalAmount)
            journalDetails.particular = "Deposit to HO of the customer [" + customerMaster.code + "]"
            journalDetails.journal = journalHead
            journalDetails.dateCreated = dateNow
            if (!journalDetails.validate()) {
                return this.getValidationErrorMessage(journalDetails)
            }
            journalDetailsList.add(journalDetails)

            /***************************** COA Entry End ***************************/
            Map map = [:]
            //map.put("withdrawSecurityDeposit", object)
            map.put("withdrawSecurityDepositList",withdrawSecurityDepositList)
            map.put("securityDepositList",securityDepositList)
            map.put("journal", journalHead)
            map.put("journalDetailsList", journalDetailsList)


            //{
            Integer row = securityDepositService.createWithdrawSecurityDeposit(map)
            if (row > 0) {
                message = this.getMessage("Withdraw Security Deposit", Message.SUCCESS, "Withdraw Security Deposit Created Successfully.")
            } else {
                message = this.getMessage("Withdraw Security Deposit", Message.ERROR, FAIL_SAVE)
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
