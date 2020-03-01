package com.bits.bdfp.finance.customerpayment

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.finance.AdjustMiscellaneousFeesWithReceivable
import com.bits.bdfp.finance.AdjustMiscellaneousFeesWithReceivableService
import com.bits.bdfp.inventory.demandorder.Invoice
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
 * Created by liton.miah on 5/12/2016.
 */
@Component("createAdjustMiscellaneousFeesWithReceivableAction")
class CreateAdjustMiscellaneousFeesWithReceivableAction extends Action{
    private static final Log log = LogFactory.getLog(this)
    private final String MESSAGE_HEADER = 'Message'

    @Autowired
    AdjustMiscellaneousFeesWithReceivableService adjustMiscellaneousFeesWithReceivableService
    @Autowired
    SessionFactory sessionFactory
    Message message

    public Object preCondition(Object params, Object object) {
        try {
            AdjustMiscellaneousFeesWithReceivable adjustMiscellaneousFeesWithReceivable = (AdjustMiscellaneousFeesWithReceivable) object.adjustMiscellaneousFeesWithReceivable

            if (!adjustMiscellaneousFeesWithReceivable.validate()){
                message = this.getValidationErrorMessage(adjustMiscellaneousFeesWithReceivable)
            }else{
                message = this.getMessage("Adjust Miscellaneous Fees With Receivable", Message.SUCCESS, "Miscellaneous Fees Adjusted With Receivable of ['${CustomerMaster.read(adjustMiscellaneousFeesWithReceivable.customerMaster.id).code}']-'${CustomerMaster.read(adjustMiscellaneousFeesWithReceivable.customerMaster.id).name}' Successfully")
            }
            return message
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Map execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            AdjustMiscellaneousFeesWithReceivable adjustMiscellaneousFeesWithReceivable = new AdjustMiscellaneousFeesWithReceivable(params)
            adjustMiscellaneousFeesWithReceivable.userCreated = applicationUser
            adjustMiscellaneousFeesWithReceivable.dateCreated = new Date()

            Double amount = 0.00
            if(Double.parseDouble(params.receivableAmount) >= Double.parseDouble(params.appliedAmount)){
                amount = Double.parseDouble(params.appliedAmount)
            }else{
                amount = Double.parseDouble(params.receivableAmount)
            }

            List<Invoice> invoiceUpdateList = []

            List invoiceList = adjustMiscellaneousFeesWithReceivableService.getInvoiceListByCustomerId(Long.parseLong(params.customerMaster.id))

            CustomerMaster customerMaster = CustomerMaster.get(Long.parseLong(params.customerMaster.id))
            EnterpriseConfiguration enterpriseConfiguration = EnterpriseConfiguration.read(customerMaster.enterpriseConfiguration.id)

            if(invoiceList.size()>0){
                for(int i = 0; i < invoiceList.size(); i++){
                    if(amount>0){
                        Invoice invoice = Invoice.get(invoiceList[i].id)
                        invoice.userUpdated = applicationUser
                        invoice.lastUpdated = new Date()

                        if(amount >= invoice.invoiceAmount) {
                            invoice.paidAmount = invoice.invoiceAmount
                            amount = amount - invoice.invoiceAmount

                            invoiceUpdateList.add(invoice)
                        }else{
                            invoice.paidAmount = amount
                            amount = 0

                            invoiceUpdateList.add(invoice)
                        }
                    }else{
                        break
                    }
                }
            }

            Date dateNow = new Date()
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
            String currentDay = formatDay.format(dateNow)

            DistributionPoint factoryDp = DistributionPoint.findByEnterpriseConfigurationAndIsFactory(enterpriseConfiguration, true)

            /***************************** COA Entry Start ***************************/
            List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()
            JournalDetails journalDetails = null
            ChartOfAccountsMapping chartOfAccountsMapping = null

            Journal journalHead = new Journal(enterprise: enterpriseConfiguration, userCreated: applicationUser, isActive: true)
            journalHead.tableName = sessionFactory.getClassMetadata(AdjustMiscellaneousFeesWithReceivable).tableName
            journalHead.transactionDate = new Date()
            journalHead.journalStatus = JournalStatus.CHECKED
            journalHead.transactionNo = CodeGenerationUtil.generate(8).toString()
            journalHead.journalNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
            journalHead.moduleName = ApplicationConstants.MODULE_ADJUST_MISCELLANEOUS_FEES_WITH_RECEIVABLE
            if(!journalHead.validate()){
                return this.getValidationErrorMessage(journalHead)
            }

            String particular = ""
            journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
            if(params.expenseType == 'ACCOMMODATION_RENT'){
                chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.RENT)
                if(!chartOfAccountsMapping){
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Gondola Charge head is not mapped with Chart of Accounts")
                }
                particular = ApplicationConstants.GONDOLA_CHARGE
            }else{
                chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.GENERAL_EXPENSE)
                if(!chartOfAccountsMapping){
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Other Charge head is not mapped with Chart of Accounts")
                }
                particular = ApplicationConstants.OTHER_CHARGE
            }

            journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
            journalDetails.prefixCode = customerMaster.code
            journalDetails.postfixCode = factoryDp.code
            journalDetails.debitAmount = Double.parseDouble(params.appliedAmount)
            journalDetails.creditAmount = 0.00
            journalDetails.particular = particular + " paid for Customer: [" + customerMaster.code + "] " + customerMaster.name
            if(!journalDetails.validate()){
                return this.getValidationErrorMessage(journalDetails)
            }
            journalDetailsList.add(journalDetails)

            journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
            chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_RECEIVABLE)

            journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
            journalDetails.prefixCode = customerMaster.code
            journalDetails.postfixCode = factoryDp.code
            journalDetails.debitAmount = 0.00
            journalDetails.creditAmount = Double.parseDouble(params.appliedAmount)
            journalDetails.particular = particular + " received by Customer: [" + customerMaster.code + "] " + customerMaster.name
            if(!journalDetails.validate()){
                return this.getValidationErrorMessage(journalDetails)
            }
            journalDetailsList.add(journalDetails)

            Map map = [:]
            map.put("adjustMiscellaneousFeesWithReceivable",adjustMiscellaneousFeesWithReceivable)
            map.put("invoiceUpdateList",invoiceUpdateList)
            map.put("journal", journalHead)
            map.put("journalDetailsList", journalDetailsList)

            Map returnMap = [:]

            message = this.preCondition(params,map)

            if(message.type == 1){
                Integer noOfRows = adjustMiscellaneousFeesWithReceivableService.adjust(map)
                if (noOfRows>0){
                    message = message
                }else {
                    message = this.getMessage("Adjust Miscellaneous Fees With Receivable", Message.ERROR, FAIL_SAVE)

                }
                returnMap.put("message",message)
                returnMap.put("amfwrId",noOfRows)
            }

            return  returnMap
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }

}
