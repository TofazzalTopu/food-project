package com.bits.bdfp.inventory.sales.invoice

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.accounts.SubLedger
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.CustomerTerritorySubArea
import com.bits.bdfp.finance.CustomerAccount
import com.bits.bdfp.finance.CustomerPayment
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.demandorder.InvoiceDetails
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointStock
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import com.bits.bdfp.inventory.sales.DistributionPointTerritorySubArea
import com.bits.bdfp.inventory.sales.DistributionPointWarehouse
import com.bits.bdfp.inventory.sales.InvoiceService
import com.bits.bdfp.inventory.setup.PosCustomer
import com.bits.bdfp.inventory.warehouse.FinishGoodStock
import com.bits.bdfp.inventory.warehouse.FinishGoodStockTransaction
import com.bits.bdfp.inventory.warehouse.SubWarehouse
import com.bits.bdfp.inventory.warehouse.SubWarehouseType
import com.bits.bdfp.inventory.warehouse.Warehouse
import com.bits.bdfp.settings.ApplicationUserEnterprise
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsDomainBinder
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

/**
 * Created by mdalinaser.khan on 9/29/15.
 */
@Component("createInvoiceAction")
class CreateInvoiceAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    private final String MESSAGE_HEADER = 'Message'
    Message message
    @Autowired
    InvoiceService invoiceService
    @Autowired
    SessionFactory sessionFactory

    public Object preCondition(Object params, Object object) {
        return  null
    }

    public Object execute(Object params, Object object) {
        try {
            List<FinishGoodStockTransaction> finishGoodStockTransactionList = new  ArrayList<FinishGoodStockTransaction>()
            List<FinishGoodStock> finishGoodStockListToBeUpdated = new ArrayList<FinishGoodStock>()
            List<DistributionPointStockTransaction> distributionPointStockTransactionList = new  ArrayList<DistributionPointStockTransaction>()
            List<DistributionPointStock> distributionPointStockListToBeUpdated = new ArrayList<DistributionPointStock>()
            List<InvoiceDetails> invoiceDetailsList = new ArrayList<InvoiceDetails>()
            List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()
            Invoice directInvoice = new Invoice(isIncentiveCalculated: false)

            SimpleDateFormat formatter = new SimpleDateFormat(ApplicationConstants.DATE_FORMAT)
            Float totalAmount = 0
            List<SubLedger> subLedgerList = []
            ApplicationUser applicationUser = (ApplicationUser) object
            if(!params.customerId){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Customer is not selected")
            }
            int productCount = Integer.parseInt(params.productCount)
            if(productCount == 0){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Product is not selected")
            }
            CustomerMaster customerMaster = CustomerMaster.get(Long.parseLong(params.customerId))
            DistributionPoint distributionPoint = DistributionPoint.read(invoiceService.getDpByCustomerAndGeo(customerMaster.id).dpId)
//            CustomerTerritorySubArea customerTerritorySubArea = CustomerTerritorySubArea.findByCustomerMaster(customerMaster)
//            DistributionPointTerritorySubArea distributionPointTerritorySubArea = DistributionPointTerritorySubArea.findByTerritorySubArea(customerTerritorySubArea.territorySubArea)
//            PosCustomer posCustomer = PosCustomer.findByCustomerMaster(customerMaster)
//            if(!posCustomer){
//                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "[" + customerMaster.code + "] " + customerMaster.name + " is not a POS customer")
//            }
            directInvoice.distributionPoint = distributionPoint
            DistributionPointWarehouse distributionPointWarehouse = DistributionPointWarehouse.findByDistributionPoint(distributionPoint)
            if(!distributionPointWarehouse){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "No DP available on this customer")
            }
            Warehouse warehouse = distributionPointWarehouse.warehouse
            SubWarehouseType subWarehouseTypeSalable = SubWarehouseType.get(ApplicationConstants.SALABLE_TYPE_INVENTORY_ID)
            SubWarehouse subWarehouse = SubWarehouse.findWhere(warehouse: warehouse, subWarehouseType: subWarehouseTypeSalable)
            if(!subWarehouse){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Sub Inventory (Salable) is not Available to Process Direct Invoice")
            }

            for(int i= 0 ; i < productCount; i++){
                long productId = Long.parseLong(params["invoiceDetails_" + i + "_finishProductId"])
                FinishProduct finishProduct = FinishProduct.get(productId)
                float unitPrice = Float.parseFloat(params["invoiceDetails_" + i + "_unitPrice"])
                float quantity = Float.parseFloat(params["invoiceDetails_" + i + "_quantity"])
                totalAmount += quantity * unitPrice
                String batchDetails = params["invoiceDetails_" + i + "_batchData"]
                if(distributionPointWarehouse.distributionPoint.isFactory){
                    // Process Factory Stock
                    if(batchDetails != ""){
                        // Allocate Batch Wise Factory Stock
                        String[] batchList = batchDetails.split(';')
                        for(int j = 0 ; j < batchList.size(); j++){
                            String batchData = batchList[j].split('_')
                            long finishGoodStockId = Long.parseLong(batchData[0])
                            float batchQty = Float.parseFloat(batchData[1])
                            FinishGoodStock finishGoodStock = FinishGoodStock.get(finishGoodStockId)
                            finishGoodStock.outQuantity += batchQty
                            finishGoodStock.userUpdated = applicationUser
                            finishGoodStockListToBeUpdated.add(finishGoodStock)

                            FinishGoodStockTransaction finishGoodStockTransaction = new FinishGoodStockTransaction()
                            finishGoodStockTransaction.unitPrice = unitPrice
                            finishGoodStockTransaction.finishGoodStock = finishGoodStock
                            finishGoodStockTransaction.userCreated = applicationUser
                            finishGoodStockTransaction.inQuantity = 0
                            finishGoodStockTransaction.outQuantity = batchQty
                            finishGoodStockTransaction.finishGoodWarehouseDetails = null
                            finishGoodStockTransaction.outInvoice = directInvoice
                            finishGoodStockTransaction.transactionDate = new Date()
                            finishGoodStockTransactionList.add(finishGoodStockTransaction)
                        }
                    }else{
                        // Allocate FIFO Batch Stock (Auto)
                        List<FinishGoodStock> finishGoodStockList = FinishGoodStock.withCriteria() {
                            eq("subWarehouse", subWarehouse)
                            eq("finishProduct", finishProduct)
                            gtProperty("inQuantity", "outQuantity")
                            order("id", "asc")
                        }
                        if(!finishGoodStockList){
                            message = this.getMessage(MESSAGE_HEADER, Message.ERROR, '' + finishProduct.name + " is out of stock.")
                            return message
                        }
                        float tempQty = 0
                        int totalItems = finishGoodStockList.size()
                        for (int j = 0; j < totalItems; j++) {
                            if (quantity > 0) {
                                FinishGoodStockTransaction finishGoodStockTransaction = new FinishGoodStockTransaction()
                                finishGoodStockTransaction.unitPrice = unitPrice
                                finishGoodStockTransaction.finishGoodStock = finishGoodStockList[j]
                                finishGoodStockTransaction.userCreated = applicationUser
                                finishGoodStockTransaction.inQuantity = 0
                                finishGoodStockTransaction.finishGoodWarehouseDetails = null
                                finishGoodStockTransaction.outInvoice = directInvoice
                                finishGoodStockTransaction.transactionDate = new Date()
                                finishGoodStockList[j].userUpdated = applicationUser
                                finishGoodStockListToBeUpdated.add(finishGoodStockList[j])
                                tempQty = quantity
                                quantity -= (finishGoodStockList[j].inQuantity - finishGoodStockList[j].outQuantity)
                                if (quantity >= 0) {
                                    finishGoodStockTransaction.outQuantity = finishGoodStockList[j].inQuantity - finishGoodStockList[j].outQuantity
                                    finishGoodStockList[j].outQuantity = finishGoodStockList[j].inQuantity
                                    finishGoodStockTransactionList.add(finishGoodStockTransaction)
                                } else {
                                    finishGoodStockTransaction.outQuantity = tempQty
                                    finishGoodStockList[j].outQuantity += tempQty
                                    finishGoodStockTransactionList.add(finishGoodStockTransaction)
                                    break
                                }
                            }
                        }
                    }

                    InvoiceDetails invoiceDetails = null
                    finishGoodStockTransactionList.each {  finishGoodStockTransaction ->
                        if(!finishGoodStockTransaction.validate()){
                            throw new RuntimeException(this.getValidationErrorMessage(finishGoodStockTransaction).messageBody[0].toString())
                        }
                        // Prepare Invoice Details
                        invoiceDetails = new InvoiceDetails(isIncentiveCalculated: false)
                        invoiceDetails.batchNumber = finishGoodStockTransaction.finishGoodStock.batchNo
                        invoiceDetails.finishProduct = finishGoodStockTransaction.finishGoodStock.finishProduct
                        invoiceDetails.invoice = directInvoice
                        invoiceDetails.quantity = finishGoodStockTransaction.outQuantity
                        invoiceDetails.unitPrice = finishGoodStockTransaction.unitPrice
                        if(!invoiceDetails.validate()){
                            throw new RuntimeException(this.getValidationErrorMessage(invoiceDetails).messageBody[0].toString())
                        }
                        invoiceDetailsList.add(invoiceDetails)
                    }
                } else {
                    // Process DP Stock
                    if(batchDetails != ""){
                        // Allocate Batch Wise Factory Stock
                        String[] batchList = batchDetails.split(';')
                        for(int j = 0 ; j < batchList.size(); j++){
                            String batchData = batchList[j].split('_')
                            long distributionPointStockId = Long.parseLong(batchData[0])
                            float batchQty = Float.parseFloat(batchData[1])
                            DistributionPointStock distributionPointStock = DistributionPointStock.get(distributionPointStockId)
                            distributionPointStock.outQuantity += batchQty
                            distributionPointStock.userUpdated = applicationUser
                            distributionPointStockListToBeUpdated.add(distributionPointStock)

                            DistributionPointStockTransaction distributionPointStockTransaction = new DistributionPointStockTransaction()
                            distributionPointStockTransaction.unitPrice = unitPrice
                            distributionPointStockTransaction.distributionPointStock = distributionPointStock
                            distributionPointStockTransaction.userCreated = applicationUser
                            distributionPointStockTransaction.inQuantity = 0
                            distributionPointStockTransaction.outQuantity = batchQty
                            distributionPointStockTransaction.outInvoice = directInvoice
                            distributionPointStockTransaction.transactionDate = new Date()
                            distributionPointStockTransactionList.add(distributionPointStockTransaction)
                        }
                    }else{
                        // Allocate FIFO Batch Stock (Auto)
                        List<DistributionPointStock> distributionPointStockList = DistributionPointStock.withCriteria() {
                            eq("subWarehouse", subWarehouse)
                            eq("finishProduct", finishProduct)
                            gtProperty("inQuantity", "outQuantity")
                            order("id", "asc")
                        }
                        if(!distributionPointStockList){
                            message = this.getMessage(MESSAGE_HEADER, Message.ERROR, '' + finishProduct.name + " is out of stock.")
                            return message
                        }
                        float tempQty = 0
                        int totalItems = distributionPointStockList.size()
                        for (int j = 0; j < totalItems; j++) {
                            if (quantity > 0) {
                                DistributionPointStockTransaction distributionPointStockTransaction = new DistributionPointStockTransaction()
                                distributionPointStockTransaction.unitPrice = unitPrice
                                distributionPointStockTransaction.distributionPointStock = distributionPointStockList[j]
                                distributionPointStockTransaction.userCreated = applicationUser
                                distributionPointStockTransaction.inQuantity = 0
                                distributionPointStockTransaction.outInvoice = directInvoice
                                distributionPointStockTransaction.transactionDate = new Date()
                                distributionPointStockList[j].userUpdated = applicationUser
                                distributionPointStockListToBeUpdated.add(distributionPointStockList[j])
                                tempQty = quantity
                                quantity -= (distributionPointStockList[j].inQuantity - distributionPointStockList[j].outQuantity)
                                if (quantity >= 0) {
                                    distributionPointStockTransaction.outQuantity = distributionPointStockList[j].inQuantity - distributionPointStockList[j].outQuantity
                                    distributionPointStockList[j].outQuantity = distributionPointStockList[j].inQuantity
                                    distributionPointStockTransactionList.add(distributionPointStockTransaction)
                                } else {
                                    distributionPointStockTransaction.outQuantity = tempQty
                                    distributionPointStockList[j].outQuantity += tempQty
                                    distributionPointStockTransactionList.add(distributionPointStockTransaction)
                                    break
                                }
                            }
                        }
                    }

                    InvoiceDetails invoiceDetails = null
                    finishGoodStockTransactionList.each {  finishGoodStockTransaction ->
                        if(!finishGoodStockTransaction.validate()){
                            throw new RuntimeException(this.getValidationErrorMessage(finishGoodStockTransaction).messageBody[0].toString())
                        }
                        // Prepare Invoice Details
                        invoiceDetails = new InvoiceDetails(isIncentiveCalculated: false)
                        invoiceDetails.batchNumber = finishGoodStockTransaction.finishGoodStock.batchNo
                        invoiceDetails.finishProduct = finishGoodStockTransaction.finishGoodStock.finishProduct
                        invoiceDetails.invoice = directInvoice
                        invoiceDetails.quantity = finishGoodStockTransaction.outQuantity
                        invoiceDetails.unitPrice = finishGoodStockTransaction.unitPrice
                        if(!invoiceDetails.validate()){
                            throw new RuntimeException(this.getValidationErrorMessage(invoiceDetails).messageBody[0].toString())
                        }
                        invoiceDetailsList.add(invoiceDetails)
                    }

                    distributionPointStockTransactionList.each {  distributionPointStockTransaction ->
                        if(!distributionPointStockTransaction.validate()){
                            throw new RuntimeException(this.getValidationErrorMessage(distributionPointStockTransaction).messageBody[0].toString())
                        }
                        // Prepare Invoice Details
                        invoiceDetails = new InvoiceDetails(isIncentiveCalculated: false)
                        invoiceDetails.batchNumber = distributionPointStockTransaction.distributionPointStock.batchNo
                        invoiceDetails.finishProduct = distributionPointStockTransaction.distributionPointStock.finishProduct
                        invoiceDetails.invoice = directInvoice
                        invoiceDetails.quantity = distributionPointStockTransaction.outQuantity
                        invoiceDetails.unitPrice = distributionPointStockTransaction.unitPrice
                        if(!invoiceDetails.validate()){
                            throw new RuntimeException(this.getValidationErrorMessage(invoiceDetails).messageBody[0].toString())
                        }
                        invoiceDetailsList.add(invoiceDetails)
                    }
                }
            }

            ApplicationUserEnterprise applicationUserEnterprise = ApplicationUserEnterprise.findByApplicationUser(applicationUser)
            if(!applicationUserEnterprise){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "User is not in any Enterprise")
            }
            Date dateNow = new Date()
            SimpleDateFormat formatMonth = new SimpleDateFormat ("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat ("YY")
            String currentYear = formatYear.format(dateNow)
            SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
            String currentDay = formatDay.format(dateNow)
            EnterpriseConfiguration enterpriseConfiguration = applicationUserEnterprise.enterpriseConfiguration

            directInvoice.code = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "DIRECT_INVOICE", "", "", "", "", "", currentMonth, currentYear, "", "")

            Float paymentReceived = Float.parseFloat(params.paymentReceived)

            //Payment record
            CustomerPayment customerPayment = new CustomerPayment()
            customerPayment.customerMaster = customerMaster
            customerPayment.amount = paymentReceived
            customerPayment.confirmAmount = paymentReceived
            customerPayment.paymentMode = params.paymentMode
            customerPayment.isSecurityDeposit = false
            customerPayment.invoices = directInvoice.code
            customerPayment.invoiceIssueDates = formatter.format(dateNow)
            customerPayment.isDeposited = false
            customerPayment.refNo = params.transactionNo
            customerPayment.transNo = CodeGenerationUtil.generate(8).toString()
            customerPayment.mrNo = CodeGenerationUtil.generate(8).toString()
            if(directInvoice.transactionDate){
                customerPayment.dateTransaction = directInvoice.transactionDate
            }
            else{
                customerPayment.dateTransaction = new Date()
            }
            customerPayment.dateCreated = new Date()
            customerPayment.userCreated = applicationUser


            /********************** COA Entry Start ************************/
            Journal journalHead = new Journal(enterprise: enterpriseConfiguration, userCreated: applicationUser)
            journalHead.tableName = sessionFactory.getClassMetadata(Invoice).tableName
//                    GrailsDomainBinder.getMapping(Invoice).table.name
            journalHead.transactionDate = new Date()
            journalHead.journalStatus = JournalStatus.CHECKED
            journalHead.transactionNo = directInvoice.code
            journalHead.journalNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
            journalHead.moduleName = ApplicationConstants.MODULE_SALES
            if(!journalHead.validate()){
                throw new RuntimeException(this.getValidationErrorMessage(journalHead).messageBody[0].toString())
            }

            JournalDetails journalDetails = null
            ChartOfAccountsMapping chartOfAccountsMapping = null

            // Add Product wise Journal Details
            for(int k = 0; k < invoiceDetailsList.size(); k++){
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser)
                journalDetails.chartOfAccounts = invoiceDetailsList[k].finishProduct.chartOfAccountHead
                journalDetails.prefixCode = ""
                journalDetails.postfixCode = directInvoice.distributionPoint.code
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = invoiceDetailsList[k].unitPrice * invoiceDetailsList[k].quantity
                journalDetails.particular = ApplicationConstants.SALES
                if(!journalDetails.validate()){
                    throw new RuntimeException(this.getValidationErrorMessage(journalDetails).messageBody[0].toString())
                }
                journalDetailsList.add(journalDetails)
            }

            float actualOtherChargeValue = 0

            if(params.actualOtherChargeValue){
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser)
                chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.SERVICE_CHARGE)
                if(!chartOfAccountsMapping){
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Service Charge head is not mapped with Chart of Accounts")
                }
                journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                journalDetails.prefixCode = ""
                journalDetails.postfixCode = directInvoice.distributionPoint.code
                actualOtherChargeValue = Float.parseFloat(params.actualOtherChargeValue)
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = actualOtherChargeValue
                journalDetails.particular = directInvoice.otherChargeName
                if(!journalDetails.validate()){
                    throw new RuntimeException(this.getValidationErrorMessage(journalDetails).messageBody[0].toString())
                }
                journalDetailsList.add(journalDetails)
            }

            float actualDiscountValue =  0

            if(params.actualDiscountValue){
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser)
                chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.DISCOUNT)
                if(!chartOfAccountsMapping){
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Discount head is not mapped with Chart of Accounts")
                }
                journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                journalDetails.prefixCode = ""
                journalDetails.postfixCode = directInvoice.distributionPoint.code
                actualDiscountValue = Float.parseFloat(params.actualDiscountValue)
                journalDetails.debitAmount = actualDiscountValue
                journalDetails.creditAmount = 0.00
                journalDetails.particular = directInvoice.discountName
                if(!journalDetails.validate()){
                    throw new RuntimeException(this.getValidationErrorMessage(journalDetails).messageBody[0].toString())
                }
                journalDetailsList.add(journalDetails)
            }

            float actualVatValue = 0
            if(params.actualVatValue){
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser)
                chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.VAT_CURRENT_ACCOUNT)
                if(!chartOfAccountsMapping){
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "VAT head is not mapped with Chart of Accounts")
                }
                journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                journalDetails.prefixCode = ""
                journalDetails.postfixCode = directInvoice.distributionPoint.code
                actualVatValue = Float.parseFloat(params.actualVatValue)
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = actualVatValue
                journalDetails.particular = directInvoice.otherChargeName
                if(!journalDetails.validate()){
                    throw new RuntimeException(this.getValidationErrorMessage(journalDetails).messageBody[0].toString())
                }
                journalDetailsList.add(journalDetails)
            }


            journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser)
            chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.CASH)
            if(!chartOfAccountsMapping){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Cash head is not mapped with Chart of Accounts")
            }
            journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
            journalDetails.prefixCode = ""
            journalDetails.postfixCode = directInvoice.distributionPoint.code
            journalDetails.debitAmount = totalAmount + actualOtherChargeValue + actualVatValue - actualDiscountValue
            journalDetails.creditAmount = 0.00
            journalDetails.particular = ApplicationConstants.CASH_RECEIVED;
            journalDetailsList.add(journalDetails)

            /********************************* COA Entry End ***************/
            /*
            //sub ledger
            SubLedger subLedger = new SubLedger()
            subLedger.vin = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "VOUCHER", "", "", "", "", "", currentMonth, currentYear, "", "")
            subLedger.accCode = "212330069"
            subLedger.credit = totalAmount
            subLedger.debit = 0.0
            subLedger.description = "Sales"
            subLedger.isActive = true
            subLedger.transactionNo = directInvoice.code
            subLedger.transactionType = 1
            subLedger.userCreated = applicationUser
            subLedger.dateCreated = new Date()
            subLedgerList.add(subLedger)
            subLedger = new SubLedger()
            subLedger.vin = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "VOUCHER", "", "", "", "", "", currentMonth, currentYear, "", "")
            subLedger.accCode = "9513"
            subLedger.credit = 0.0
            subLedger.debit = totalAmount
            subLedger.description = "cash Received"
            subLedger.isActive = true
            subLedger.transactionNo = directInvoice.code
            subLedger.transactionType = 1
            subLedger.userCreated = applicationUser
            subLedger.dateCreated = new Date()
            subLedgerList.add(subLedger)

            float actualOtherChargeValue = 0

            if(params.actualOtherChargeValue){
                subLedger = new SubLedger()
                subLedger.vin = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "VOUCHER", "", "", "", "", "",currentMonth, currentYear, "", "")
                subLedger.accCode = "222555666"
                actualOtherChargeValue = Float.parseFloat(params.actualOtherChargeValue)
                subLedger.credit = actualOtherChargeValue
                subLedger.debit = 0.0
                subLedger.description = "Service Charge"
                subLedger.isActive = true
                subLedger.transactionNo = directInvoice.code
                subLedger.transactionType = 1
                subLedger.userCreated = applicationUser
                subLedger.dateCreated = new Date()
                subLedgerList.add(subLedger)
            }

            float actualDiscountValue =  0

            if(params.actualDiscountValue){
                subLedger = new SubLedger()
                subLedger.vin = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "VOUCHER", "", "", "", "", "",currentMonth, currentYear, "", "")
                subLedger.accCode = "222555777"
                actualDiscountValue = Float.parseFloat(params.actualDiscountValue)
                subLedger.credit = actualDiscountValue
                subLedger.debit = 0.0
                subLedger.description = "Discount Charge"
                subLedger.isActive = true
                subLedger.transactionNo = directInvoice.code
                subLedger.transactionType = 1
                subLedger.userCreated = applicationUser
                subLedger.dateCreated = new Date()
                subLedgerList.add(subLedger)
            }

            float actualVatValue = 0
            if(params.actualVatValue){
                subLedger = new SubLedger()
                subLedger.vin = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "VOUCHER", "", "", "", "", "", currentMonth, currentYear, "", "")
                subLedger.accCode = "222555999"
                actualVatValue = Float.parseFloat(params.actualVatValue)
                subLedger.credit = actualVatValue
                subLedger.debit = 0.0
                subLedger.description = "Vat/Tax Charge"
                subLedger.isActive = true
                subLedger.transactionNo = directInvoice.code
                subLedger.transactionType = 1
                subLedger.userCreated = applicationUser
                subLedger.dateCreated = new Date()
                subLedgerList.add(subLedger)
            }
            */

            //invoice details
            directInvoice.invoiceAmount = totalAmount
            directInvoice.serviceCharge = actualOtherChargeValue
            directInvoice.discount = actualDiscountValue
            directInvoice.vat = actualVatValue
            directInvoice.ait = 0.0
            directInvoice.paidAmount = paymentReceived
            directInvoice.defaultCustomer = customerMaster
            directInvoice.userCreated = applicationUser
            directInvoice.dateCreated = new Date()
            directInvoice.isActive = true
            directInvoice.isDirectInvoice = true

            //external customer infor for pos customer
            directInvoice.externalCustomerName = params.externalCustomerName
            directInvoice.externalCustomerMobile = params.externalCustomerMobile
            directInvoice.externalCustomerAddress = params.externalCustomerAddress

            //payment method details
            if(params.transactionDate){
                directInvoice.transactionDate = formatter.parse(params.transactionDate)
            }

            directInvoice.transactionNo = params.transactionNo
            directInvoice.reference = params.reference

            //customer balance
            Double customerBalance = paymentReceived - (totalAmount + actualOtherChargeValue + actualVatValue - actualDiscountValue)

            CustomerAccount customerAccount = CustomerAccount.findByCustomerMaster(customerMaster)
            if(!customerAccount){
                customerAccount = new CustomerAccount()
                customerAccount.customerMaster = customerMaster
                customerAccount.balance = customerBalance
                customerAccount.dateCreated = new Date()
                customerAccount.userCreated = applicationUser
            }else{
                customerAccount.balance += customerBalance
                customerAccount.dateUpdated = new Date()
                customerAccount.userUpdated = applicationUser
            }

            Map mapInstance = [:]
            mapInstance.put("directInvoice", directInvoice)
            mapInstance.put("directInvoiceDetails", invoiceDetailsList)
//            mapInstance.put("subLedgerList", subLedgerList)
            mapInstance.put("finishGoodStockList", finishGoodStockListToBeUpdated)
            mapInstance.put("finishGoodStockTransactionList", finishGoodStockTransactionList)
            mapInstance.put("distributionPointStockList", distributionPointStockListToBeUpdated)
            mapInstance.put("distributionPointStockTransactionList", distributionPointStockTransactionList)
            mapInstance.put("customerAccount", customerAccount)
            mapInstance.put("customerPayment", customerPayment)
            mapInstance.put("journal", journalHead)
            mapInstance.put("journalDetailsList", journalDetailsList)

            if (invoiceService.create(mapInstance, params)) {
                message = this.getMessage(MESSAGE_HEADER, Message.SUCCESS, "Invoice Created Successfully, InvoiceNo:" + directInvoice.code)
            } else {
                message = this.getMessage(MESSAGE_HEADER, Message.ERROR, this.FAIL_SAVE)
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