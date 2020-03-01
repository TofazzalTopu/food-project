package com.bits.bdfp.finance.customerpayment

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.common.CashPool
import com.bits.bdfp.currency.CurrencyDemonstration
import com.bits.bdfp.finance.CustomerAccount
import com.bits.bdfp.finance.CustomerPayment
import com.bits.bdfp.finance.CustomerPaymentCurrencyDenomination
import com.bits.bdfp.finance.CustomerPaymentInvoice
import com.bits.bdfp.finance.CustomerPaymentService
import com.bits.bdfp.finance.SecurityDeposit
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsDomainBinder
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

/**
 * Created by prianka.adhikary on 9/30/2015.
 */
@Component("createCustomerPaymentAction")
class CreateCustomerPaymentAction extends Action {

    private static final Log log = LogFactory.getLog(this)
    private final String MESSAGE_HEADER = 'Message'

    @Autowired
    CustomerPaymentService customerPaymentService
    @Autowired
    SessionFactory sessionFactory
    Message message

    public Object preCondition(Object params, Object object) {
        try {
            return null
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Customer Payment", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object execute(Object params, Object object) {
        try {
            Float totalApplied = Float.parseFloat(params?.totalAppliedAmount)
            Float amountReceived = Float.parseFloat(params?.amount)
            List<CustomerPaymentCurrencyDenomination> customerPaymentCurrencyDenominationList = []
            CustomerPaymentCurrencyDenomination customerPaymentCurrencyDenomination = null

            ApplicationUser applicationUser = (ApplicationUser) object
            CustomerPayment customerPayment = new CustomerPayment(params)
            customerPayment.transNo = "RMP" + CodeGenerationUtil.generate(8).toString()     // RMP= Register Money Payment
            customerPayment.mrNo = "MRB" + CodeGenerationUtil.generate(8).toString()
            customerPayment.dateTransaction = DateUtil.getSimpleDate(params?.dateTransaction)
            customerPayment.dateCreated = new Date()
            customerPayment.userCreated = applicationUser

            String invoices = ''
            String invoiceIssueDates = ''

            if (params.paymentMode == 'Bank') {
                customerPayment.paymentMode = 'Bank'
            } else {
                customerPayment.paymentMode = 'Cash'
                customerPayment.cashPool = CashPool.read(Long.parseLong(params.cashPool.id))
                params.denominations.each { key, val ->
                    if (val instanceof Map && Float.parseFloat(val.qty) > 0) {
                        CurrencyDemonstration currencyDemonstration = CurrencyDemonstration.read(Long.parseLong(val.id))
                        customerPaymentCurrencyDenomination = new CustomerPaymentCurrencyDenomination(params)
                        customerPaymentCurrencyDenomination.currencyDemonstration = currencyDemonstration
                        customerPaymentCurrencyDenomination.customerPayment = customerPayment
                        customerPaymentCurrencyDenomination.dateCreated = new Date()
                        customerPaymentCurrencyDenomination.quantity = Long.parseLong(val.qty)
                        customerPaymentCurrencyDenomination.userCreated = applicationUser
                        if(!customerPaymentCurrencyDenomination.validate()) {
                            throw new RuntimeException(this.getValidationErrorMessage(customerPaymentCurrencyDenomination).messageBody[0].toString())
                        }
                        customerPaymentCurrencyDenominationList.add(customerPaymentCurrencyDenomination)
                    }
                }
            }

            if(!customerPayment.validate()){
                return this.getValidationErrorMessage(customerPayment)
            }

            if (customerPayment.amount != customerPayment.confirmAmount) {
                return this.getMessage("Customer Payment", Message.ERROR, "Amount Received and Confirm Amount should be same.")
            }

            Double advancePayment = 0.00

            if(params.isSecurityDeposit != "true") {
                // Collect Against Invoices
                if (Double.parseDouble(params.totalAppliedAmount).round(2) > customerPayment.amount.round(2)) {
                    return this.getMessage("Customer Payment", Message.ERROR, "Amount Received can not be less than Total Applied Amount")
                }
                advancePayment = customerPayment.amount - Double.parseDouble(params.totalAppliedAmount).round(2)
            }

//            CustomerDemandOrderPayment customerDemandOrderPayment = null
//            List<CustomerDemandOrderPayment> customerDemandOrderPaymentList = []
            SecurityDeposit securityDeposit = null
            CustomerPaymentInvoice customerPaymentInvoice = null
            List<CustomerPaymentInvoice> customerPaymentInvoiceList = new ArrayList<CustomerPaymentInvoice>()
            CustomerAccount customerAccount = null
            List<Invoice> invoiceList = []
            Invoice invoice = null
            if(params.isSecurityDeposit != "true") {
                // Collect Against Invoices
                params.items.each { key, val ->
                    if (val instanceof Map) {
                        invoice = Invoice.get(Long.parseLong(val.id))
                        customerPaymentInvoice = new CustomerPaymentInvoice(customerPayment: customerPayment, userCreated: applicationUser)
                        customerPaymentInvoice.invoice = invoice
                        float appliedAmount = Float.parseFloat(val.applied_amount)
                        customerPaymentInvoice.paidAmount = appliedAmount
                        customerPaymentInvoiceList.add(customerPaymentInvoice)

                        invoice.paidAmount += appliedAmount
                        if (invoice.paidAmount > invoice.invoiceAmount) {
                            invoice.paidAmount = invoice.invoiceAmount
                        }
                        invoiceList.add(invoice)
                        /*
                        // No need as invoice log is saved in CustomerPaymentInvoice table
                        if (invoices == '') {
                            invoices = val.invoice_no
                            invoiceIssueDates = val.invoice_date
                        } else {
                            invoices = invoices + ',' + val.invoice_no
                            invoiceIssueDates = invoiceIssueDates + ',' + val.invoice_date
                        }
                        */
                    }
                }
            }else{
                securityDeposit = new SecurityDeposit(customerPayment: customerPayment, customerMaster: customerPayment.customerMaster, userCreated: applicationUser)
                securityDeposit.dateTransaction = new Date()
                securityDeposit.deposited = customerPayment.confirmAmount
                securityDeposit.dateCreated = new Date()
                securityDeposit.withdrawn = 0.00
                if(!securityDeposit.validate()){
                    return this.getValidationErrorMessage(securityDeposit)
                }
            }

//            customerPayment.invoices = invoices
//            customerPayment.invoiceIssueDates = invoiceIssueDates
            EnterpriseConfiguration enterpriseConfiguration = EnterpriseConfiguration.read(customerPayment.customerMaster.enterpriseConfiguration.id)

            Date dateNow = new Date()
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
            String currentDay = formatDay.format(dateNow)

            /***************************** COA Entry Start ***************************/
            List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()
            JournalDetails journalDetails = null
            ChartOfAccountsMapping chartOfAccountsMapping = null
            Journal journalHead = new Journal(enterprise: enterpriseConfiguration, userCreated: applicationUser, isActive: true)
            journalHead.tableName =  sessionFactory.getClassMetadata(CustomerPayment).tableName
//                    GrailsDomainBinder.getMapping(CustomerPayment).table.name
            journalHead.transactionDate = new Date()
            journalHead.journalStatus = JournalStatus.CHECKED
            journalHead.transactionNo = customerPayment.transNo
            journalHead.journalNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
            journalHead.moduleName = ApplicationConstants.MODULE_CUSTOMER_PAYMENT
            if(!journalHead.validate()){
                return this.getValidationErrorMessage(journalHead)
            }

            String particular = ""
            journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
            if (params.paymentMode == 'Bank') {
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
            journalDetails.prefixCode = ""
            journalDetails.postfixCode = customerPayment.distributionPoint.code
            journalDetails.debitAmount = customerPayment.confirmAmount
            journalDetails.creditAmount = 0.00
            journalDetails.particular = particular + " from Customer: [" + customerPayment.customerMaster.code + "] " + customerPayment.customerMaster.name
            journalDetails.dateCreated = new Date()
            if(!journalDetails.validate()){
                return this.getValidationErrorMessage(journalDetails)
            }
            journalDetailsList.add(journalDetails)

            if(params.isSecurityDeposit == "true"){
                // Security Deposit
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.SECURITY_DEPOSIT)
                if(!chartOfAccountsMapping){
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Security Deposit head is not mapped with Chart of Accounts")
                }
                journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                journalDetails.prefixCode = customerPayment.customerMaster.code
                journalDetails.postfixCode = customerPayment.distributionPoint.code
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = customerPayment.confirmAmount
                journalDetails.particular = ApplicationConstants.CASH_RECEIVED + " from Customer: [" + customerPayment.customerMaster.code + "] " + customerPayment.customerMaster.name
                journalDetails.dateCreated = new Date()
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)
            }else{
                // Payment Against Invoice
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_RECEIVABLE)
                if(!chartOfAccountsMapping){
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Accounts Receivable head is not mapped with Chart of Accounts")
                }
                journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                journalDetails.prefixCode = customerPayment.customerMaster.code
                journalDetails.postfixCode = customerPayment.distributionPoint.code
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = customerPayment.confirmAmount - advancePayment
                journalDetails.particular = ApplicationConstants.CASH_RECEIVED + " Against Invoice from Customer: [" + customerPayment.customerMaster.code + "] " + customerPayment.customerMaster.name
                journalDetails.dateCreated = new Date()
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

                if(advancePayment > 0.00){
                    journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                    chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.ADVANCE_FROM_CUSTOMER)
                    if(!chartOfAccountsMapping){
                        return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Advance from Customer head is not mapped with Chart of Accounts")
                    }
                    journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                    journalDetails.prefixCode = customerPayment.customerMaster.code
                    journalDetails.postfixCode = customerPayment.distributionPoint.code
                    journalDetails.debitAmount = 0.00
                    journalDetails.creditAmount = advancePayment
                    journalDetails.particular = ApplicationConstants.CASH_RECEIVED + " as Advance from Customer: [" + customerPayment.customerMaster.code + "] " + customerPayment.customerMaster.name
                    journalDetails.dateCreated = new Date()
                    if(!journalDetails.validate()){
                        return this.getValidationErrorMessage(journalDetails)
                    }
                    journalDetailsList.add(journalDetails)
                }
            }
            /*************************** COA Entry End *********************************/
            if (totalApplied < amountReceived) {
                customerAccount = CustomerAccount.findByCustomerMaster(customerPayment.customerMaster)
                if (customerAccount) {
                    customerAccount.dateUpdated = new Date()
                    customerAccount.userUpdated = applicationUser
                    customerAccount.balance = customerAccount.balance + (amountReceived - totalApplied)
                } else {
                    customerAccount = new CustomerAccount(params)
                    customerAccount.dateCreated = new Date()
                    customerAccount.userCreated = applicationUser
                    customerAccount.balance = amountReceived - totalApplied
                }
                if (!customerAccount.validate()) {
                    return this.getValidationErrorMessage(customerAccount)
                }
            }
            Map map = [:]
            map.put('customerPayment', customerPayment)
            map.put('customerPaymentInvoiceList', customerPaymentInvoiceList)
            map.put("customerAccount", customerAccount)
            map.put('customerPaymentCurrencyDenominationList', customerPaymentCurrencyDenominationList)
            map.put("journal", journalHead)
            map.put("journalDetailsList", journalDetailsList)
            map.put('invoiceList', invoiceList)
            map.put("securityDeposit", securityDeposit)

            if (customerPaymentService.createPayment(map)) {
                return this.getMessage(customerPayment, Message.SUCCESS, "Payment record successfully saved against MR no: " + customerPayment.mrNo)
            } else {
                return this.getMessage(customerPayment, Message.ERROR, FAIL_SAVE)
            }

        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException("Exception-${ex.message}")
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}
