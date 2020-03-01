package com.bits.bdfp.finance.cashreceivedfrombranch

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountService
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.accounts.SubLedger
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.finance.DepositCashToDepositPool
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointWarehouse
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
import com.sun.xml.internal.bind.v2.util.StackRecorder
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.finance.CashReceivedFromBranch
import com.bits.bdfp.finance.CashReceivedFromBranchService


import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService

import java.text.SimpleDateFormat


@Component("createCashReceivedFromBranchAction")
class CreateCashReceivedFromBranchAction extends Action {
    public static final Log log = LogFactory.getLog(CreateCashReceivedFromBranchAction.class)
    private final String MESSAGE_HEADER = 'New Cash Received From Branch'
    private final String MESSAGE_SUCCESS = 'Cash Received From Branch Created Successfully'

    Message message

    @Autowired
    CashReceivedFromBranchService cashReceivedFromBranchService
    @Autowired
    ChartOfAccountService chartOfAccountService
    @Autowired
    SpringSecurityService springSecurityService
    @Autowired
    SessionFactory sessionFactory


    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            Date dateNow = new Date()
            ApplicationUser applicationUser = (ApplicationUser) object
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
            String currentDay = formatDay.format(dateNow)
            CashReceivedFromBranch cashReceivedFromBranch = new CashReceivedFromBranch()
            cashReceivedFromBranch.properties = params
            cashReceivedFromBranch.userCreated = applicationUser
            cashReceivedFromBranch.dateCreated = dateNow
            DepositCashToDepositPool depositCashToDepositPool = cashReceivedFromBranch.depositCashToDepositPool
            depositCashToDepositPool.status = ApplicationConstants.ACKNOWLEDGED

            if (!cashReceivedFromBranch.validate()) {
                return this.getValidationErrorMessage(cashReceivedFromBranch)
            }

            CustomerMaster defaultBranchCustomer =  DistributionPointWarehouse.findByDistributionPoint(cashReceivedFromBranch.distributionPoint)?.defaultCustomer
            if(!defaultBranchCustomer){
                return this.getMessage("Error", Message.ERROR, "Default Customer for DP:" + cashReceivedFromBranch.distributionPoint.name + " is not available")
            }
            /***************************** COA Entry Start ***************************/
            List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()
            DistributionPoint branchDp = depositCashToDepositPool.distributionPoint
            EnterpriseConfiguration enterprise = branchDp.enterpriseConfiguration
            DistributionPoint factoryDp = DistributionPoint.findByEnterpriseConfigurationAndIsFactory(enterprise, true)

            JournalDetails journalDetails = null
            ChartOfAccountsMapping chartOfAccountsMapping = null
            Journal journalHead = new Journal(enterprise: enterprise, userCreated: applicationUser, isActive: true)
            journalHead.tableName =  sessionFactory.getClassMetadata(CashReceivedFromBranch).tableName
            journalHead.transactionDate = new Date()
            journalHead.journalStatus = JournalStatus.CHECKED
            journalHead.transactionNo = depositCashToDepositPool.transactionNo
            journalHead.journalNo = CodeGenerationUtil.instance.generateCode(enterprise, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
            journalHead.moduleName = ApplicationConstants.MODULE_RECEIVE_PAYMENT_FROM_BRANCH
            if(!journalHead.validate()){
                return this.getValidationErrorMessage(journalHead)
            }

            if(depositCashToDepositPool.depositToBankAccount > 0.00){
                // Bank Account
                chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.BANK)
                if(!chartOfAccountsMapping){
                    return this.getMessage("Error", Message.ERROR, "Bank head is not mapped with Chart of Accounts")
                }
                // DR
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                journalDetails.prefixCode = ""
                journalDetails.postfixCode = factoryDp.code
                journalDetails.debitAmount = depositCashToDepositPool.depositToBankAccount
                journalDetails.creditAmount = 0.00
                journalDetails.particular = "Receive by Bank for HO Book from DP: [" + branchDp.code + "] " + branchDp.name
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)
            }
            if(depositCashToDepositPool.depositToHoCash > 0.00){
                // Cash Amount
                chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.CASH)
                if(!chartOfAccountsMapping){
                    return this.getMessage("Error", Message.ERROR, "Cash head is not mapped with Chart of Accounts")
                }
                // DR
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                journalDetails.prefixCode = ""
                journalDetails.postfixCode = factoryDp.code
                journalDetails.debitAmount = depositCashToDepositPool.depositToHoCash
                journalDetails.creditAmount = 0.00
                journalDetails.particular = "Receive by Cash for HO Book from DP: [" + branchDp.code + "] " + branchDp.name
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)
            }
            if(cashReceivedFromBranch.salesAmount > 0.00){
                // Sales Amount
                chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_RECEIVABLE)
                if(!chartOfAccountsMapping){
                    return this.getMessage("Error", Message.ERROR, "Accounts Receivable head is not mapped with Chart of Accounts")
                }
                // CR
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                journalDetails.prefixCode = defaultBranchCustomer.code
                journalDetails.postfixCode = factoryDp.code
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = cashReceivedFromBranch.salesAmount
                journalDetails.particular = "Receive Sales Amount for HO Book from DP: [" + branchDp.code + "] " + branchDp.name
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)
            }
            if(cashReceivedFromBranch.sdAmount > 0.00){
                // Sales Amount
                chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.SECURITY_DEPOSIT)
                if(!chartOfAccountsMapping){
                    return this.getMessage("Error", Message.ERROR, "Deposit Refundable head is not mapped with Chart of Accounts")
                }
                // CR
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                journalDetails.prefixCode = defaultBranchCustomer.code
                journalDetails.postfixCode = factoryDp.code
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = cashReceivedFromBranch.sdAmount
                journalDetails.particular = "Receive SD for HO Book from DP: [" + branchDp.code + "] " + branchDp.name
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)
            }

            Map map = [:]
            map.put('cashReceivedFromBranch', cashReceivedFromBranch)
            map.put('journalHead', journalHead)
            map.put('journalDetailsList', journalDetailsList)
            map.put('depositCashToDepositPool', depositCashToDepositPool)

            int noOfRows = cashReceivedFromBranchService.create(map)
            if (noOfRows > 0) {
                message = this.getMessage("Receive Cash From Branch", Message.SUCCESS, 'Cash Received From Branch Successfully.')
            } else {
                message = this.getMessage('Receive Cash From Branch', Message.ERROR, 'Cash Could Not Be Received From Branch.')
            }

            return message
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}