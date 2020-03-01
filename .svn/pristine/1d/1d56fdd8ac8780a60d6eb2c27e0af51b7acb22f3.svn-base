package com.bits.bdfp.finance.expensefromdpcashpool

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.finance.CustomerPayment
import com.bits.bdfp.inventory.sales.DistributionPointWarehouse
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.finance.ExpenseFromDPCashPool
import com.bits.bdfp.finance.ExpenseFromDPCashPoolService


import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService

import java.text.SimpleDateFormat


@Component("createExpenseFromDPCashPoolAction")
class CreateExpenseFromDPCashPoolAction extends Action{
    public static final Log log = LogFactory.getLog(CreateExpenseFromDPCashPoolAction.class)
    private final String MESSAGE_HEADER = 'New Expense From DPC ash Pool'
    private final String MESSAGE_SUCCESS = 'Expense From DPC ash Pool Created Successfully'

    @Autowired
    ExpenseFromDPCashPoolService expenseFromDPCashPoolService
    
    @Autowired
    SpringSecurityService springSecurityService
    @Autowired
    SessionFactory sessionFactory

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            ExpenseFromDPCashPool expenseFromDPCashPool = new ExpenseFromDPCashPool()
            expenseFromDPCashPool.properties = params

            expenseFromDPCashPool.userCreated = (ApplicationUser) springSecurityService?.getCurrentUser()
            expenseFromDPCashPool.isActive=1
            if (!expenseFromDPCashPool.validate()) {
                this.getValidationErrorMessage(expenseFromDPCashPool)
            }

            expenseFromDPCashPool.transactionNo = CodeGenerationUtil.generate(8).toString()

            //ApplicationUser applicationUser = (ApplicationUser) object
            ApplicationUser applicationUser =  (ApplicationUser) springSecurityService?.getCurrentUser()
            Date dateNow = new Date()
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
            String currentDay = formatDay.format(dateNow)

            EnterpriseConfiguration enterpriseConfiguration = EnterpriseConfiguration.read(expenseFromDPCashPool.cashPool.enterpriseConfiguration.id)

            CustomerMaster branch = DistributionPointWarehouse.findByDistributionPoint(expenseFromDPCashPool.distributionPoint).defaultCustomer

            //Invoice secondaryInvoice = new Invoice()
            //secondaryInvoice.code = CodeGenerationUtil.instance.generateCode(secondaryDemandOrder.customerMaster.enterpriseConfiguration, "SECONDARY_INVOICE", "", "", "", "", "", currentYear, currentMonth, "", "")
            /***************************** COA Entry Start ***************************/
            List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()
            JournalDetails journalDetails = null
            ChartOfAccountsMapping chartOfAccountsMapping = null
            //Journal journalHead = new Journal(userCreated: applicationUser, isActive: true)
            Journal journalHead = new Journal(enterprise: enterpriseConfiguration, userCreated: applicationUser, isActive: true)
            journalHead.tableName =  sessionFactory.getClassMetadata(ExpenseFromDPCashPool).tableName

            journalHead.transactionDate = new Date()
            journalHead.journalStatus = JournalStatus.CHECKED
            journalHead.transactionNo = expenseFromDPCashPool.transactionNo
            journalHead.dateCreated = dateNow
            journalHead.journalNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
            journalHead.moduleName = ApplicationConstants.MODULE_EXPENSE_DP_CASH_POOL
            /*if(!journalHead.validate()){
                return this.getValidationErrorMessage(journalHead)
            }*/

            String particular = ""
            journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
            /*chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.CASH)
            if(!chartOfAccountsMapping){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Cash head is not mapped with Chart of Accounts")
            }*/
            //For Debit
            particular = ApplicationConstants.EXPENSE_FROM_DP
            journalDetails.chartOfAccounts = expenseFromDPCashPool.expenditureHeads
            journalDetails.prefixCode = ''
            //journalDetails.postfixCode = branch.code
            journalDetails.postfixCode = expenseFromDPCashPool.distributionPoint.id
            journalDetails.debitAmount = expenseFromDPCashPool.expenseAmount
            journalDetails.creditAmount = 0.00
            journalDetails.dateCreated = dateNow
            journalDetails.particular = particular + " for: [" + expenseFromDPCashPool.expenditureHeads.chartOfAccountCodeUser + "] " + expenseFromDPCashPool.cashPool.code
            //journalDetails.particular = particular + " from Customer: [" + customerPayment.customerMaster.code + "] " + customerPayment.customerMaster.name
           /* if(!journalDetails.validate()){
                return this.getValidationErrorMessage(journalDetails)
            }*/
            journalDetailsList.add(journalDetails)

            journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
            chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.CASH)
            if(!chartOfAccountsMapping){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Cash head is not mapped with Chart of Accounts")
            }
            //For Credit
            particular = ApplicationConstants.EXPENSE_FROM_DP
            journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
            journalDetails.prefixCode = ''
            ////journalDetails.postfixCode = branch.code
            journalDetails.postfixCode = expenseFromDPCashPool.distributionPoint.id
            journalDetails.debitAmount = 0.00
            journalDetails.creditAmount = expenseFromDPCashPool.expenseAmount
            journalDetails.dateCreated=dateNow
            journalDetails.lastUpdated=dateNow
            journalDetails.particular = particular + " for: [" + expenseFromDPCashPool.expenditureHeads.chartOfAccountCodeUser + "] " + expenseFromDPCashPool.cashPool.code

            journalDetailsList.add(journalDetails)

            Map map =[:]
            map.put('expenseFromDPCashPool', expenseFromDPCashPool)
            map.put("journal", journalHead)
            map.put("journalDetailsList", journalDetailsList)

            expenseFromDPCashPoolService.createExpense(map)
            /*
            if (expenseFromDPCashPoolService.create(map)){
                return this.getMessage(customerPayment, Message.SUCCESS, "Payment record successfully saved against MR no: " + customerPayment.mrNo)
            } else {
                return this.getMessage(customerPayment, Message.ERROR, FAIL_SAVE)
            }*/
            return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}