package com.bits.bdfp.customer.customersettlement

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.CustomerSettlement
import com.bits.bdfp.customer.CustomerSettlementService
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

/**
 * Created by mdalinaser.khan on 6/26/16.
 */
@Component("withdrawCustomerSettlementAction")
class WithdrawCustomerSettlementAction extends Action {
    public static final Log log = LogFactory.getLog(WithdrawCustomerSettlementAction.class)
    private final String MESSAGE_HEADER = 'Withdraw Customer Settlement'
    private final String MESSAGE_SUCCESS = 'Amount Withdraw Successfully'

    @Autowired
    CustomerSettlementService customerSettlementService
    @Autowired
    SpringSecurityService springSecurityService
    @Autowired
    SessionFactory sessionFactory


    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService?.getCurrentUser()

            CustomerSettlement customerSettlement = new CustomerSettlement()
            customerSettlement.properties = params
            customerSettlement.userCreated = applicationUser
            customerSettlement.transactionNo = DateUtil.now()
            customerSettlement.adjustAmount = 0.00
            if (!customerSettlement.validate()) {
                return this.getValidationErrorMessage(customerSettlement)
            }

            CustomerMaster customerMaster = customerSettlement.customerMaster

            Float remainingAfterAdjustAmount = Float.parseFloat(params.remainingAfterAdjustAmount)
            if(customerSettlement.withdrawAmount > remainingAfterAdjustAmount){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Withdraw Amount can not greater than Remaining After Adjust Amount")
            }

            DistributionPoint factoryDp = DistributionPoint.findByEnterpriseConfigurationAndIsFactory(customerMaster.enterpriseConfiguration, true)

            /***************************** COA Entry Start ***************************/
            Date dateNow = new Date()
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
            String currentDay = formatDay.format(dateNow)
            DistributionPoint factoryDP = DistributionPoint.findByEnterpriseConfigurationAndIsFactory(customerSettlement.enterprise, true)
            if(!factoryDP){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "No Factory Available for Accounting Entry")
            }
            List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()
            JournalDetails journalDetails = null
            ChartOfAccountsMapping chartOfAccountsMapping = null
            Journal journalHead = new Journal(enterprise: customerSettlement.enterprise, userCreated: applicationUser, isActive: true)
            journalHead.tableName =  sessionFactory.getClassMetadata(CustomerSettlement).tableName
//                    GrailsDomainBinder.getMapping(CustomerPayment).table.name
            journalHead.transactionDate = new Date()
            journalHead.journalStatus = JournalStatus.CHECKED
            journalHead.transactionNo = customerSettlement.transactionNo
            journalHead.journalNo = CodeGenerationUtil.instance.generateCode(customerSettlement.enterprise, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
            journalHead.moduleName = ApplicationConstants.MODULE_CUSTOMER_SETTLEMENT
            if(!journalHead.validate()){
                return this.getValidationErrorMessage(journalHead)
            }

            ChartOfAccountsMapping chartOfAccountsMappingSecurityDeposit = ChartOfAccountsMapping.findByCoaType(COAType.SECURITY_DEPOSIT)
            if(!chartOfAccountsMappingSecurityDeposit){
                if(!chartOfAccountsMappingSecurityDeposit){
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Deposit Refundable is not mapped with Chart of Accounts")
                }
            }

            ChartOfAccountsMapping chartOfAccountsMappingDepositToHO = ChartOfAccountsMapping.findByCoaType(COAType.DEPOSIT_TO_HO)
            if(!chartOfAccountsMappingDepositToHO){
                if(!chartOfAccountsMappingDepositToHO){
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Deposit to HO is not mapped with Chart of Accounts")
                }
            }

            ChartOfAccountsMapping chartOfAccountsMappingCash = ChartOfAccountsMapping.findByCoaType(COAType.CASH)
            if(!chartOfAccountsMappingCash){
                if(!chartOfAccountsMappingCash){
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Cash Head is not mapped with Chart of Accounts")
                }
            }

            ChartOfAccountsMapping chartOfAccountsMappingBank = ChartOfAccountsMapping.findByCoaType(COAType.BANK)
            if(!chartOfAccountsMappingBank){
                if(!chartOfAccountsMappingBank){
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Bank Head is not mapped with Chart of Accounts")
                }
            }

            if(params.selectedCustomerType == "branch"){
                CustomerMaster defaultCustomer = CustomerMaster.get(Long.parseLong(params.defaultCustomerId))
                // Sales Man Customer
                DistributionPoint distributionPoint = DistributionPoint.get(Long.parseLong(params.distributionPoint))
                if(!distributionPoint){
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "DP is not Available")
                }

                // HO Book
                // DR
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsMappingSecurityDeposit.chartOfAccounts
                journalDetails.prefixCode = defaultCustomer.code
                journalDetails.postfixCode = factoryDp.code
                journalDetails.debitAmount = customerSettlement.withdrawAmount
                journalDetails.creditAmount = 0.00
                journalDetails.particular = ApplicationConstants.WITHDRAW_AMOUNT + " from Customer: [" + defaultCustomer.code + "] " + defaultCustomer.name
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

                // CR
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                if(customerMaster.customerPaymentType == "Bank"){
                    journalDetails.chartOfAccounts = chartOfAccountsMappingBank.chartOfAccounts
                }else{
                    journalDetails.chartOfAccounts = chartOfAccountsMappingCash.chartOfAccounts
                }
                journalDetails.postfixCode = ""
                journalDetails.postfixCode = factoryDP.code
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = customerSettlement.withdrawAmount
                journalDetails.particular = ApplicationConstants.WITHDRAW_AMOUNT + " from Customer: [" + customerMaster.code + "] " + customerMaster.name
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

                // Branch/DP Book
                // DR
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsMappingSecurityDeposit.chartOfAccounts
                journalDetails.prefixCode = customerMaster.code
                journalDetails.postfixCode = distributionPoint.code
                journalDetails.debitAmount = customerSettlement.withdrawAmount
                journalDetails.creditAmount = 0.00
                journalDetails.particular = ApplicationConstants.WITHDRAW_AMOUNT + " from Customer: [" + customerMaster.code + "] " + customerMaster.name
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

                // CR
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsMappingDepositToHO.chartOfAccounts
                journalDetails.prefixCode = customerMaster.code
                journalDetails.postfixCode = distributionPoint.code
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = customerSettlement.withdrawAmount
                journalDetails.particular = ApplicationConstants.WITHDRAW_AMOUNT + " from Customer: [" + customerMaster.code + "] " + customerMaster.name
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

            }else{
                // Others Customer
                // DR
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsMappingSecurityDeposit.chartOfAccounts
                journalDetails.prefixCode = customerMaster.code
                journalDetails.postfixCode = factoryDP.code
                journalDetails.debitAmount = customerSettlement.withdrawAmount
                journalDetails.creditAmount = 0.00
                journalDetails.particular = ApplicationConstants.WITHDRAW_AMOUNT + " from Customer: [" + customerMaster.code + "] " + customerMaster.name
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

                // CR
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                if(customerMaster.customerPaymentType == "Bank"){
                    journalDetails.chartOfAccounts = chartOfAccountsMappingBank.chartOfAccounts
                }else{
                    journalDetails.chartOfAccounts = chartOfAccountsMappingCash.chartOfAccounts
                }
                journalDetails.prefixCode = ""
                journalDetails.prefixCode = factoryDP.code
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = customerSettlement.withdrawAmount
                journalDetails.particular = ApplicationConstants.WITHDRAW_AMOUNT + " from Customer: [" + customerMaster.code + "] " + customerMaster.name
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)
            }

            Map map = [:]
            map.put('customerSettlement', customerSettlement)
            map.put("journal", journalHead)
            map.put("journalDetailsList", journalDetailsList)

            customerSettlementService.adjustWithReceivable(map)
            return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, "Withdraw Successfully. Withdraw Amount:" + customerSettlement.withdrawAmount)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}