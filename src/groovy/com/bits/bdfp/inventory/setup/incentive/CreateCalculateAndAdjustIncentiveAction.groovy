package com.bits.bdfp.inventory.setup.incentive

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.customer.CustomerLevel
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.finance.AdjustMiscellaneousFeesWithReceivableService
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.demandorder.InvoiceDetails
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointWarehouse
import com.bits.bdfp.inventory.setup.CalculateAndAdjustIncentiveService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
//import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

/**
 * Created by mdalinaser.khan on 7/31/16.
 */
@Component("createCalculateAndAdjustIncentiveAction")
class CreateCalculateAndAdjustIncentiveAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    private final String MESSAGE_HEADER = 'Message'

    @Autowired
    AdjustMiscellaneousFeesWithReceivableService adjustMiscellaneousFeesWithReceivableService
    @Autowired
    CalculateAndAdjustIncentiveService calculateAndAdjustIncentiveService
//    @Autowired
//    SessionFactory sessionFactory
    Message message

    public Object preCondition(Object params, Object object) {
        try {
            Map map = (Map) object
            List<CalculateAndAdjustIncentive> calculateAndAdjustIncentiveList = (List<CalculateAndAdjustIncentive>) map.calculateAndAdjustIncentiveList
            Boolean isTrue = true

            calculateAndAdjustIncentiveList.each{
                if (!it.validate()){
                    message = this.getValidationErrorMessage(it)
                    isTrue = false
                }
            }

            if(isTrue){
                message = this.getMessage("Calculate And Adjust Incentive With Receivable", Message.SUCCESS, SUCCESS_SAVE)
            }

            return message
        }catch (Exception ex){
            log.error(ex.message)
            return this.getMessage("Calculate And Adjust Incentive With Receivable", Message.ERROR, "Exception-${ex.message}")
        }
    }

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            Double amount = 0.00
            Double amountAdjusted = 0.00
            Double incentiveAmount = 0.00
            String msg = ""

            List<Journal> journalList = []
            List<JournalDetails> journalDetailsList = []
            List<Invoice> invoiceUpdateList = []
            List<Invoice> calculatedInvoiceList = []
            List<InvoiceDetails> calculatedInvoiceDetailsList = []
            List<CalculateAndAdjustIncentive> calculateAndAdjustIncentiveList = []
            List<UnadjustedIncentive> unadjustedIncentiveList = []
/*
            List<TargetBasedIncentiveCustomers> targetBasedIncentiveCustomersList = []
            List<SalesAmountBasedIncentiveCustomers> salesAmountBasedIncentiveCustomersList = []
            List<QuantityBasedIncentiveCustomers> quantityBasedIncentiveCustomersList = []
            List<VolumeBasedIncentiveCustomers> volumeBasedIncentiveCustomersList = []

            */


            Map map = [:]

            params.allIncentive.each { key, val ->
                if (val instanceof Map) {
                    CalculateAndAdjustIncentive calculateAndAdjustIncentive = new CalculateAndAdjustIncentive(val)
                    calculateAndAdjustIncentive.userCreated = applicationUser
                    calculateAndAdjustIncentive.dateCreated = new Date()

                    incentiveAmount = Double.parseDouble(val.incentiveAmount)
                    amount = Double.parseDouble(val.incentiveAmount)
                    CustomerMaster customerMaster = CustomerMaster.get(calculateAndAdjustIncentive.customer.id)
                    List invoiceList = adjustMiscellaneousFeesWithReceivableService.getInvoiceListByCustomerId(customerMaster.id)

                    EnterpriseConfiguration enterpriseConfiguration = EnterpriseConfiguration.read(customerMaster.enterpriseConfiguration.id)
                    DistributionPoint factoryDp = DistributionPoint.findByEnterpriseConfigurationAndIsFactory(enterpriseConfiguration, true)
                    List list = null
                    if(customerMaster.categoryId == ApplicationConstants.CUSTOMER_CATEGORY_SALES_MAN_ID) {
                        list = calculateAndAdjustIncentiveService.fetchBranch(customerMaster.id)
                    }

                    UnadjustedIncentive unadjustedIncentiveRow = UnadjustedIncentive.findByCustomerAndIsActive(customerMaster,true)
                    if(unadjustedIncentiveRow){
                        incentiveAmount += unadjustedIncentiveRow.unadjustedAmount
                        amount += unadjustedIncentiveRow.unadjustedAmount
                        unadjustedIncentiveRow.isActive = false
                        unadjustedIncentiveRow.userUpdated = applicationUser
                        unadjustedIncentiveRow.lastUpdated = new Date()
                        unadjustedIncentiveList.add(unadjustedIncentiveRow)
                    }

                    if (invoiceList.size() > 0) {
                        for (int i = 0; i < invoiceList.size(); i++) {
                            if (amount > 0) {
                                Invoice invoice = Invoice.get(invoiceList[i].id)
                                invoice.userUpdated = applicationUser
                                invoice.lastUpdated = new Date()

                                if ((amount >= (invoice.invoiceAmount - invoice.paidAmount)) && ((invoice.invoiceAmount - invoice.paidAmount) > 0)) {
                                    amount = amount - (invoice.invoiceAmount - invoice.paidAmount)
                                    invoice.paidAmount += (invoice.invoiceAmount - invoice.paidAmount)
                                    invoiceUpdateList.add(invoice)
                                } else {
                                    invoice.paidAmount += amount
                                    amount = 0

                                    invoiceUpdateList.add(invoice)
                                }
                            } else {
                                break
                            }
                        }
                        amountAdjusted = incentiveAmount - amount
                        if(amount > 0){
                            UnadjustedIncentive unadjustedIncentive = new UnadjustedIncentive()
                            unadjustedIncentive.incentiveAmount = incentiveAmount
                            unadjustedIncentive.unadjustedAmount = amount
                            unadjustedIncentive.adjustedAmount = amountAdjusted
                            unadjustedIncentive.customer = customerMaster
                            unadjustedIncentive.incentiveType = calculateAndAdjustIncentive.incentiveType
                            unadjustedIncentive.incentiveProgram = calculateAndAdjustIncentive.incentiveProgram
                            unadjustedIncentive.isActive = true
                            unadjustedIncentive.userCreated = applicationUser
                            unadjustedIncentive.dateCreated = new Date()
                            unadjustedIncentiveList.add(unadjustedIncentive)
                        }

                        String[] cIds = val.cIds.split(",")
                        if(calculateAndAdjustIncentive.incentiveType == 'tbi' || calculateAndAdjustIncentive.incentiveType == 'sabi'){
                            for(int i=0; i<cIds.length; i++){
                                Invoice invoice = Invoice.get(Long.parseLong(cIds[i]))
                                invoice.isIncentiveCalculated = true
                                invoice.userUpdated = applicationUser
                                invoice.lastUpdated = new Date()
                                calculatedInvoiceList.add(invoice)
                            }
                        }else{
                            for(int i=0; i<cIds.length; i++){
                                InvoiceDetails invoiceDetails = InvoiceDetails.get(Long.parseLong(cIds[i]))
                                invoiceDetails.isIncentiveCalculated = true
                                calculatedInvoiceDetailsList.add(invoiceDetails)
                            }
                        }
                    }else{
                        if(msg){
                            msg += ', '+customerMaster.name+'['+customerMaster.code+']'
                        }else{
                            msg = "No unadjusted invoice found for the customer "+customerMaster.name+"["+customerMaster.code+"]"
                        }

                        return message = this.getMessage(MESSAGE_HEADER, Message.ERROR, "No unadjusted invoice found for the customer"+customerMaster.name+'['+customerMaster.code+']')
                    }
/*
                    if (calculateAndAdjustIncentive.incentiveType == 'tbi') {
                        TargetBasedIncentiveCustomers targetBasedIncentiveCustomers = TargetBasedIncentiveCustomers.findByCustomerMaster(customerMaster)
                        targetBasedIncentiveCustomers.sattlementStatus = 'SETTLED'
                        targetBasedIncentiveCustomers.userUpdated = applicationUser
                        targetBasedIncentiveCustomers.lastUpdated = new Date()
                        targetBasedIncentiveCustomersList.add(targetBasedIncentiveCustomers)
                    } else if (calculateAndAdjustIncentive.incentiveType == 'sabi') {
                        SalesAmountBasedIncentiveCustomers salesAmountBasedIncentiveCustomers = SalesAmountBasedIncentiveCustomers.findByCustomerMaster(customerMaster)
                        salesAmountBasedIncentiveCustomers.sattlementStatus = 'SETTLED'
                        salesAmountBasedIncentiveCustomers.userUpdated = applicationUser
                        salesAmountBasedIncentiveCustomersList.add(salesAmountBasedIncentiveCustomers)
                    } else if (calculateAndAdjustIncentive.incentiveType == 'qbi') {
                        QuantityBasedIncentiveCustomers quantityBasedIncentiveCustomers = QuantityBasedIncentiveCustomers.findByCustomerMaster(customerMaster)
                        quantityBasedIncentiveCustomers.sattlementStatus = 'SETTLED'
                        quantityBasedIncentiveCustomers.userUpdated = applicationUser
                        quantityBasedIncentiveCustomersList.add(quantityBasedIncentiveCustomers)
                    } else if (calculateAndAdjustIncentive.incentiveType == 'vbi') {
                        VolumeBasedIncentiveCustomers volumeBasedIncentiveCustomers = VolumeBasedIncentiveCustomers.findByCustomerMaster(calculateAndAdjustIncentive)
                        volumeBasedIncentiveCustomers.sattlementStatus = 'SETTLED'
                        volumeBasedIncentiveCustomers.userUpdated = applicationUser
                        volumeBasedIncentiveCustomers.lastUpdated = new Date()
                        volumeBasedIncentiveCustomersList.add(volumeBasedIncentiveCustomers)
                    }

                    */

                    DistributionPointWarehouse distributionPointWarehouse = DistributionPointWarehouse.findByDefaultCustomer(customerMaster)
                    DistributionPoint distributionPoint = null
                    if(distributionPointWarehouse){
                        distributionPoint = DistributionPoint.findByIdAndIsFactory(distributionPointWarehouse.distributionPoint.id,false)
                    }

                    Date dateNow = new Date()
                    SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
                    String currentMonth = formatMonth.format(dateNow)
                    SimpleDateFormat formatYear = new SimpleDateFormat("YY")
                    String currentYear = formatYear.format(dateNow)
                    SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
                    String currentDay = formatDay.format(dateNow)

                    /***************************** COA Entry Start ***************************/
                    JournalDetails journalDetails = null
                    ChartOfAccountsMapping chartOfAccountsMapping = null

                    Journal journalHead = new Journal(enterprise: enterpriseConfiguration, userCreated: applicationUser, isActive: true)
                    journalHead.tableName =  "calculate_and_adjust_incentive"// sessionFactory.getClassMetadata(CalculateAndAdjustIncentive).tableName
                    journalHead.transactionDate = new Date()
                    journalHead.journalStatus = JournalStatus.CHECKED
                    journalHead.transactionNo = CodeGenerationUtil.generate(8).toString()
                    journalHead.journalNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
                    journalHead.moduleName = ApplicationConstants.CALCULATE_ANDADJUST_INCENTIVE
                    if(!journalHead.validate()){
                        return this.getValidationErrorMessage(journalHead)
                    }
                    journalList.add(journalHead)

                    String particular = ""
                    journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                    if(distributionPointWarehouse && distributionPoint){
                        chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.SALES_INCENTIVE)
                        if(!chartOfAccountsMapping){
                            return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Sales incentive head is not mapped with Chart of Accounts")
                        }
                        particular = "Sales Incentive"
                        journalDetails.prefixCode = ''
                        journalDetails.postfixCode = factoryDp.code
                    }else if(customerMaster.category && customerMaster.category.name == 'Sales Man'){
                        chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_PAYABLE)
                        if(!chartOfAccountsMapping){
                            return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Accounts payable head is not mapped with Chart of Accounts")
                        }
                        particular = "Accounts Payable"
                        journalDetails.prefixCode = customerMaster.code
                        journalDetails.postfixCode = list[0].code
                    }else if(customerMaster.customerLevel == CustomerLevel.PRIMARY){
                        chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.MARKETING_EXPENSE)
                        if(!chartOfAccountsMapping){
                            return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Marketing expense head is not mapped with Chart of Accounts")
                        }
                        particular = "Marketing Expense"
                        journalDetails.prefixCode = ''
                        journalDetails.postfixCode = factoryDp.code
                    }

                    journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                    journalDetails.debitAmount = amountAdjusted
                    journalDetails.creditAmount = 0.00
                    journalDetails.particular = particular + " Debited "
                    if(!journalDetails.validate()){
                        return this.getValidationErrorMessage(journalDetails)
                    }
                    journalDetailsList.add(journalDetails)

                    journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                    if(distributionPointWarehouse && distributionPoint){
                        chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_RECEIVABLE)
                        if(!chartOfAccountsMapping){
                            return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Accounts receivable head is not mapped with Chart of Accounts")
                        }
                        particular = "Customer Account"
                        journalDetails.prefixCode = customerMaster.code
                        journalDetails.postfixCode = factoryDp.code
                    }else if(customerMaster.category && customerMaster.category.name == 'Sales Man'){
                        chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_RECEIVABLE)
                        if(!chartOfAccountsMapping){
                            return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Accounts receivable head is not mapped with Chart of Accounts")
                        }
                        particular = "Sales Man"
                        journalDetails.prefixCode = customerMaster.code
                        journalDetails.postfixCode = list[0].code
                    }else if(customerMaster.customerLevel == CustomerLevel.PRIMARY){
                        chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_RECEIVABLE)
                        if(!chartOfAccountsMapping){
                            return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Accounts receivable head is not mapped with Chart of Accounts")
                        }
                        particular = "Customer Account"
                        journalDetails.prefixCode = customerMaster.code
                        journalDetails.postfixCode = factoryDp.code
                    }

                    journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                    journalDetails.debitAmount = 0.00
                    journalDetails.creditAmount = amountAdjusted
                    journalDetails.particular = particular + " Credited "
                    if(!journalDetails.validate()){
                        return this.getValidationErrorMessage(journalDetails)
                    }
                    journalDetailsList.add(journalDetails)

                    calculateAndAdjustIncentiveList.add(calculateAndAdjustIncentive)
                }
            }
/*
            if(targetBasedIncentiveCustomersList.size()>0){
                map.put("targetBasedIncentiveCustomersList",targetBasedIncentiveCustomersList)
            }else if(salesAmountBasedIncentiveCustomersList.size()>0){
                map.put("salesAmountBasedIncentiveCustomersList",salesAmountBasedIncentiveCustomersList)
            }else if(quantityBasedIncentiveCustomersList.size()>0){
                map.put("quantityBasedIncentiveCustomersList",quantityBasedIncentiveCustomersList)
            }else if(volumeBasedIncentiveCustomersList.size()>0){
                map.put("volumeBasedIncentiveCustomersList",volumeBasedIncentiveCustomersList)
            }
*/
            map.put("calculateAndAdjustIncentiveList",calculateAndAdjustIncentiveList)
            map.put("invoiceUpdateList",invoiceUpdateList)
            map.put("calculatedInvoiceList",calculatedInvoiceList)
            map.put("calculatedInvoiceDetailsList",calculatedInvoiceDetailsList)
            map.put("unadjustedIncentiveList", unadjustedIncentiveList)
            map.put("journalList", journalList)
            map.put("journalDetailsList", journalDetailsList)

            message = this.preCondition(params,map)

            if(message.type == 1){
                int noOfRows = calculateAndAdjustIncentiveService.adjust(map)
                if (noOfRows>0){
                    message = this.getMessage("Calculate And Adjust Incentive With Receivable", Message.SUCCESS, "Calculation And Adjustment of Incentive Against Customer Receivable Completed Successfully")
                }else{
                    message = this.getMessage("Calculate And Adjust Incentive With Receivable", Message.ERROR, "Calculation And Adjustment of Incentive not Complete. Cause "+msg)
                }
            }
//            message.messageBody += ' Cause '+msg
            return  message
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}
