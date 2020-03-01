package com.bits.bdfp.finance.adjustSecurityDeposit

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.finance.AdjustSecurityDepositService
import com.bits.bdfp.finance.AdjustSecurityDepositWithInvoice
import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.warehouse.InventoryToInventoryTransfer
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
 * Created by depok.chakma on 7/12/2016.
 */
@Component("createAdjustSecurityDepositAction")
class CreateAdjustSecurityDepositAction extends Action{
    private static final Log log = LogFactory.getLog(this)
    private final String MESSAGE_HEADER = 'Message'

    @Autowired
    SessionFactory sessionFactory
    @Autowired
    AdjustSecurityDepositService adjustSecurityDepositService
    Message message
    @Override
    public Object preCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    public Object postCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    public Object execute(Object params, Object user) {
        try
        {
            ApplicationUser applicationUser = (ApplicationUser) user
            AdjustSecurityDepositWithInvoice adjustSecurityDepositWithInvoice = new AdjustSecurityDepositWithInvoice(params)
            adjustSecurityDepositWithInvoice.code = CodeGenerationUtil.generate(8).toString()
            adjustSecurityDepositWithInvoice.enterpriseConfiguration = EnterpriseConfiguration.read(params.idEnterprise)
            adjustSecurityDepositWithInvoice.distributionPoint = DistributionPoint.read(params.distributionPoint)
            adjustSecurityDepositWithInvoice.territoryConfiguration = TerritoryConfiguration.read(params.territory)

            CustomerMaster primaryCustomer = CustomerMaster.read(params.defaultCustomerId)
            CustomerMaster salesMan = null

            if(params.isDpCustomer == "true") {
                salesMan = CustomerMaster.read(params.tpId)
                adjustSecurityDepositWithInvoice.customerMaster = salesMan
            }
            else {
                adjustSecurityDepositWithInvoice.customerMaster = primaryCustomer
            }

            adjustSecurityDepositWithInvoice.amountAdjusted = Double.parseDouble(params.amountAdjusted)

            adjustSecurityDepositWithInvoice.userCreated = applicationUser
            adjustSecurityDepositWithInvoice.dateCreated = new Date()

            DistributionPoint factoryDp = DistributionPoint.findByEnterpriseConfigurationAndIsFactory(adjustSecurityDepositWithInvoice.enterpriseConfiguration, true)

            /***************************** COA Entry Start ***************************/

            Date dateNow = new Date()
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
            String currentDay = formatDay.format(dateNow)

            Journal journalHead = new Journal(enterprise: adjustSecurityDepositWithInvoice.enterpriseConfiguration,userCreated: applicationUser, isActive: true)
            journalHead.tableName = sessionFactory.getClassMetadata(InventoryToInventoryTransfer).tableName
            journalHead.transactionDate = new Date()

            journalHead.journalStatus = JournalStatus.CHECKED
            journalHead.transactionNo = adjustSecurityDepositWithInvoice.code
            journalHead.journalNo = CodeGenerationUtil.instance.generateCode(adjustSecurityDepositWithInvoice.enterpriseConfiguration, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
            journalHead.moduleName = ApplicationConstants.MODULE_ADJUST_SECURITY_DEPOSIT_WITH_INVOICE
            //WithdrawSecurityDeposit withdrawSecurityDepositInstance = createWithdrawSecurityDepositAction.preCondition(applicationUser, withdrawSecurityDeposit)


            List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()
            JournalDetails journalDetails = null

            // DR Journal HO Factory
            ChartOfAccountsMapping chartOfAccountsMappingSecurityDeposit = ChartOfAccountsMapping.findByCoaType(COAType.SECURITY_DEPOSIT)
            if(!chartOfAccountsMappingSecurityDeposit){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Deposit Refundable Head is not mapped with Chart of Accounts")
            }

            ChartOfAccountsMapping chartOfAccountsMappingReceivable = ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_RECEIVABLE)
            if(!chartOfAccountsMappingSecurityDeposit){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Accounts Receivable Head is not mapped with Chart of Accounts")
            }

            ChartOfAccountsMapping chartOfAccountsMappingPayable = ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_PAYABLE)
            if(!chartOfAccountsMappingPayable){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Accounts Payable Head is not mapped with Chart of Accounts")
            }

            ChartOfAccountsMapping chartOfAccountsMappingDepositToHo = ChartOfAccountsMapping.findByCoaType(COAType.DEPOSIT_TO_HO)
            if(!chartOfAccountsMappingDepositToHo){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Deposit to HO Head is not mapped with Chart of Accounts")
            }

            // HO Book for Primary Customer (Branch/Non DP) // DR
            journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
            journalDetails.chartOfAccounts = chartOfAccountsMappingSecurityDeposit.chartOfAccounts
            journalDetails.prefixCode = primaryCustomer.code
            journalDetails.postfixCode = factoryDp.code
            journalDetails.debitAmount =  adjustSecurityDepositWithInvoice.amountAdjusted
            journalDetails.creditAmount = 0.00
            journalDetails.particular = "Deposit Refundable to HO Book for Customer: " + primaryCustomer.name + " [" + primaryCustomer.legacyId + "]"
            if(!journalDetails.validate()){
                return this.getValidationErrorMessage(journalDetails)
            }
            journalDetailsList.add(journalDetails)

            // HO Book for Primary Customer (Branch/Non DP) // CR
            journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
            journalDetails.chartOfAccounts = chartOfAccountsMappingReceivable.chartOfAccounts
            journalDetails.prefixCode = primaryCustomer.code   // Branch Default // Primary Customer
            journalDetails.postfixCode = factoryDp.code
            journalDetails.debitAmount =  0.00
            journalDetails.creditAmount = adjustSecurityDepositWithInvoice.amountAdjusted
            journalDetails.particular = "Accounts Receivable to HO Book for Customer: " + primaryCustomer.name + " [" + primaryCustomer.legacyId + "]"
            if(!journalDetails.validate()){
                return this.getValidationErrorMessage(journalDetails)
            }
            journalDetailsList.add(journalDetails)

            if(params.isDpCustomer == "true") {
                // Branch Book // DR
                journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsMappingSecurityDeposit.chartOfAccounts
                journalDetails.prefixCode = salesMan.code // Sales Man
                journalDetails.postfixCode = adjustSecurityDepositWithInvoice.distributionPoint.code
                journalDetails.debitAmount =  adjustSecurityDepositWithInvoice.amountAdjusted
                journalDetails.creditAmount = 0.00
                journalDetails.particular = "Deposit Refundable to Branch Book for Customer: " + salesMan.name + " [" + salesMan.legacyId + "]"
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

                // Branch Book // CR
                journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsMappingReceivable.chartOfAccounts
                journalDetails.prefixCode = adjustSecurityDepositWithInvoice.customerMaster.code   // Sales Man
                journalDetails.postfixCode = adjustSecurityDepositWithInvoice.distributionPoint.code
                journalDetails.debitAmount =  0.00
                journalDetails.creditAmount = adjustSecurityDepositWithInvoice.amountAdjusted
                journalDetails.particular = "Accounts Receivable to Branch Book for Customer: " + salesMan.name + " [" + salesMan.legacyId + "]"
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

                // Branch Book // DR
                journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsMappingPayable.chartOfAccounts
                journalDetails.prefixCode = factoryDp.code
                journalDetails.postfixCode = adjustSecurityDepositWithInvoice.distributionPoint.code  // Branch DP
                journalDetails.debitAmount =  adjustSecurityDepositWithInvoice.amountAdjusted
                journalDetails.creditAmount = 0.00
                journalDetails.particular = "Accounts Payable to Branch Book for Customer: " + salesMan.name + " [" + salesMan.legacyId + "]"
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

                // Branch Book // CR
                journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsMappingDepositToHo.chartOfAccounts
                journalDetails.prefixCode = ""
                journalDetails.postfixCode = adjustSecurityDepositWithInvoice.distributionPoint.code  // Branch DP
                journalDetails.debitAmount =  0.00
                journalDetails.creditAmount = adjustSecurityDepositWithInvoice.amountAdjusted
                journalDetails.particular = "Deposit to HO to Branch Book for Customer: " + salesMan.name + " [" + salesMan.legacyId + "]"
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)
            }

            Map mapInstance = [:]
            mapInstance.put("adjustSecurityDepositWithInvoice", adjustSecurityDepositWithInvoice)
            mapInstance.put("journalHead", journalHead)
            mapInstance.put("journalDetailsList", journalDetailsList)


            Integer row  = adjustSecurityDepositService.createAdjustSecurityDepositInvoice(mapInstance)
            if(row>0){
                message = this.getMessage("Adjust Security Deposit With Invoice", Message.SUCCESS, "Adjust Security Deposit With Invoice Created Successfully.")
            } else {
                message = this.getMessage("Adjust Security Deposit With Invoice", Message.ERROR, FAIL_SAVE)
            }
            return message
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

}
