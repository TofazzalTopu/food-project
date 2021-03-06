package com.bits.bdfp.calculateandadjustbonus

import com.bits.bdfp.accounts.*
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
import com.bits.bdfp.promotion.*
import com.bits.bdfp.promotionsetup.PromotionSetupService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.web.json.JSONArray
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

/**
 * Created by mdalinaser.khan on 2/15/16.
 */
@Component("postAdjustSecondaryInvoiceAction")
class PostAdjustSecondaryInvoiceAction extends Action {
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
    @Autowired
    JournalDetailsService journalDetailsService
    @Autowired
    PromotionSetupService promotionSetupService
    Message message

    public Object preCondition(Object params, Object object) {
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            String successResult = ""
            String unSuccessResult = ""
            Date dateNow = new Date()

            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            SimpleDateFormat formatDay = new SimpleDateFormat("DD")
            String currentDay = formatDay.format(dateNow)
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()

            JSONArray packageList = new JSONArray(params.packageList)
            List<Invoice> unadjustedInvoiceList = []
            JournalDetails journalDetails = null
            List<JournalDetails> journalDetailsListForBonus = []
            List<Journal> journalHeadList = []

            List<DistributionPointStock> distributionPointStockBonusPromotionList = []
            List<DistributionPointStockTransaction> distributionPointStockTransactionBonusPromotionList = []
            List<AdjustBonusPromotionWithInvoice> adjustBonusPromotionWithInvoiceList = []
            List<CustomerStock> customerStockList = new ArrayList<CustomerStock>()

            if (packageList.size() > 0) {
                packageList.each {
                    Promotion promotion = Promotion.read(it.promotion_id)
                    List<Invoice> list = Invoice.findAllByDateCreatedBetweenAndSecondaryDemandOrderIsNotNull(promotion.effectiveFrom, promotion.effectiveTo)
                    list.each {
                        AdjustBonusPromotionWithInvoice adjustBonusPromotionWithInvoice = AdjustBonusPromotionWithInvoice.findByInvoice(it)
                        if (!adjustBonusPromotionWithInvoice) {
                            unadjustedInvoiceList.add(it)
                        }
                    }
                }
            }

            if(unadjustedInvoiceList.size() == 0){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "No invoice found for this package promotions.")
            }

            unadjustedInvoiceList.each {
                SecondaryDemandOrder secondaryDemandOrder = it.secondaryDemandOrder
                List<SecondaryDemandOrderDetails> secondaryDemandOrderDetailsList = SecondaryDemandOrderDetails.findAllBySecondaryDemandOrder(secondaryDemandOrder)
                CustomerMaster customerMaster = it.defaultCustomer

                /********************** COA Entry Start ************************/
                EnterpriseConfiguration enterpriseConfiguration = secondaryDemandOrder.customerMaster.enterpriseConfiguration
                Journal journalHead = new Journal(enterprise: enterpriseConfiguration, userCreated: applicationUser, isActive: true)
                journalHead.tableName = sessionFactory.getClassMetadata(Invoice).tableName
                journalHead.transactionDate = new Date()
                journalHead.journalStatus = JournalStatus.CHECKED
                journalHead.transactionNo = CodeGenerationUtil.generate(8).toString() // it.code
                journalHead.journalNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
                journalHead.moduleName = ApplicationConstants.MODULE_PROCESS_PRIMARY_ORDER
                if (!journalHead.validate()) {
                    throw new RuntimeException(this.getValidationErrorMessage(journalHead).messageBody[0].toString())
                }
                journalHeadList.add(journalHead)

//                        Calculate Bonus & Promotions
                Map checkPromotion = checkPromotionBonus(secondaryDemandOrder, secondaryDemandOrderDetailsList)
                PromotionPackage promotionPackage = checkPromotion.promotionPackage
                Double amountDiscount = promotionPackage?.discountAmount
                List<PromotionPackagePromotionalProducts> packagePromotionalProductsList = checkPromotion.packagePromotionalProductsList
                if ((checkPromotion.message.type == Message.SUCCESS) && (packagePromotionalProductsList.size() > 0) && (checkPromotion.bonusCount > 0)) {
                    Message bonusMsg = null
                    int bonusCount = checkPromotion.bonusCount

                    DistributionPointWarehouse distributionPointWarehouse = DistributionPointWarehouse.findByDistributionPoint(it.distributionPoint)
                    if(!distributionPointWarehouse?.warehouse){
                        return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Distribution Point Inventory is not Selected to process secondary invoice(s)")
                    }
                    Warehouse warehouse = distributionPointWarehouse?.warehouse
                    SubWarehouseType subWarehouseTypeBonus = SubWarehouseType.get(ApplicationConstants.BONUS_TYPE_INVENTORY_ID)
                    SubWarehouse subWarehouseBonus = SubWarehouse.findWhere(warehouse: warehouse, subWarehouseType: subWarehouseTypeBonus)
                    if(!subWarehouseBonus){
                        return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Sub Inventory is not Available to adjust secondary invoice.")
                    }
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
                                    distributionPointStockTransactionBonusPromotion.outInvoice = it
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
                                    distributionPointStockTransactionBonusPromotion.outInvoice = it
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
                                adjustBonusPromotionWithInvoice.customer = it.defaultCustomer
                                adjustBonusPromotionWithInvoice.invoice = it
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
//                                InvoiceDetails invoiceDetails = InvoiceDetails.findByInvoiceAndFinishProduct(it,adjustBonusPromotionWithInvoice.product)
                                List<CustomerStock> existingCustomerStockList = CustomerStock.withCriteria {
                                    eq('deliveryMan', secondaryDemandOrder.userTentativeDelivery)
                                    eq('finishProduct', adjustBonusPromotionWithInvoice.product)
//                                    eq('batchNo', invoiceDetails.batchNumber)
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
//                                    customerStock.batchNo = invoiceDetails.batchNumber
                                    customerStock.inQuantity = adjustBonusPromotionWithInvoice.quantity
                                    customerStock.finishProduct = adjustBonusPromotionWithInvoice.product
                                    customerStock.outQuantity = 0
                                }

                                CustomerStockTransaction customerStockTransaction = new CustomerStockTransaction()
                                customerStockTransaction.customerStock = customerStock
                                customerStockTransaction.transactionDate = new Date()
                                customerStockTransaction.inInvoice = it
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
                                journalDetails.postfixCode = it.distributionPoint.code
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

                    if (bonusMsg) {
                        return bonusMsg
                    } else {
//                                Accounting Treatment Of Bonus Promotion
                        ChartOfAccountsMapping chartOfAccountsMappingSalesDiscount = ChartOfAccountsMapping.findByCoaType(COAType.SALES_DISCOUNT)
                        if (!chartOfAccountsMappingSalesDiscount) {
                            return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Sales Discount head is not mapped with Chart of Accounts")
                        }

                        journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
                        journalDetails.chartOfAccounts = chartOfAccountsMappingSalesDiscount.chartOfAccounts
                        journalDetails.prefixCode = customerMaster.code
                        journalDetails.postfixCode = it.code
                        journalDetails.debitAmount = totalBonusProductPrice // * bonusCount
                        journalDetails.creditAmount = 0.00
                        journalDetails.particular = ApplicationConstants.SALES + " discount for the Customer: [" + customerMaster.code + "] " + customerMaster.name
                        journalDetailsListForBonus.add(journalDetails)
                    }
                } else if (amountDiscount > 0.00) {
                    AdjustBonusPromotionWithInvoice adjustBonusPromotionWithInvoice = new AdjustBonusPromotionWithInvoice()
                    adjustBonusPromotionWithInvoice.promotionPackage = promotionPackage
                    adjustBonusPromotionWithInvoice.customer = it.defaultCustomer
                    adjustBonusPromotionWithInvoice.invoice = it
                    adjustBonusPromotionWithInvoice.discountAmount = amountDiscount * checkPromotion.bonusCount
                    adjustBonusPromotionWithInvoice.isActive = true
                    adjustBonusPromotionWithInvoice.isAdjusted = true
                    adjustBonusPromotionWithInvoice.userCreated = applicationUser
                    adjustBonusPromotionWithInvoice.dateCreated = new Date()

                    adjustBonusPromotionWithInvoiceList.add(adjustBonusPromotionWithInvoice)

                    ChartOfAccountsMapping chartOfAccountsMappingAccountsReceivable = ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_RECEIVABLE)
                    if (!chartOfAccountsMappingAccountsReceivable) {
                        return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Accounts receivable head is not mapped with Chart of Accounts")
                    }

                    ChartOfAccountsMapping chartOfAccountsMappingAdvertisement = ChartOfAccountsMapping.findByCoaType(COAType.ADVERTISEMENT)
                    if (!chartOfAccountsMappingAdvertisement) {
                        return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Advertisement head is not mapped with Chart of Accounts")
                    }

                    journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
                    journalDetails.chartOfAccounts = chartOfAccountsMappingAdvertisement.chartOfAccounts
                    journalDetails.prefixCode = customerMaster.code
                    journalDetails.postfixCode = it.code
                    journalDetails.debitAmount = amountDiscount * checkPromotion.bonusCount
                    journalDetails.creditAmount = 0.00
                    journalDetails.particular = ApplicationConstants.SALES + " discount of cash amount for the Customer: [" + customerMaster.code + "] " + customerMaster.name
                    journalDetailsListForBonus.add(journalDetails)

                    journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
                    journalDetails.chartOfAccounts = chartOfAccountsMappingAccountsReceivable.chartOfAccounts
                    journalDetails.prefixCode = customerMaster.code
                    journalDetails.postfixCode = it.code
                    journalDetails.debitAmount = 0.00
                    journalDetails.creditAmount = amountDiscount * checkPromotion.bonusCount
                    journalDetails.particular = ApplicationConstants.SALES + " discount of accounts receivable for the Customer: [" + customerMaster.code + "] " + customerMaster.name
                    journalDetailsListForBonus.add(journalDetails)
                }
            }

            if((distributionPointStockBonusPromotionList.size() == 0) && (distributionPointStockTransactionBonusPromotionList.size() == 0) && (adjustBonusPromotionWithInvoiceList.size() == 0) && (journalDetailsListForBonus.size() == 0)){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "No bonus package found for this customer.")
            }

            Map mapInstance = [:]
            mapInstance.put("distributionPointStockBonusPromotionList", distributionPointStockBonusPromotionList)
            mapInstance.put("distributionPointStockTransactionBonusPromotionList", distributionPointStockTransactionBonusPromotionList)
            mapInstance.put("adjustBonusPromotionWithInvoiceList", adjustBonusPromotionWithInvoiceList)
            mapInstance.put("journalHeadList", journalHeadList)
            mapInstance.put("journalDetailsListForBonus", journalDetailsListForBonus)

            Boolean isAdjusted = promotionSetupService.adjustBonusPromotion(mapInstance)
            if (isAdjusted) {
                successResult += "Calculation and adjustment of secondary invoices are successfully completed."
            } else {
                unSuccessResult += "Calculation and adjustment of secondary invoices are failed."
            }

            return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, successResult + unSuccessResult)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }

    private Map checkPromotionBonus(SecondaryDemandOrder secondaryDemandOrder, List<SecondaryDemandOrderDetails> secondaryDemandOrderDetailsList){
        Map map = [:]
        Object promotionPackageCustomers = processOrderService.checkBonusPromotions(secondaryDemandOrder.customerMaster.id)
        if(promotionPackageCustomers && (promotionPackageCustomers.calculationStatus == ApplicationConstants.BONUS_CALCULATION_STATUS_POST)){
            PromotionPackage promotionPackage = PromotionPackage.read(promotionPackageCustomers.packageId)
            List<PromotionPackageProducts> packageProductsList = PromotionPackageProducts.findAllByPromotionPackageAndIsActive(promotionPackage,true)
            List<PromotionPackagePromotionalProducts> packagePromotionalProductsList = PromotionPackagePromotionalProducts.findAllByPromotionPackageAndIsActive(promotionPackage,true)
            int bonusCount = 0
            int bonusLoopCount = 0
            boolean isBonusConditionMeet = true

            while(isBonusConditionMeet == true){
                for(int i = 0; i < packageProductsList.size(); i++){
                    bonusLoopCount = 0;
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