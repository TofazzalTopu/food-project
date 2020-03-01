package com.bits.bdfp.customer.customersettlement

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.CustomerSettlement
import com.bits.bdfp.customer.CustomerSettlementService
import com.bits.bdfp.customer.SettledInvoice
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointWarehouse
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
 * Created by mdalinaser.khan on 6/22/16.
 */
@Component("adjustWithCustomerReceivableAction")
class AdjustWithCustomerReceivableAction extends Action {
    public static final Log log = LogFactory.getLog(AdjustWithCustomerReceivableAction.class)
    private final String MESSAGE_HEADER = 'Adjust Customer Receivable'
    private final String MESSAGE_SUCCESS = 'Customer Receivable Adjusted Successfully'

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
            List<Invoice> invoiceListToBeUpdated = new ArrayList<Invoice>()
            List<SettledInvoice> settledInvoiceList = new ArrayList<SettledInvoice>()
            SettledInvoice settledInvoice = null
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService?.getCurrentUser()

            CustomerSettlement customerSettlement = new CustomerSettlement()
            customerSettlement.properties = params
            customerSettlement.userCreated = applicationUser
            customerSettlement.transactionNo = DateUtil.now()
            customerSettlement.adjustAmount = 0.00
            customerSettlement.withdrawAmount = 0.00
            if (!customerSettlement.validate()) {
                return this.getValidationErrorMessage(customerSettlement)
            }
            CustomerMaster customerMaster = customerSettlement.customerMaster
            DistributionPoint factoryDp = DistributionPoint.findByEnterpriseConfigurationAndIsFactory(customerMaster.enterpriseConfiguration, true)

            Float adjustAmount = Float.parseFloat(params.adjustAmount)
            Float adjustedAmount = 0.00
            List invoiceList = customerSettlementService.listDueInvoiceByCustomer(customerMaster.id)
            if(invoiceList && invoiceList.size() > 0){
                for (int i = 0; i < invoiceList.size(); i++){
                    Invoice invoice = Invoice.get(invoiceList[i].id)
                    if(invoice){
                        double dueAmount = Math.round(invoice.invoiceAmount - invoice.paidAmount)
                        double diff = adjustAmount - adjustedAmount
                        if(diff > 0.00){
                            if(dueAmount > diff){
                                invoice.paidAmount += diff
                                adjustedAmount += diff
                            }else if(dueAmount <= diff) {
                                invoice.paidAmount += dueAmount
                                adjustedAmount += dueAmount
                            }
                            invoice.userUpdated = applicationUser
                            invoiceListToBeUpdated.add(invoice)

                            settledInvoice = new SettledInvoice(customerSettlement: customerSettlement, invoice: invoice, settledAmount: dueAmount)
                            settledInvoiceList.add(settledInvoice)
                        }
                    }
                }
            }
            if(adjustedAmount <= 0.00){
                return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, " No invoice available to adjust. Adjusted Amount:" + customerSettlement.adjustAmount)
            }
            customerSettlement.adjustAmount = adjustedAmount
            /***************************** COA Entry Start ***************************/
            Date dateNow = new Date()
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
            String currentDay = formatDay.format(dateNow)
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

            ChartOfAccountsMapping chartOfAccountsMappingAccountsReceivable = ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_RECEIVABLE)
            if(!chartOfAccountsMappingAccountsReceivable){
                if(!chartOfAccountsMappingAccountsReceivable){
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Accounts Receivable is not mapped with Chart of Accounts")
                }
            }

            ChartOfAccountsMapping chartOfAccountsMappingAccountsPayable = ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_PAYABLE)
            if(!chartOfAccountsMappingAccountsPayable){
                if(!chartOfAccountsMappingAccountsPayable){
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Accounts Payable is not mapped with Chart of Accounts")
                }
            }

            ChartOfAccountsMapping chartOfAccountsMappingDepositToHO = ChartOfAccountsMapping.findByCoaType(COAType.DEPOSIT_TO_HO)
            if(!chartOfAccountsMappingDepositToHO){
                if(!chartOfAccountsMappingDepositToHO){
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Deposit to HO is not mapped with Chart of Accounts")
                }
            }

            if(params.selectedCustomerType == "branch"){
                CustomerMaster defaultCustomer = CustomerMaster.get(Long.parseLong(params.defaultCustomerId))
                DistributionPoint distributionPoint = DistributionPoint.get(Long.parseLong(params.distributionPoint))
                // Sales Man Customer
                if(!distributionPoint){
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "DP is not Available")
                }

                // HO Book
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsMappingSecurityDeposit.chartOfAccounts
                journalDetails.prefixCode = defaultCustomer.code
                journalDetails.postfixCode = factoryDp.code
                journalDetails.debitAmount = adjustedAmount
                journalDetails.creditAmount = 0.00
                journalDetails.particular = ApplicationConstants.RECEIVABLE_ADJUSTED + " from Customer: [" + defaultCustomer.code + "] " + defaultCustomer.name
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsMappingAccountsReceivable.chartOfAccounts
                journalDetails.prefixCode = defaultCustomer.code
                journalDetails.postfixCode = factoryDp.code
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = adjustedAmount
                journalDetails.particular = ApplicationConstants.RECEIVABLE_ADJUSTED + " from Customer: [" + defaultCustomer.code + "] " + defaultCustomer.name
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

                // Branch/DP Book
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsMappingSecurityDeposit.chartOfAccounts
                journalDetails.prefixCode = customerMaster.code
                journalDetails.postfixCode = distributionPoint.code
                journalDetails.debitAmount = adjustedAmount
                journalDetails.creditAmount = 0.00
                journalDetails.particular = ApplicationConstants.RECEIVABLE_ADJUSTED + " from Customer: [" + customerMaster.code + "] " + customerMaster.name
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsMappingAccountsReceivable.chartOfAccounts
                journalDetails.prefixCode = customerMaster.code
                journalDetails.postfixCode = distributionPoint.code
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = adjustedAmount
                journalDetails.particular = ApplicationConstants.RECEIVABLE_ADJUSTED + " from Customer: [" + customerMaster.code + "] " + customerMaster.name
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)


                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsMappingAccountsPayable.chartOfAccounts
                journalDetails.prefixCode = factoryDp.code
                journalDetails.postfixCode = distributionPoint.code
                journalDetails.debitAmount = adjustedAmount
                journalDetails.creditAmount = 0.00
                journalDetails.particular = ApplicationConstants.RECEIVABLE_ADJUSTED + " from Customer: [" + customerMaster.code + "] " + customerMaster.name
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsMappingDepositToHO.chartOfAccounts
                journalDetails.prefixCode = ''
                journalDetails.postfixCode = distributionPoint.code
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = adjustedAmount
                journalDetails.particular = ApplicationConstants.RECEIVABLE_ADJUSTED + " from Customer: [" + customerMaster.code + "] " + customerMaster.name
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)
            }else{
                // Others Customer
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsMappingSecurityDeposit.chartOfAccounts
                journalDetails.prefixCode = customerMaster.code
                journalDetails.postfixCode = factoryDp.code
                journalDetails.debitAmount = adjustedAmount
                journalDetails.creditAmount = 0.00
                journalDetails.particular = ApplicationConstants.RECEIVABLE_ADJUSTED + " from Customer: [" + customerMaster.code + "] " + customerMaster.name
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsMappingAccountsReceivable.chartOfAccounts
                journalDetails.prefixCode = customerMaster.code
                journalDetails.postfixCode = factoryDp.code
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = adjustedAmount
                journalDetails.particular = ApplicationConstants.RECEIVABLE_ADJUSTED + " from Customer: [" + customerMaster.code + "] " + customerMaster.name
                if(!journalDetails.validate()){
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)
            }

            Map map = [:]
            map.put('customerSettlement', customerSettlement)
            map.put('settledInvoiceList', settledInvoiceList)
            map.put('invoiceListToBeUpdated', invoiceListToBeUpdated)
            map.put("journal", journalHead)
            map.put("journalDetailsList", journalDetailsList)

            customerSettlementService.adjustWithReceivable(map)
            return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, "Adjust with Receivable Successfully. Adjusted Amount:" + customerSettlement.adjustAmount)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}