package com.bits.bdfp.inventory.demandorder.processanorder

import com.bits.bdfp.accounts.*
import com.bits.bdfp.bonus.QuantityBasedBonus
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.CustomerStock
import com.bits.bdfp.customer.CustomerStockTransaction
import com.bits.bdfp.inventory.demandorder.*
import com.bits.bdfp.inventory.product.ProductPriceService
import com.bits.bdfp.inventory.sales.DistributionPointStock
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import com.bits.bdfp.inventory.sales.DistributionPointWarehouse
import com.bits.bdfp.inventory.warehouse.SubWarehouse
import com.bits.bdfp.inventory.warehouse.SubWarehouseType
import com.bits.bdfp.inventory.warehouse.Warehouse
import com.bits.bdfp.promotion.AdjustBonusPromotionWithInvoice
import com.bits.bdfp.promotion.PromotionPackage
import com.bits.bdfp.promotion.PromotionPackageProducts
import com.bits.bdfp.promotion.PromotionPackagePromotionalProducts
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

@Component("processSecondaryOrderServiceAction")
class ProcessSecondaryOrderServiceAction extends Action {
//    static transactional = false
    public static final Log log = LogFactory.getLog(this)
    private final String MESSAGE_HEADER = 'Message'
    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService
    @Autowired
    ProcessOrderService processOrderService
    @Autowired
    ProductPriceService productPriceService
    @Autowired
    SpringSecurityService springSecurityService
    @Autowired
    SessionFactory sessionFactory
    Message message

    public Object preCondition(Object params, Object object) {
        return null
    }

    public Object execute(Object params, Object object) {
        String successResult = ""
        String unSuccessResult = ""
        try {
            Date dateNow = new Date()
            Float totalInvoiceAmount = 0
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
            String currentDay = formatDay.format(dateNow)
            SecondaryDemandOrder secondaryDemandOrder = null
            String secondaryOrderIds = params.orderIds
            String[] secondaryOrderIdList = secondaryOrderIds.split(",")
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()

            if(!params.warehouseId){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Factory Inventory is not Selected to Process Primary Order(s)")
            }
            Warehouse warehouse = Warehouse.get(Long.parseLong(params.warehouseId))
            SubWarehouseType subWarehouseTypeSalable = SubWarehouseType.get(ApplicationConstants.SALABLE_TYPE_INVENTORY_ID)
            SubWarehouse subWarehouse = SubWarehouse.findWhere(warehouse: warehouse, subWarehouseType: subWarehouseTypeSalable)
            if(!subWarehouse){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Sub Inventory is not Available to Secondary Primary Order(s)")
            }
            for(int i = 0; i < secondaryOrderIdList.length; i++){
                totalInvoiceAmount = 0
//                List<SubLedger> subLedgerList = []
                List<DistributionPointStockTransaction> distributionPointStockTransactionList = new  ArrayList<DistributionPointStockTransaction>()
                List<DistributionPointStock> distributionPointStockListToBeUpdated = new ArrayList<DistributionPointStock>()
                List<CustomerStock> customerStockList = new ArrayList<CustomerStock>()
                List<CustomerStockTransaction> customerStockTransactionList = new ArrayList<CustomerStockTransaction>()
                List<QuantityBasedBonus> quantityBasedBonusList = new ArrayList<QuantityBasedBonus>()
                secondaryDemandOrder = SecondaryDemandOrder.get(Long.parseLong(secondaryOrderIdList[i]))
                if(secondaryDemandOrder){
                    List secondaryOrderDetailsList = processOrderService.secondaryOrderDetailsList(secondaryDemandOrder, warehouse.id, subWarehouseTypeSalable.id)
                    Boolean isDemandMeet = true
                    if (secondaryOrderDetailsList && secondaryOrderDetailsList.size() > 0) {
                        for (int j = 0; j < secondaryOrderDetailsList.size(); j++) {
                            if (secondaryOrderDetailsList[j].order_qty > secondaryOrderDetailsList[j].qty) {
                                isDemandMeet = false
                                break;
                            }
                        }
                    } else {
                        isDemandMeet = false
                    }
                    if(isDemandMeet){
                        // Prepare Retail Invoice
                        Invoice secondaryInvoice = new Invoice(isIncentiveCalculated: false)
                        secondaryInvoice.secondaryDemandOrder = secondaryDemandOrder
                        // Get DP
                        DistributionPointWarehouse distributionPointWarehouse = DistributionPointWarehouse.findByWarehouse(warehouse)
                        if(distributionPointWarehouse){
                            secondaryInvoice.distributionPoint = distributionPointWarehouse.distributionPoint
                        }
                        secondaryInvoice.userCreated = applicationUser
                        secondaryInvoice.transactionDate = new Date()
                        secondaryInvoice.defaultCustomer = secondaryDemandOrder.customerMaster
                        secondaryInvoice.vat = 0.0
                        secondaryInvoice.ait = 0.0
                        secondaryInvoice.discount = 0.00
                        secondaryInvoice.serviceCharge = 0.00
                        secondaryInvoice.paidAmount = 0.00
                        secondaryInvoice.isActive = true
                        secondaryInvoice.isDirectInvoice = false
                        // Generate Retail Invoice No
                        secondaryInvoice.dateCreated = new Date()
                        secondaryInvoice.code = CodeGenerationUtil.instance.generateCode(secondaryDemandOrder.customerMaster.enterpriseConfiguration, "SECONDARY_INVOICE", "", "", "", "", "", currentMonth, currentYear, "", "")

                        List<SecondaryDemandOrderDetails> secondaryDemandOrderDetailsList = SecondaryDemandOrderDetails.findAllBySecondaryDemandOrder(secondaryDemandOrder)
                        secondaryDemandOrderDetailsList.each { secondaryDemandOrderDetails ->
                            float quantity = secondaryDemandOrderDetails.quantity
                            float tempQty = 0
                            List<DistributionPointStock> distributionPointStockList = DistributionPointStock.withCriteria() {
                                eq("subWarehouse", subWarehouse)
                                eq("finishProduct", secondaryDemandOrderDetails.finishProduct)
                                gtProperty("inQuantity", "outQuantity")
                                order("id", "asc")
                            }
                            // Get updated product price
                            Map productPrice = productPriceService.getProductPriceByCustomerAndProduct(secondaryDemandOrder.userTentativeDelivery.id, secondaryDemandOrderDetails.finishProduct.id, secondaryDemandOrder.territorySubArea.id)
                            secondaryDemandOrderDetails.rate = productPrice.productPriceWithVat
                            totalInvoiceAmount += secondaryDemandOrderDetails.quantity * secondaryDemandOrderDetails.rate
                            int totalItems = distributionPointStockList.size()
                            for(int j = 0; j <totalItems; j++){
                                if(quantity > 0){
                                    DistributionPointStockTransaction distributionPointStockTransaction = new DistributionPointStockTransaction()
                                    distributionPointStockTransaction.unitPrice = secondaryDemandOrderDetails.rate
                                    distributionPointStockTransaction.distributionPointStock = distributionPointStockList[j]
                                    distributionPointStockTransaction.userCreated = applicationUser
                                    distributionPointStockTransaction.inQuantity = 0
                                    distributionPointStockTransaction.inInvoice = null
                                    distributionPointStockTransaction.outInvoice = secondaryInvoice
                                    distributionPointStockTransaction.transactionDate = new Date()
                                    distributionPointStockList[j].userUpdated = applicationUser
                                    tempQty = quantity
                                    quantity = quantity - (distributionPointStockList[j].inQuantity - distributionPointStockList[j].outQuantity)
                                    if(quantity > 0){
                                        distributionPointStockTransaction.outQuantity = distributionPointStockList[j].inQuantity - distributionPointStockList[j].outQuantity
                                        distributionPointStockList[j].outQuantity = distributionPointStockList[j].inQuantity
                                        distributionPointStockTransactionList.add(distributionPointStockTransaction)
                                        distributionPointStockListToBeUpdated.add(distributionPointStockList[j])
                                    }else {
                                        distributionPointStockTransaction.outQuantity = tempQty
                                        distributionPointStockList[j].outQuantity = distributionPointStockList[j].outQuantity + tempQty
                                        distributionPointStockTransactionList.add(distributionPointStockTransaction)
                                        distributionPointStockListToBeUpdated.add(distributionPointStockList[j])
                                        break
                                    }
                                }
                            }
                        }
                        InvoiceDetails invoiceDetails = null
                        Float totalVat = 0.00
                        List<InvoiceDetails> invoiceDetailsList = new ArrayList<InvoiceDetails>()
                        distributionPointStockTransactionList.each {  distributionPointStockTransaction ->
                            if(!distributionPointStockTransaction.validate()){
                                throw new RuntimeException(this.getValidationErrorMessage(distributionPointStockTransaction).messageBody[0].toString())
                            }
                            // Prepare Invoice Details
                            invoiceDetails = new InvoiceDetails(isIncentiveCalculated: false)
                            invoiceDetails.batchNumber = distributionPointStockTransaction.distributionPointStock.batchNo
                            invoiceDetails.finishProduct = distributionPointStockTransaction.distributionPointStock.finishProduct
                            invoiceDetails.invoice = secondaryInvoice
                            invoiceDetails.quantity = distributionPointStockTransaction.outQuantity
                            Map productPrice = productPriceService.getProductPriceByCustomerAndProduct(secondaryDemandOrder.userTentativeDelivery.id, invoiceDetails.finishProduct.id, secondaryDemandOrder.territorySubArea.id)
                            invoiceDetails.unitVat = productPrice.productPriceWithVat - productPrice.productPriceWithoutVat
                            invoiceDetails.unitPrice = distributionPointStockTransaction.unitPrice
                            invoiceDetails.dpUnitPrice = productPrice.dpPrice
                            totalVat = invoiceDetails.unitVat * invoiceDetails.quantity
                            if(!invoiceDetails.validate()){
                                throw new RuntimeException(this.getValidationErrorMessage(invoiceDetails).messageBody[0].toString())
                            }
                            invoiceDetailsList.add(invoiceDetails)

                            /************ Process Customer(Delivery Man) Stock  ***********/
                            List<CustomerStock> existingCustomerStockList = CustomerStock.withCriteria {
                                eq('deliveryMan', secondaryDemandOrder.userTentativeDelivery)
                                eq('finishProduct', invoiceDetails.finishProduct)
                                eq('batchNo', invoiceDetails.batchNumber)
                                maxResults(1)
                            }
                            CustomerStock customerStock
                            if(existingCustomerStockList.size() > 0){
                                customerStock = existingCustomerStockList[0]
                                customerStock.userUpdated = applicationUser
                                customerStock.inQuantity += invoiceDetails.quantity
                            }else{
                                customerStock = new CustomerStock()
                                customerStock.userCreated = applicationUser
                                customerStock.deliveryMan = secondaryDemandOrder.userTentativeDelivery
                                customerStock.batchNo = invoiceDetails.batchNumber
                                customerStock.inQuantity = invoiceDetails.quantity
                                customerStock.finishProduct = invoiceDetails.finishProduct
                                customerStock.outQuantity = 0
                            }
//                            CustomerStock customerStock = CustomerStock.findWhere(deliveryMan: secondaryDemandOrder.userTentativeDelivery, finishProduct: invoiceDetails.finishProduct, batchNo: invoiceDetails.batchNumber)

                            customerStockList.add(customerStock)

                            CustomerStockTransaction customerStockTransaction = new CustomerStockTransaction()
                            customerStockTransaction.customerStock = customerStock
                            customerStockTransaction.transactionDate = new Date()
                            customerStockTransaction.inInvoice = secondaryInvoice
                            customerStockTransaction.inQuantity = invoiceDetails.quantity
                            customerStockTransaction.outQuantity = 0
                            customerStockTransaction.unitPrice = invoiceDetails.unitPrice
                            customerStockTransaction.userCreated = applicationUser
                            customerStockTransactionList.add(customerStockTransaction)
                            /********************************************************/
                        }

                        secondaryInvoice.invoiceAmount = totalInvoiceAmount
                        secondaryInvoice.vat = totalVat
                        if(!secondaryInvoice.validate()){
                            throw new RuntimeException(this.getValidationErrorMessage(secondaryInvoice).messageBody[0].toString())
                        }

                        CustomerMaster customerMaster = secondaryInvoice.defaultCustomer
                        secondaryDemandOrder.demandOrderStatus = DemandOrderStatus.DELIVERED
                        secondaryDemandOrder.lastUpdated = new Date()
                        secondaryDemandOrder.userUpdated = applicationUser

                        //BONUS**************************************************************************************************
                        Invoice primaryInvoice = secondaryDemandOrder.primaryDemandOrder ? Invoice.findByPrimaryDemandOrder(secondaryDemandOrder.primaryDemandOrder) : null
                        if(primaryInvoice){
                            if(QuantityBasedBonus.findByInvoice(primaryInvoice)) {
                                for (int j = 0; j < secondaryDemandOrderDetailsList.size(); j++) {
                                    List bonusList = processOrderService.fetchBonusList(secondaryDemandOrderDetailsList[j].finishProduct.id)
                                    if (bonusList && bonusList[0].required_quantity <= secondaryDemandOrderDetailsList[j].quantity) {
                                        List stockList = processOrderService.fetchDpStockList(secondaryDemandOrderDetailsList[j].finishProduct.id)

                                        Float bonusQty = 0
                                        if (bonusList[0].is_multiplexing) {
                                            Float temp = secondaryDemandOrderDetailsList[j].quantity - (secondaryDemandOrderDetailsList[j].quantity % bonusList[0].required_quantity)
                                            bonusQty = temp / bonusList[0].required_quantity * bonusList[0].bonus_quantity
                                        } else {
                                            bonusQty = bonusList[0].bonus_quantity
                                        }

                                        yo: for (int k = 0; k < stockList.size(); k++) {
                                            QuantityBasedBonus quantityBasedBonus = new QuantityBasedBonus()
                                            quantityBasedBonus.invoice = secondaryInvoice
                                            quantityBasedBonus.finishProduct = secondaryDemandOrderDetailsList[j].finishProduct
                                            quantityBasedBonus.userCreated = applicationUser
                                            quantityBasedBonus.dateCreated = new Date()

                                            DistributionPointStock distributionPointStockTemp = DistributionPointStock.read(stockList[k].fid)
                                            if (stockList[k].quantity < bonusQty) {
                                                quantityBasedBonus.quantity = stockList[k].quantity
                                            } else {
                                                quantityBasedBonus.quantity = bonusQty
                                            }

                                            distributionPointStockTemp.outQuantity = distributionPointStockTemp.outQuantity + quantityBasedBonus.quantity
                                            quantityBasedBonus.batchNo = distributionPointStockTemp.batchNo
                                            quantityBasedBonus.rate = stockList[k].rate

                                            DistributionPointStockTransaction distributionPointStockTransactionTemp = new DistributionPointStockTransaction()
                                            distributionPointStockTransactionTemp.unitPrice = 0
                                            distributionPointStockTransactionTemp.distributionPointStock = distributionPointStockTemp
                                            distributionPointStockTransactionTemp.inQuantity = 0
                                            distributionPointStockTransactionTemp.outQuantity = quantityBasedBonus.quantity
                                            distributionPointStockTransactionTemp.outInvoice = secondaryInvoice
                                            distributionPointStockTransactionTemp.transactionDate = new Date()
                                            distributionPointStockTransactionTemp.userCreated = applicationUser
                                            distributionPointStockTransactionTemp.dateCreated = new Date()
                                            quantityBasedBonus.distributionPointStockTransaction = distributionPointStockTransactionTemp

                                            List<CustomerStock> existingCustomerStockList = CustomerStock.withCriteria {
                                                eq('deliveryMan', secondaryDemandOrder.userTentativeDelivery)
                                                eq('finishProduct', quantityBasedBonus.finishProduct)
                                                eq('batchNo', quantityBasedBonus.batchNo)
                                                maxResults(1)
                                            }

                                            CustomerStock customerStock
                                            if(existingCustomerStockList.size() > 0){
                                                customerStock = existingCustomerStockList[0]
                                                customerStock.userUpdated = applicationUser
                                                customerStock.inQuantity = customerStock.inQuantity + quantityBasedBonus.quantity
                                            }else{
                                                customerStock = new CustomerStock()
                                                customerStock.userCreated = applicationUser
                                                customerStock.deliveryMan = secondaryDemandOrder.userTentativeDelivery
                                                customerStock.batchNo = quantityBasedBonus.batchNo
                                                customerStock.inQuantity = quantityBasedBonus.quantity
                                                customerStock.finishProduct = quantityBasedBonus.finishProduct
                                                customerStock.outQuantity = 0
                                            }

                                            CustomerStockTransaction customerStockTransaction = new CustomerStockTransaction()
                                            customerStockTransaction.customerStock = customerStock
                                            customerStockTransaction.transactionDate = new Date()
                                            customerStockTransaction.inInvoice = secondaryInvoice
                                            customerStockTransaction.inQuantity = quantityBasedBonus.quantity
                                            customerStockTransaction.outQuantity = 0
                                            customerStockTransaction.unitPrice = 0
                                            customerStockTransaction.userCreated = applicationUser

                                            quantityBasedBonusList.add(quantityBasedBonus)
                                            customerStockList.add(customerStock)
                                            customerStockTransactionList.add(customerStockTransaction)
                                            distributionPointStockListToBeUpdated.add(distributionPointStockTemp)
                                            distributionPointStockTransactionList.add(distributionPointStockTransactionTemp)

                                            bonusQty = bonusQty - quantityBasedBonus.quantity
                                            if (bonusQty <= 0) {
                                                break yo
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        /***************************** COA Entry Start ***************************/
                        List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()
                        List<JournalDetails> journalDetailsListForBonus = new ArrayList<JournalDetails>()
                        JournalDetails journalDetails = null
                        ChartOfAccountsMapping chartOfAccountsMapping = null
                        Journal journalHead = new Journal(enterprise: customerMaster.enterpriseConfiguration, userCreated: applicationUser, isActive: true)
                        journalHead.tableName =  sessionFactory.getClassMetadata(Invoice).tableName
                        journalHead.transactionDate = new Date()
                        journalHead.journalStatus = JournalStatus.CHECKED
                        journalHead.transactionNo = secondaryInvoice.code
                        journalHead.journalNo = CodeGenerationUtil.instance.generateCode(customerMaster.enterpriseConfiguration, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
                        journalHead.moduleName = ApplicationConstants.MODULE_PROCESS_SECONDARY_ORDER
                        if(!journalHead.validate()){
                            return this.getValidationErrorMessage(journalHead)
                        }

                        //                        Calculate Bonus & Promotions
                        Map checkPromotion = checkPromotionBonus(secondaryDemandOrder,secondaryDemandOrderDetailsList)
                        PromotionPackage promotionPackage = checkPromotion.promotionPackage
                        List<PromotionPackagePromotionalProducts> packagePromotionalProductsList = checkPromotion.packagePromotionalProductsList
                        List<DistributionPointStock> distributionPointStockBonusPromotionList = []
                        List<DistributionPointStockTransaction> distributionPointStockTransactionBonusPromotionList = []
                        List<AdjustBonusPromotionWithInvoice> adjustBonusPromotionWithInvoiceList = []
                        int bonusCount = checkPromotion.bonusCount
                        if((checkPromotion.message.type == Message.SUCCESS) && (packagePromotionalProductsList.size() > 0) && (checkPromotion.bonusCount > 0)){
                            Message bonusMsg = null

                            SubWarehouseType subWarehouseTypeBonus = SubWarehouseType.get(ApplicationConstants.BONUS_TYPE_INVENTORY_ID)
                            SubWarehouse subWarehouseBonus = SubWarehouse.findWhere(warehouse: warehouse, subWarehouseType: subWarehouseTypeBonus)
                            double totalBonusProductPrice = 0

                            for(int q = 0; q < packagePromotionalProductsList.size(); q++ ) {
                                List<DistributionPointStock> distributionPointStockBonusPromotion = DistributionPointStock.findAllBySubWarehouseAndFinishProduct(subWarehouseBonus,packagePromotionalProductsList[q].product)
                                if(distributionPointStockBonusPromotion.size()){
                                    String stockIds = ""
                                    boolean isQuantityMeet = false
                                    int totalBonusQuantity = (packagePromotionalProductsList[q].bonusQuantity * bonusCount)
                                    int totalBonusQuantityTemp = (packagePromotionalProductsList[q].bonusQuantity * bonusCount)

                                    for(int n = 0; n < distributionPointStockBonusPromotion.size(); n++){
                                        if((totalBonusQuantityTemp > 0) && (distributionPointStockBonusPromotion[n].inQuantity - distributionPointStockBonusPromotion[n].outQuantity) >= totalBonusQuantityTemp){
                                            distributionPointStockBonusPromotion[n].outQuantity += totalBonusQuantityTemp
                                            distributionPointStockBonusPromotion[n].userUpdated = applicationUser
                                            distributionPointStockBonusPromotion[n].lastUpdated = new Date()

                                            DistributionPointStockTransaction distributionPointStockTransactionBonusPromotion = new DistributionPointStockTransaction()
                                            distributionPointStockTransactionBonusPromotion.unitPrice = 0
                                            distributionPointStockTransactionBonusPromotion.distributionPointStock = distributionPointStockBonusPromotion[n]
                                            distributionPointStockTransactionBonusPromotion.inQuantity = 0
                                            distributionPointStockTransactionBonusPromotion.outQuantity = totalBonusQuantityTemp
                                            distributionPointStockTransactionBonusPromotion.outInvoice = secondaryInvoice
                                            distributionPointStockTransactionBonusPromotion.transactionDate = new Date()
                                            distributionPointStockTransactionBonusPromotion.userCreated = applicationUser
                                            distributionPointStockTransactionBonusPromotion.dateCreated = new Date()

                                            totalBonusQuantityTemp = 0.00

                                            if(stockIds){
                                                stockIds += ","+distributionPointStockBonusPromotion[n].id
                                            }else{
                                                stockIds = distributionPointStockBonusPromotion[n].id
                                            }

                                            distributionPointStockTransactionBonusPromotionList.add(distributionPointStockTransactionBonusPromotion)
                                            distributionPointStockBonusPromotionList.add(distributionPointStockBonusPromotion[n])
                                        }else if((totalBonusQuantityTemp > 0 && (distributionPointStockBonusPromotion[n].inQuantity - distributionPointStockBonusPromotion[n].outQuantity) > 0)){
                                            totalBonusQuantityTemp -= (distributionPointStockBonusPromotion[n].inQuantity - distributionPointStockBonusPromotion[n].outQuantity)
                                            distributionPointStockBonusPromotion[n].outQuantity += (distributionPointStockBonusPromotion[n].inQuantity - distributionPointStockBonusPromotion[n].outQuantity)
                                            distributionPointStockBonusPromotion[n].userUpdated = applicationUser
                                            distributionPointStockBonusPromotion[n].lastUpdated = new Date()

                                            DistributionPointStockTransaction distributionPointStockTransactionBonusPromotion = new DistributionPointStockTransaction()
                                            distributionPointStockTransactionBonusPromotion.unitPrice = 0
                                            distributionPointStockTransactionBonusPromotion.distributionPointStock = distributionPointStockBonusPromotion[n]
                                            distributionPointStockTransactionBonusPromotion.inQuantity = 0
                                            distributionPointStockTransactionBonusPromotion.outQuantity = (distributionPointStockBonusPromotion[n].inQuantity - distributionPointStockBonusPromotion[n].outQuantity)
                                            distributionPointStockTransactionBonusPromotion.outInvoice = secondaryInvoice
                                            distributionPointStockTransactionBonusPromotion.transactionDate = new Date()
                                            distributionPointStockTransactionBonusPromotion.userCreated = applicationUser
                                            distributionPointStockTransactionBonusPromotion.dateCreated = new Date()

                                            if(stockIds){
                                                stockIds += ","+distributionPointStockBonusPromotion[n].id
                                            }else{
                                                stockIds = distributionPointStockBonusPromotion[n].id
                                            }

                                            distributionPointStockTransactionBonusPromotionList.add(distributionPointStockTransactionBonusPromotion)
                                            distributionPointStockBonusPromotionList.add(distributionPointStockBonusPromotion[n])
                                        }

                                        if(totalBonusQuantityTemp == 0){
                                            isQuantityMeet = true
                                            break
                                        }
                                    }

                                    if(isQuantityMeet == true){
                                        AdjustBonusPromotionWithInvoice adjustBonusPromotionWithInvoice = new AdjustBonusPromotionWithInvoice()
                                        adjustBonusPromotionWithInvoice.promotionPackage = packagePromotionalProductsList[q].promotionPackage
                                        adjustBonusPromotionWithInvoice.customer = secondaryInvoice.defaultCustomer
                                        adjustBonusPromotionWithInvoice.invoice = secondaryInvoice
                                        adjustBonusPromotionWithInvoice.product = packagePromotionalProductsList[q].product
                                        adjustBonusPromotionWithInvoice.stock = stockIds
                                        adjustBonusPromotionWithInvoice.quantity = packagePromotionalProductsList[q].bonusQuantity * bonusCount
                                        adjustBonusPromotionWithInvoice.rate = packagePromotionalProductsList[q].productRate
                                        adjustBonusPromotionWithInvoice.isActive = true
                                        adjustBonusPromotionWithInvoice.isAdjusted = true
                                        adjustBonusPromotionWithInvoice.userCreated = applicationUser
                                        adjustBonusPromotionWithInvoice.dateCreated = new Date()

                                        adjustBonusPromotionWithInvoiceList.add(adjustBonusPromotionWithInvoice)
                                        /************ Process Customer(Delivery Man) Stock  ***********/
                                        List<CustomerStock> existingCustomerStockList = CustomerStock.withCriteria {
                                            eq('deliveryMan', secondaryDemandOrder.userTentativeDelivery)
                                            eq('finishProduct', invoiceDetails.finishProduct)
                                            eq('batchNo', invoiceDetails.batchNumber)
                                            maxResults(1)
                                        }

                                        CustomerStock customerStock
                                        if(existingCustomerStockList.size() > 0){
                                            customerStock = existingCustomerStockList[0]
                                            customerStock.userUpdated = applicationUser
                                            customerStock.inQuantity = customerStock.inQuantity + totalBonusQuantity
                                        }else{
                                            customerStock = new CustomerStock()
                                            customerStock.userCreated = applicationUser
                                            customerStock.deliveryMan = secondaryDemandOrder.userTentativeDelivery
                                            customerStock.batchNo = invoiceDetails.batchNumber
                                            customerStock.inQuantity = adjustBonusPromotionWithInvoice.quantity
                                            customerStock.finishProduct = adjustBonusPromotionWithInvoice.product
                                            customerStock.outQuantity = 0
                                        }

                                        CustomerStockTransaction customerStockTransaction = new CustomerStockTransaction()
                                        customerStockTransaction.customerStock = customerStock
                                        customerStockTransaction.transactionDate = new Date()
                                        customerStockTransaction.inInvoice = secondaryInvoice
                                        customerStockTransaction.inQuantity = adjustBonusPromotionWithInvoice.quantity
                                        customerStockTransaction.outQuantity = 0
                                        customerStockTransaction.unitPrice = 0
                                        customerStockTransaction.userCreated = applicationUser

                                        customerStockList.add(customerStock)

                                        Map bonusProductPrice = productPriceService.getProductPriceByCustomerAndProduct(adjustBonusPromotionWithInvoice.customer.id, adjustBonusPromotionWithInvoice.product.id, secondaryDemandOrder.territorySubArea.id)
                                        totalBonusProductPrice += (bonusProductPrice.dpPrice * adjustBonusPromotionWithInvoice.quantity)

                                        journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
                                        journalDetails.chartOfAccounts = adjustBonusPromotionWithInvoice.product.chartOfAccountHead
                                        journalDetails.prefixCode = customerMaster.code
                                        journalDetails.postfixCode = secondaryInvoice.distributionPoint.code
                                        journalDetails.debitAmount = 0.00
                                        journalDetails.creditAmount = bonusProductPrice.dpPrice * adjustBonusPromotionWithInvoice.quantity
                                        journalDetails.particular = ApplicationConstants.SALES + " of bonus product: [" +adjustBonusPromotionWithInvoice.product.code + "] " + adjustBonusPromotionWithInvoice.product.name
                                        journalDetailsListForBonus.add(journalDetails)

                                    }else{
                                        bonusMsg = this.getMessage(MESSAGE_HEADER, Message.ERROR, "Bonus product quantity is not available in dp bonus sub-inventory.")
                                        break
                                    }
                                }else{
                                    bonusMsg = this.getMessage(MESSAGE_HEADER, Message.ERROR, "Bonus product not found in dp bonus sub-inventory.")
                                    break
                                }
                            }

                            if(bonusMsg){
                                return bonusMsg
                                break
                            }else{
//                                Accounting Treatment Of Bonus Promotion
                                ChartOfAccountsMapping chartOfAccountsMappingSalesDiscount = ChartOfAccountsMapping.findByCoaType(COAType.SALES_DISCOUNT)
                                if (!chartOfAccountsMappingSalesDiscount) {
                                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Sales Discount head is not mapped with Chart of Accounts")
                                }

                                journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
                                journalDetails.chartOfAccounts = chartOfAccountsMappingSalesDiscount.chartOfAccounts
                                journalDetails.prefixCode = customerMaster.code
                                journalDetails.postfixCode = secondaryInvoice.distributionPoint.code
                                journalDetails.debitAmount = totalBonusProductPrice // * bonusCount
                                journalDetails.creditAmount = 0.00
                                journalDetails.particular = ApplicationConstants.SALES + " discount for the Customer: [" + customerMaster.code + "] " + customerMaster.name
                                journalDetailsListForBonus.add(journalDetails)
                            }
                        } else if((checkPromotion.message.type == Message.SUCCESS) && promotionPackage.discountAmount > 0){
                            AdjustBonusPromotionWithInvoice adjustBonusPromotionWithInvoice = new AdjustBonusPromotionWithInvoice()
                            adjustBonusPromotionWithInvoice.promotionPackage = promotionPackage
                            adjustBonusPromotionWithInvoice.customer = secondaryInvoice.defaultCustomer
                            adjustBonusPromotionWithInvoice.invoice = secondaryInvoice
                            adjustBonusPromotionWithInvoice.discountAmount = (promotionPackage.discountAmount * bonusCount)
                            adjustBonusPromotionWithInvoice.isActive = true
                            adjustBonusPromotionWithInvoice.isAdjusted = true
                            adjustBonusPromotionWithInvoice.userCreated = applicationUser
                            adjustBonusPromotionWithInvoice.dateCreated = new Date()

                            adjustBonusPromotionWithInvoiceList.add(adjustBonusPromotionWithInvoice)

//                          Accounting Treatment Of Bonus Promotion
                            ChartOfAccountsMapping chartOfAccountsMappingAdvertisement = ChartOfAccountsMapping.findByCoaType(COAType.ADVERTISEMENT)
                            if (!chartOfAccountsMappingAdvertisement) {
                                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Advertisement head is not mapped with Chart of Accounts")
                            }

                            journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
                            journalDetails.chartOfAccounts = chartOfAccountsMappingAdvertisement.chartOfAccounts
                            journalDetails.prefixCode = customerMaster.code
                            journalDetails.postfixCode = secondaryInvoice.distributionPoint.code  // Non Factory DP
                            journalDetails.debitAmount = (promotionPackage.discountAmount * bonusCount)
                            journalDetails.creditAmount = 0.00
                            journalDetails.particular = " Advertisement for the Customer: [" + customerMaster.code + "] " + customerMaster.name
                            journalDetailsListForBonus.add(journalDetails)

                            ChartOfAccountsMapping chartOfAccountsMappingAccountsReceivable = ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_RECEIVABLE)
                            if (!chartOfAccountsMappingAccountsReceivable) {
                                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Accounts Receivable head is not mapped with Chart of Accounts")
                            }

                            journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
                            journalDetails.chartOfAccounts = chartOfAccountsMappingAccountsReceivable.chartOfAccounts
                            journalDetails.prefixCode = ""
                            journalDetails.postfixCode = secondaryInvoice.distributionPoint.code  // Factory DP
                            journalDetails.creditAmount = (promotionPackage.discountAmount * bonusCount)
                            journalDetails.debitAmount = 0.00
                            journalDetails.particular = " Accounts Receivable for [" + secondaryInvoice.distributionPoint.code + "] " + secondaryInvoice.distributionPoint.name
                            journalDetailsListForBonus.add(journalDetails)
                        }

                        // DR Journal
                        chartOfAccountsMapping = ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_RECEIVABLE)
                        if(!chartOfAccountsMapping){
                            return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Accounts Receivable Head is not mapped with Chart of Accounts")
                        }

                        journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                        journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                        journalDetails.prefixCode = customerMaster.code
                        journalDetails.postfixCode = secondaryInvoice.distributionPoint.code
                        journalDetails.debitAmount = secondaryInvoice.invoiceAmount
                        journalDetails.creditAmount = 0.00
                        journalDetails.particular = ApplicationConstants.SALES + " from Customer: [" + customerMaster.code + "] " + customerMaster.name
                        if(!journalDetails.validate()){
                            return this.getValidationErrorMessage(journalDetails)
                        }
                        journalDetailsList.add(journalDetails)

                        // CR Journal
                        Float totalCommission = 0.00
                        invoiceDetailsList.each { invoiceDetail ->
                            journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                            journalDetails.chartOfAccounts = invoiceDetail.finishProduct.chartOfAccountHead
                            journalDetails.prefixCode = ""
                            journalDetails.postfixCode = secondaryInvoice.distributionPoint?.code
                            journalDetails.debitAmount = 0.00
                            Map productPrice =  productPriceService.getDPProductPriceByCustomerAndProduct(secondaryDemandOrder.userTentativeDelivery.id, invoiceDetail.finishProduct.id, secondaryDemandOrder.territorySubArea.id)
                            journalDetails.creditAmount =  productPrice.productPriceWithVat * invoiceDetail.quantity   // DP Price * Quantity
                            journalDetails.particular = ApplicationConstants.SALES + ": " + invoiceDetail.finishProduct.name
                            journalDetailsList.add(journalDetails)
                            totalCommission += (invoiceDetail.unitPrice * invoiceDetail.quantity) - journalDetails.creditAmount   // TP price - DP Price
                        }
                        // CR Journal
                        chartOfAccountsMapping = ChartOfAccountsMapping.findByCoaType(COAType.COMMISSION)
                        if(!chartOfAccountsMapping){
                            return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Commission Head is not mapped with Chart of Accounts")
                        }

                        journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                        journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                        journalDetails.prefixCode = secondaryInvoice.defaultCustomer.code
                        journalDetails.postfixCode = secondaryInvoice.distributionPoint?.code
                        journalDetails.debitAmount = 0.00
                        journalDetails.creditAmount = totalCommission
                        journalDetails.particular = ApplicationConstants.SALES + " from Customer: [" + customerMaster.code + "] " + customerMaster.name
                        if(!journalDetails.validate()){
                            return this.getValidationErrorMessage(journalDetails)
                        }
                        journalDetailsList.add(journalDetails)

                        Map mapInstance = [:]
                        mapInstance.put("secondaryInvoice", secondaryInvoice)
                        mapInstance.put("secondaryInvoiceDetails", invoiceDetailsList)
                        mapInstance.put("distributionPointStockList", distributionPointStockListToBeUpdated)
                        mapInstance.put("distributionPointStockTransactionList", distributionPointStockTransactionList)
                        mapInstance.put("secondaryDemandOrder", secondaryDemandOrder)
                        mapInstance.put("customerStockList", customerStockList)
                        mapInstance.put("customerStockTransactionList", customerStockTransactionList)
                        mapInstance.put("journalHead", journalHead)
                        mapInstance.put("journalDetailsList", journalDetailsList)

                        mapInstance.put("distributionPointStockBonusPromotionList", distributionPointStockBonusPromotionList)
                        mapInstance.put("distributionPointStockTransactionBonusPromotionList", distributionPointStockTransactionBonusPromotionList)
                        mapInstance.put("adjustBonusPromotionWithInvoiceList", adjustBonusPromotionWithInvoiceList)
                        mapInstance.put("journalDetailsListForBonus", journalDetailsListForBonus)

                        if (quantityBasedBonusList) {
                            mapInstance.put("quantityBasedBonusList", quantityBasedBonusList)
                        }
                        Invoice createdInvoice = (Invoice) processOrderService.processSecondaryDemandOrder(mapInstance)
                        if (createdInvoice) {
                            successResult += "Order: " + secondaryDemandOrder.orderNo + " processed with Invoice No: " + createdInvoice.code + ";"
                        } else {
                            unSuccessResult += "Order: " + secondaryDemandOrder.orderNo + " failed;"
                        }
                    }else{
                        unSuccessResult += "Order: " + secondaryDemandOrder.orderNo + " failed;"
                    }
                }
            }
            return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, successResult + unSuccessResult)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message + " Success: " + successResult + "; Failed: " + unSuccessResult)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }

    //    Check Promotion or Bonus Amount or Quantity
    private Map checkPromotionBonus(SecondaryDemandOrder secondaryDemandOrder, List<SecondaryDemandOrderDetails> secondaryDemandOrderDetailsList){
        Map map = [:]
        Object promotionPackageCustomers = processOrderService.checkBonusPromotions(secondaryDemandOrder.customerMaster.id)
        if(promotionPackageCustomers && (promotionPackageCustomers.calculationStatus == ApplicationConstants.BONUS_CALCULATION_STATUS_CURRENT)){
            PromotionPackage promotionPackage = PromotionPackage.read(promotionPackageCustomers.packageId)
            List<PromotionPackageProducts> packageProductsList = PromotionPackageProducts.findAllByPromotionPackageAndIsActive(promotionPackage,true)
            List<PromotionPackagePromotionalProducts> packagePromotionalProductsList = PromotionPackagePromotionalProducts.findAllByPromotionPackageAndIsActive(promotionPackage,true)
            int bonusCount = 0
            int bonusLoopCount = 0
            boolean isBonusConditionMeet = true

            while(isBonusConditionMeet == true){
                for(int i = 0; i < packageProductsList.size(); i++){
                    bonusLoopCount = 0
                    for (int j = 0; j < secondaryDemandOrderDetailsList.size(); j++) {
                        if (packageProductsList[i].product.id == secondaryDemandOrderDetailsList[j].finishProduct.id) {
                            int totalProductPurchaseQuantity = packageProductsList[i].purchaseQuantity * (bonusCount + 1)
                            if((totalProductPurchaseQuantity <= secondaryDemandOrderDetailsList[j].quantity) && (packageProductsList[i].quantityLimit >= totalProductPurchaseQuantity)){
                                isBonusConditionMeet = true
                            }else{
                                isBonusConditionMeet = false
                                map.put("message",this.getMessage(MESSAGE_HEADER, Message.WARNING, "Bonus condition doesn't meet by this invoice."))
                            }

                            break
                        }else{
                            bonusLoopCount++
                        }
                    }

                    if(secondaryDemandOrderDetailsList.size() == bonusLoopCount){
                        isBonusConditionMeet = false
                    }

                    if(isBonusConditionMeet == false){
                        break
                    }
                }

                if(isBonusConditionMeet == true){
                    bonusCount++
                }
            }

            map.put("packagePromotionalProductsList",packagePromotionalProductsList)
            map.put("bonusCount",bonusCount)
            map.put("promotionPackage", promotionPackage)
            map.put("message",this.getMessage(MESSAGE_HEADER, Message.SUCCESS, "Bonus promotion has been calculated."))
        }else{
            map.put("message",this.getMessage(MESSAGE_HEADER, Message.WARNING, "No bonus package found for this customer."))
        }

        return map;
    }
}
