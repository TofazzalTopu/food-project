package com.bits.bdfp.inventory.warehouse.miscellaneoustransactions

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.product.ProductPriceService
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointStock
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import com.bits.bdfp.inventory.warehouse.*
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
 * Created by liton.miah on 4/27/2016.
 */
@Component("createDamageMiscellaneousTransactionsAction")
class CreateDamageMiscellaneousTransactionsAction extends Action {
    private static final Log log = LogFactory.getLog(this)

    @Autowired
    MiscellaneousTransactionsService miscellaneousTransactionsService
    @Autowired
    ProductPriceService productPriceService
    @Autowired
    SessionFactory sessionFactory
    Message message

    public Object preCondition(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            List<DamageMiscellaneousTransactions> damageMiscellaneousTransactionsList = (List<DamageMiscellaneousTransactions>) params.damageMiscellaneousTransactionsList
            Boolean isValidate = true
            damageMiscellaneousTransactionsList.each {
                if(!it.validate()){
                    message = this.getValidationErrorMessage()
                    isValidate = false
                }
            }
            if(isValidate){
                message = this.getMessage("Miscellaneous Transactions", Message.SUCCESS, SUCCESS_SAVE)
            }
            return message
        }catch(Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            List<DamageMiscellaneousTransactions> damageMiscellaneousTransactionsList = []
            List<DistributionPointStockTransaction> distributionPointStockTransactionList = []
            List<FinishGoodStockTransaction> finishGoodStockTransactionList = []
            EnterpriseConfiguration enterpriseConfiguration = null

            Float checkQty = 0
//            Float damageQtyTotal = 0
            Float damageQty = 0
            Float productPriceTotal = 0
            String prefixCode = ""
            Boolean isFactory = false

            params.products.each { key, val ->
                if (val instanceof Map) {
                    DamageMiscellaneousTransactions damageMiscellaneousTransactions = new DamageMiscellaneousTransactions(val)

                    FinishProduct finishProduct = FinishProduct.get(val.product.id)
                    SubWarehouse subWarehouse = SubWarehouse.get(val.subInventory.id)

                    DistributionPoint distributionPoint = DistributionPoint.get(val.distributionPoint.id)
                    enterpriseConfiguration = EnterpriseConfiguration.read(distributionPoint.enterpriseConfiguration.id)

//                    damageQtyTotal = Float.parseFloat(val.damageQty)
                    damageQty = Float.parseFloat(val.damageQty)

                    damageMiscellaneousTransactions.userCreated = applicationUser
                    damageMiscellaneousTransactions.dateCreated = new Date()

                    if(distributionPoint.isFactory){
                        isFactory = true
                        List<FinishGoodStock> finishGoodStockList = FinishGoodStock.findAllBySubWarehouseAndFinishProduct(subWarehouse,finishProduct)
                        finishGoodStockList.each {
                            FinishGoodStockTransaction finishGoodStockTransaction = FinishGoodStockTransaction.findByFinishGoodStock(it)
                            if (finishGoodStockTransaction) {
                                if((finishGoodStockTransaction.inQuantity - finishGoodStockTransaction.outQuantity) >= damageQty && damageQty > 0){
                                    finishGoodStockTransaction.outQuantity += damageQty
                                    productPriceTotal += (damageQty * finishGoodStockTransaction.unitPrice)
                                    damageQty = 0

                                    finishGoodStockTransaction.userUpdated = applicationUser
                                    finishGoodStockTransaction.lastUpdated = new Date()

                                    finishGoodStockTransactionList.add(finishGoodStockTransaction)
                                }else if(damageQty > 0 && (finishGoodStockTransaction.inQuantity - finishGoodStockTransaction.outQuantity) < damageQty ){
                                    productPriceTotal += ((finishGoodStockTransaction.inQuantity - finishGoodStockTransaction.outQuantity) * finishGoodStockTransaction.unitPrice)
                                    damageQty -= (finishGoodStockTransaction.inQuantity - finishGoodStockTransaction.outQuantity)
                                    finishGoodStockTransaction.outQuantity += finishGoodStockTransaction.inQuantity - finishGoodStockTransaction.outQuantity

                                    finishGoodStockTransaction.userUpdated = applicationUser
                                    finishGoodStockTransaction.lastUpdated = new Date()

                                    finishGoodStockTransactionList.add(finishGoodStockTransaction)
                                }
                            }
                        }
                    }else{
                        List<DistributionPointStock> distributionPointStockList = DistributionPointStock.findAllBySubWarehouseAndFinishProduct(subWarehouse,finishProduct)
                        distributionPointStockList.each {
                            DistributionPointStockTransaction distributionPointStockTransaction = DistributionPointStockTransaction.findByDistributionPointStock(it)
                            if(distributionPointStockTransaction){
                                if((damageQty > 0) && ((distributionPointStockTransaction.inQuantity - distributionPointStockTransaction.outQuantity) >= damageQty)){
                                    distributionPointStockTransaction.outQuantity += damageQty
                                    productPriceTotal += (distributionPointStockTransaction.unitPrice * damageQty)
                                    damageQty = 0

                                    distributionPointStockTransaction.userUpdated = applicationUser
                                    distributionPointStockTransaction.lastUpdated = new Date()

                                    distributionPointStockTransactionList.add(distributionPointStockTransaction)
                                }else if((damageQty > 0)&&((distributionPointStockTransaction.inQuantity - distributionPointStockTransaction.outQuantity) < damageQty)){
                                    productPriceTotal += ((distributionPointStockTransaction.inQuantity - distributionPointStockTransaction.outQuantity) * distributionPointStockTransaction.unitPrice)
                                    damageQty -= (distributionPointStockTransaction.inQuantity - distributionPointStockTransaction.outQuantity)
                                    distributionPointStockTransaction.outQuantity += (distributionPointStockTransaction.inQuantity - distributionPointStockTransaction.outQuantity)

                                    distributionPointStockTransaction.userUpdated = applicationUser
                                    distributionPointStockTransaction.lastUpdated = new Date()

                                    distributionPointStockTransactionList.add(distributionPointStockTransaction)
                                }
                            }
                        }
                    }

                    if(damageQty > 0){
                        checkQty += damageQty
                    }

//                    if (distributionPoint.isFactory == true) {
//                        isFactory = true
//                        productPriceTotal += productPriceService.getFactoryDPProductPriceByProduct(finishProduct.id, distributionPoint.id).averageRateWithVat * damageQtyTotal
//                    }
                    prefixCode = distributionPoint.code

                    damageMiscellaneousTransactionsList.add(damageMiscellaneousTransactions)
                }
            }

            List<JournalDetails> journalDetailsList = []
            Journal journalHead = null

            if (isFactory == true){
                Date dateNow = new Date()
                SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
                String currentMonth = formatMonth.format(dateNow)
                SimpleDateFormat formatYear = new SimpleDateFormat("YY")
                String currentYear = formatYear.format(dateNow)
                SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
                String currentDay = formatDay.format(dateNow)
                /***************************** COA Entry Start ***************************/
                journalDetailsList = new ArrayList<JournalDetails>()
                JournalDetails journalDetails = null
                ChartOfAccountsMapping chartOfAccountsMapping = null

                journalHead = new Journal(enterprise: enterpriseConfiguration, userCreated: applicationUser, isActive: true)
                journalHead.tableName = sessionFactory.getClassMetadata(DamageMiscellaneousTransactions).tableName
                journalHead.transactionDate = new Date()
                journalHead.journalStatus = JournalStatus.CHECKED
                journalHead.transactionNo = CodeGenerationUtil.generate(8).toString()
                journalHead.journalNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
                journalHead.moduleName = ApplicationConstants.MODULE_MISCELLANEOUS_TRANSACTIONS
                if (!journalHead.validate()) {
                    return this.getValidationErrorMessage(journalHead)
                }

                String particular = ""
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)

                chartOfAccountsMapping = ChartOfAccountsMapping.findByCoaType(COAType.SALES_DISCOUNT)
                if (!chartOfAccountsMapping) {
                    return this.getMessage("Miscellaneous Transactions", Message.ERROR, "Sales discount head is not mapped with Chart of Accounts")
                }
                particular = "Sales Discount"


                journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                journalDetails.prefixCode = prefixCode
                journalDetails.debitAmount = productPriceTotal
                journalDetails.creditAmount = 0.00
                journalDetails.particular = particular + " paid amount"
                if (!journalDetails.validate()) {
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                chartOfAccountsMapping = ChartOfAccountsMapping.findByCoaType(COAType.FINISH_GOOD_STOCK)
                if (!chartOfAccountsMapping) {
                    return this.getMessage("Miscellaneous Transactions", Message.ERROR, "Finish good stock head is not mapped with Chart of Accounts")
                }
                particular = "Finish Good Stock"

                journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
                journalDetails.prefixCode = prefixCode
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = productPriceTotal
                journalDetails.particular = particular + " received amount"
                if (!journalDetails.validate()) {
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)
            }

            Map map = [:]
            map.put("damageMiscellaneousTransactionsList",damageMiscellaneousTransactionsList)
            map.put("finishGoodStockTransactionList",finishGoodStockTransactionList)
            map.put("distributionPointStockTransactionList",distributionPointStockTransactionList)
            map.put("journalHead",journalHead)
            map.put("journalDetailsList",journalDetailsList)

            if(checkQty > 0){
                message = this.getMessage("Miscellaneous Transactions", Message.WARNING, "Stock is Not Available.")
            }else{
                message = this.preCondition(map,applicationUser)
                if(message.type == 1){
                    Integer row = miscellaneousTransactionsService.createDamage(map)
                    if(row > 0){
                        message = this.getMessage("Miscellaneous Transactions", Message.SUCCESS, "Miscellaneous Transactions Return to Production Created Successfully.")
                    }else{
                        message = this.getMessage("Miscellaneous Transactions", Message.ERROR, FAIL_SAVE)
                    }
                }
            }
            return message
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }

    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}
