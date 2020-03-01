package com.bits.bdfp.finance.customerpayment

import com.bits.bdfp.accounts.*
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.finance.AdjustMiscellaneousFeesWithReceivable
import com.bits.bdfp.finance.AdjustMiscellaneousFeesWithReceivableService
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
@Component("withdrawAdjustMiscellaneousFeesWithReceivableAction")
class WithdrawAdjustMiscellaneousFeesWithReceivableAction extends Action{
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
                message = this.getMessage("Adjust Miscellaneous Fees With Receivable", Message.SUCCESS, "Miscellaneous Adjusted Due Amount Withdrawn by ['${CustomerMaster.read(adjustMiscellaneousFeesWithReceivable.customerMaster.id).code}']-'${CustomerMaster.read(adjustMiscellaneousFeesWithReceivable.customerMaster.id).name}' Successfully")
            }
            return message
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            AdjustMiscellaneousFeesWithReceivable adjustMiscellaneousFeesWithReceivable = AdjustMiscellaneousFeesWithReceivable.get(Long.parseLong(params.amfwrId))

            adjustMiscellaneousFeesWithReceivable.properties = params
            adjustMiscellaneousFeesWithReceivable.withdrawAmount = Double.parseDouble(params.withdrawalAmount)

            adjustMiscellaneousFeesWithReceivable.userUpdated = applicationUser
            adjustMiscellaneousFeesWithReceivable.lastUpdated = new Date()


            CustomerMaster customerMaster = CustomerMaster.get(adjustMiscellaneousFeesWithReceivable.customerMaster.id)
            EnterpriseConfiguration enterpriseConfiguration = EnterpriseConfiguration.read(customerMaster.enterpriseConfiguration.id)


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
            if(adjustMiscellaneousFeesWithReceivable.expenseType == 'ACCOMMODATION_RENT'){
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
            journalDetails.prefixCode = ''
            journalDetails.postfixCode = factoryDp.code
            journalDetails.debitAmount = Double.parseDouble(params.withdrawalAmount)
            journalDetails.creditAmount = 0.00
            journalDetails.particular = particular + " due amount paid for Customer: [" + customerMaster.code + "] " + customerMaster.name
            if(!journalDetails.validate()){
                return this.getValidationErrorMessage(journalDetails)
            }
            journalDetailsList.add(journalDetails)

            journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)

            if(params.paymentType == 'bank'){
                chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.BANK)
                if(!chartOfAccountsMapping){
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Bank head is not mapped with Chart of Accounts")
                }
                particular = particular +' '+ ApplicationConstants.CHECK_RECEIVED
            }else{
                chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.CASH)
                if(!chartOfAccountsMapping){
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Cash head is not mapped with Chart of Accounts")
                }
                particular = particular = particular +' '+ ApplicationConstants.CASH_RECEIVED
            }

            journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
            journalDetails.prefixCode = ''
            journalDetails.postfixCode = factoryDp.code
            journalDetails.debitAmount = 0.00
            journalDetails.creditAmount = Double.parseDouble(params.withdrawalAmount)
            journalDetails.particular = particular + " by Customer: [" + customerMaster.code + "] " + customerMaster.name
            if(!journalDetails.validate()){
                return this.getValidationErrorMessage(journalDetails)
            }
            journalDetailsList.add(journalDetails)

            Map map = [:]
            map.put("adjustMiscellaneousFeesWithReceivable",adjustMiscellaneousFeesWithReceivable)
            map.put("journal", journalHead)
            map.put("journalDetailsList", journalDetailsList)


            message = this.preCondition(params,map)

            if(message.type == 1){
                Integer noOfRows = adjustMiscellaneousFeesWithReceivableService.withdraw(map)
                if (noOfRows>0){
                    message = message
                }else{
                    message = this.getMessage("Adjust Miscellaneous Fees With Receivable", Message.ERROR, FAIL_SAVE)
                }
            }
            return message
        }catch (Exception ex){
            log.error(ex.message)
            throw  new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }

}
