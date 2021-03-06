package com.bits.bdfp.calculateandadjustbonus

import com.bits.bdfp.accounts.*
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.demandorder.*
import com.bits.bdfp.inventory.product.ProductPriceService
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.warehouse.*
import com.bits.bdfp.promotion.AdjustBonusPromotionWithInvoice
import com.bits.bdfp.promotion.Promotion
import com.bits.bdfp.promotion.PromotionPackage
import com.bits.bdfp.promotion.PromotionPackageProducts
import com.bits.bdfp.promotion.PromotionPackagePromotionalProducts
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
@Component("postAdjustPrimaryInvoiceAction")
class PostAdjustPrimaryInvoiceAction extends Action {
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
            Float totalInvoiceAmount = 0

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
            List<FinishGoodStockTransaction> finishGoodStockTransactionBonusPromotionList = new ArrayList<FinishGoodStockTransaction>()
            List<FinishGoodStock> finishGoodStockBonusPromotionList = new ArrayList<FinishGoodStock>()
            List<AdjustBonusPromotionWithInvoice> adjustBonusPromotionWithInvoiceList = []


            if (packageList.size() > 0) {
                packageList.each {
                    Promotion promotion = Promotion.read(it.promotion_id)
                    List<Invoice> list = Invoice.findAllByDateCreatedBetweenAndPrimaryDemandOrderIsNotNull(promotion.effectiveFrom, promotion.effectiveTo)
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
                PrimaryDemandOrder primaryDemandOrder = it.primaryDemandOrder
                List<PrimaryDemandOrderDetails> primaryDemandOrderDetailsList = PrimaryDemandOrderDetails.findAllByPrimaryDemandOrder(primaryDemandOrder)
                DistributionPoint factoryDp = DistributionPoint.findByIsFactoryAndEnterpriseConfiguration(true, primaryDemandOrder.customerOrderFor.enterpriseConfiguration)
                CustomerMaster customerMaster = it.defaultCustomer

                /********************** COA Entry Start ************************/
                EnterpriseConfiguration enterpriseConfiguration = primaryDemandOrder.customerOrderFor.enterpriseConfiguration
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

//              Calculate Bonus & Promotions
                Map checkPromotion = checkPromotionBonus(primaryDemandOrder, primaryDemandOrderDetailsList)
                PromotionPackage promotionPackage = checkPromotion.promotionPackage
                Double amountDiscount = promotionPackage?.discountAmount
                List<PromotionPackagePromotionalProducts> packagePromotionalProductsList = checkPromotion.packagePromotionalProductsList
                if ((checkPromotion.message.type == Message.SUCCESS) && (packagePromotionalProductsList.size() > 0) && (checkPromotion.bonusCount > 0)) {
                    Message bonusMsg = null
                    int bonusCount = checkPromotion.bonusCount

                    SubWarehouse subWarehouseBonus = SubWarehouse.read(ApplicationConstants.BONUS_TYPE_INVENTORY_ID)
                    double totalBonusProductPrice = 0

                    for (int q = 0; q < packagePromotionalProductsList.size(); q++) {
                        List<FinishGoodStock> finishGoodStockBonusPromotion = FinishGoodStock.findAllBySubWarehouseAndFinishProduct(subWarehouseBonus, packagePromotionalProductsList[q].product)
                        if (finishGoodStockBonusPromotion.size() > 0) {
                            String stockIds = ""
                            boolean isQuantityMeet = false
                            int totalBonusQuantity = (packagePromotionalProductsList[q].bonusQuantity * bonusCount)
                            for (int n = 0; n < finishGoodStockBonusPromotion.size(); n++) {
                                if ((totalBonusQuantity > 0) && (finishGoodStockBonusPromotion[n].inQuantity - finishGoodStockBonusPromotion[n].outQuantity) >= totalBonusQuantity) {
                                    finishGoodStockBonusPromotion[n].outQuantity += totalBonusQuantity
                                    finishGoodStockBonusPromotion[n].userUpdated = applicationUser
                                    finishGoodStockBonusPromotion[n].lastUpdated = new Date()

                                    FinishGoodStockTransaction finishGoodStockTransactionBonusPromotion = new FinishGoodStockTransaction()
                                    finishGoodStockTransactionBonusPromotion.unitPrice = 0
                                    finishGoodStockTransactionBonusPromotion.finishGoodStock = finishGoodStockBonusPromotion[n]
                                    finishGoodStockTransactionBonusPromotion.inQuantity = 0
                                    finishGoodStockTransactionBonusPromotion.outQuantity = totalBonusQuantity
                                    finishGoodStockTransactionBonusPromotion.finishGoodWarehouseDetails = null
                                    finishGoodStockTransactionBonusPromotion.outInvoice = it
                                    finishGoodStockTransactionBonusPromotion.transactionDate = new Date()
                                    finishGoodStockTransactionBonusPromotion.userCreated = applicationUser
                                    finishGoodStockTransactionBonusPromotion.dateCreated = new Date()

                                    totalBonusQuantity = 0.00

                                    if (stockIds) {
                                        stockIds += "," + finishGoodStockBonusPromotion[n].id
                                    } else {
                                        stockIds = finishGoodStockBonusPromotion[n].id
                                    }

                                    finishGoodStockTransactionBonusPromotionList.add(finishGoodStockTransactionBonusPromotion)
                                    finishGoodStockBonusPromotionList.add(finishGoodStockBonusPromotion[n])
                                } else if ((totalBonusQuantity > 0 && (finishGoodStockBonusPromotion[n].inQuantity - finishGoodStockBonusPromotion[n].outQuantity) > 0)) {
                                    totalBonusQuantity -= (finishGoodStockBonusPromotion[n].inQuantity - finishGoodStockBonusPromotion[n].outQuantity)
                                    finishGoodStockBonusPromotion[n].outQuantity += (finishGoodStockBonusPromotion[n].inQuantity - finishGoodStockBonusPromotion[n].outQuantity)
                                    finishGoodStockBonusPromotion[n].userUpdated = applicationUser
                                    finishGoodStockBonusPromotion[n].lastUpdated = new Date()

                                    FinishGoodStockTransaction finishGoodStockTransactionBonusPromotion = new FinishGoodStockTransaction()
                                    finishGoodStockTransactionBonusPromotion.unitPrice = 0
                                    finishGoodStockTransactionBonusPromotion.finishGoodStock = finishGoodStockBonusPromotion[n]
                                    finishGoodStockTransactionBonusPromotion.inQuantity = 0
                                    finishGoodStockTransactionBonusPromotion.outQuantity = (finishGoodStockBonusPromotion[n].inQuantity - finishGoodStockBonusPromotion[n].outQuantity)
                                    finishGoodStockTransactionBonusPromotion.finishGoodWarehouseDetails = null
                                    finishGoodStockTransactionBonusPromotion.outInvoice = it
                                    finishGoodStockTransactionBonusPromotion.transactionDate = new Date()
                                    finishGoodStockTransactionBonusPromotion.userCreated = applicationUser
                                    finishGoodStockTransactionBonusPromotion.dateCreated = new Date()

                                    if (stockIds) {
                                        stockIds += "," + finishGoodStockBonusPromotion[n].id
                                    } else {
                                        stockIds = finishGoodStockBonusPromotion[n].id
                                    }

                                    finishGoodStockTransactionBonusPromotionList.add(finishGoodStockTransactionBonusPromotion)
                                    finishGoodStockBonusPromotionList.add(finishGoodStockBonusPromotion[n])
                                }

                                if (totalBonusQuantity == 0) {
                                    isQuantityMeet = true
                                    break
                                }
                            }

                            if (isQuantityMeet == true) {
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

                                Map bonusProductPrice = productPriceService.getProductPriceByCustomerAndProduct(adjustBonusPromotionWithInvoice.customer.id, adjustBonusPromotionWithInvoice.product.id, primaryDemandOrder.territorySubArea.id)
                                totalBonusProductPrice += (bonusProductPrice.dpPrice * adjustBonusPromotionWithInvoice.quantity)

                                journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
                                journalDetails.chartOfAccounts = adjustBonusPromotionWithInvoice.product.chartOfAccountHead
                                journalDetails.prefixCode = customerMaster.code
                                journalDetails.postfixCode = factoryDp.code  // Factory DP
                                journalDetails.debitAmount = 0.00
                                journalDetails.creditAmount = bonusProductPrice.dpPrice * adjustBonusPromotionWithInvoice.quantity
                                journalDetails.particular = ApplicationConstants.SALES + " of bonus product: [" + adjustBonusPromotionWithInvoice.product.code + "] " + adjustBonusPromotionWithInvoice.product.name
                                journalDetailsListForBonus.add(journalDetails)

                            } else {
                                bonusMsg = this.getMessage(MESSAGE_HEADER, Message.ERROR, "Bonus product quantity is not available in factory bonus sub-inventory.")
                                break
                            }
                        } else {
                            bonusMsg = this.getMessage(MESSAGE_HEADER, Message.ERROR, "Bonus product not found in factory bonus sub-inventory.")
                            break
                        }
                    }

                    if (bonusMsg) {
                        return bonusMsg
                    } else {
//                      Accounting Treatment Of Bonus Promotion
                        ChartOfAccountsMapping chartOfAccountsMappingSalesDiscount = ChartOfAccountsMapping.findByCoaType(COAType.SALES_DISCOUNT)
                        if (!chartOfAccountsMappingSalesDiscount) {
                            return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Sales Discount head is not mapped with Chart of Accounts")
                        }

                        journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
                        journalDetails.chartOfAccounts = chartOfAccountsMappingSalesDiscount.chartOfAccounts
                        journalDetails.prefixCode = customerMaster.code
                        journalDetails.postfixCode = factoryDp.code  // Factory DP
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

            Map mapInstance = [:]
            mapInstance.put("finishGoodStockBonusPromotionList", finishGoodStockBonusPromotionList)
            mapInstance.put("finishGoodStockTransactionBonusPromotionList", finishGoodStockTransactionBonusPromotionList)
            mapInstance.put("adjustBonusPromotionWithInvoiceList", adjustBonusPromotionWithInvoiceList)
            mapInstance.put("journalHeadList", journalHeadList)
            mapInstance.put("journalDetailsListForBonus", journalDetailsListForBonus)

            if((finishGoodStockBonusPromotionList.size() == 0) && (finishGoodStockTransactionBonusPromotionList.size() == 0) && (adjustBonusPromotionWithInvoiceList.size() == 0) && (journalDetailsListForBonus.size() == 0)){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "No bonus package found for this customer.")
            }

            Boolean isAdjusted = promotionSetupService.adjustBonusPromotion(mapInstance)
            if (isAdjusted) {
                successResult += "Calculation and adjustment of primary invoices are successfully completed."
            } else {
                unSuccessResult += "Calculation and adjustment of primary invoices are failed."
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

//    Check Promotion or Bonus Amount or Quantity
    private Map checkPromotionBonus(PrimaryDemandOrder primaryDemandOrder, List<PrimaryDemandOrderDetails> primaryDemandOrderDetailsList) {
        Map map = [:]
        Object promotionPackageCustomers = processOrderService.checkBonusPromotions(primaryDemandOrder.customerOrderFor.id)
        if (promotionPackageCustomers && (promotionPackageCustomers.calculationStatus == ApplicationConstants.BONUS_CALCULATION_STATUS_POST)) {
            PromotionPackage promotionPackage = PromotionPackage.read(promotionPackageCustomers.packageId)
            List<PromotionPackageProducts> packageProductsList = PromotionPackageProducts.findAllByPromotionPackageAndIsActive(promotionPackage, true)
            List<PromotionPackagePromotionalProducts> packagePromotionalProductsList = PromotionPackagePromotionalProducts.findAllByPromotionPackageAndIsActive(promotionPackage, true)
            int bonusCount = 0
            int bonusLoopCount = 0
            boolean isBonusConditionMeet = true

            while (isBonusConditionMeet == true) {
                for (int i = 0; i < packageProductsList.size(); i++) {
                    bonusLoopCount = 0
                    for (int j = 0; j < primaryDemandOrderDetailsList.size(); j++) {
                        if (packageProductsList[i].product.id == primaryDemandOrderDetailsList[j].finishProduct.id) {
                            int totalProductPurchaseQuantity = packageProductsList[i].purchaseQuantity * (bonusCount + 1)
                            if ((totalProductPurchaseQuantity <= primaryDemandOrderDetailsList[j].quantity) && (packageProductsList[i].quantityLimit >= totalProductPurchaseQuantity)) {
                                isBonusConditionMeet = true
                            } else {
                                isBonusConditionMeet = false
                                map.put("message", this.getMessage(MESSAGE_HEADER, Message.WARNING, "Bonus condition doesn't meet by this invoice."))
                            }
                            break
                        } else {
                            bonusLoopCount++
                        }
                    }

                    if(primaryDemandOrderDetailsList.size() == bonusLoopCount){
                        isBonusConditionMeet = false
                    }

                    if (isBonusConditionMeet == false) {
                        break
                    }
                }

                if (isBonusConditionMeet == true) {
                    bonusCount++
                }
            }

            map.put("packagePromotionalProductsList", packagePromotionalProductsList)
            map.put("bonusCount", bonusCount)
            map.put("promotionPackage", promotionPackage)
            map.put("message", this.getMessage(MESSAGE_HEADER, Message.SUCCESS, "Bonus promotion has been calculated."))
        } else {
            map.put("message", this.getMessage(MESSAGE_HEADER, Message.WARNING, "No bonus package found for this customer."))
        }

        return map;
    }
}