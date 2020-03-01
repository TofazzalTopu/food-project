package com.bits.bdfp.inventory.warehouse.miscellaneoustransactions

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.finance.AdjustMiscellaneousFeesWithReceivable
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.product.ProductPriceService
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointStock
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import com.bits.bdfp.inventory.sales.DistributionPointWarehouse
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
import org.springframework.transaction.TransactionUsageException

import java.text.SimpleDateFormat

/**
 * Created by liton.miah on 4/27/2016.
 */
@Component("createEntertainmentMiscellaneousTransactionsAction")
class CreateEntertainmentMiscellaneousTransactionsAction extends Action {
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
            List<EntertainmentMiscellaneousTransactions> entertainmentMiscellaneousTransactionsList = (List<EntertainmentMiscellaneousTransactions>) params.entertainmentMiscellaneousTransactionsList
            Boolean isValidate = true
            entertainmentMiscellaneousTransactionsList.each {
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
            List<EntertainmentMiscellaneousTransactions> entertainmentMiscellaneousTransactionsList = []
            List<DistributionPointStockTransaction> distributionPointStockTransactionList = []
            List<FinishGoodStockTransaction> finishGoodStockTransactionList = []
            EnterpriseConfiguration enterpriseConfiguration = null

            Float checkQty = 0
//            Float entertainmentQtyTotal = 0
            Float entertainmentQty = 0
            Float productPriceTotal = 0
            String prefixCode = ""

            params.products.each{key, val ->
                if(val instanceof Map){
                    EntertainmentMiscellaneousTransactions entertainmentMiscellaneousTransactions = new EntertainmentMiscellaneousTransactions(val)
                    entertainmentMiscellaneousTransactions.userCreated = applicationUser
                    entertainmentMiscellaneousTransactions.dateCreated = new Date()

                    FinishProduct finishProduct = FinishProduct.get(val.product.id)
                    SubWarehouse subWarehouse = SubWarehouse.get(val.subInventory.id)

                    DistributionPoint distributionPoint = DistributionPoint.get(val.distributionPoint.id)
                    enterpriseConfiguration = EnterpriseConfiguration.read(distributionPoint.enterpriseConfiguration.id)

//                    entertainmentQtyTotal = Float.parseFloat(val.entertainmentQty)
                    entertainmentQty = Float.parseFloat(val.entertainmentQty)

                    if(distributionPoint.isFactory){
                        List<FinishGoodStock> finishGoodStockList = FinishGoodStock.findAllBySubWarehouseAndFinishProduct(subWarehouse,finishProduct)
                        finishGoodStockList.each {
                            FinishGoodStockTransaction finishGoodStockTransaction= FinishGoodStockTransaction.findByFinishGoodStock(it)
                            if(finishGoodStockTransaction){
                                if((finishGoodStockTransaction.inQuantity - finishGoodStockTransaction.outQuantity) >= entertainmentQty && entertainmentQty > 0){
                                    finishGoodStockTransaction.outQuantity += entertainmentQty
                                    productPriceTotal += (finishGoodStockTransaction.unitPrice * entertainmentQty)
                                    entertainmentQty = 0

                                    finishGoodStockTransaction.userUpdated = applicationUser
                                    finishGoodStockTransaction.lastUpdated = new Date()

                                    finishGoodStockTransactionList.add(finishGoodStockTransaction)
                                }else if(entertainmentQty > 0 && finishGoodStockTransaction.inQuantity - finishGoodStockTransaction.outQuantity <= entertainmentQty){
                                    productPriceTotal += ((finishGoodStockTransaction.inQuantity - finishGoodStockTransaction.outQuantity) * finishGoodStockTransaction.unitPrice)
                                    entertainmentQty -= (finishGoodStockTransaction.inQuantity - finishGoodStockTransaction.outQuantity)
                                    finishGoodStockTransaction.outQuantity += (finishGoodStockTransaction.inQuantity - finishGoodStockTransaction.outQuantity)

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
                                if((entertainmentQty > 0) && ((distributionPointStockTransaction.inQuantity - distributionPointStockTransaction.outQuantity) >= entertainmentQty)){
                                    distributionPointStockTransaction.outQuantity += entertainmentQty
                                    productPriceTotal += (distributionPointStockTransaction.unitPrice * entertainmentQty)
                                    entertainmentQty = 0

                                    distributionPointStockTransaction.userUpdated = applicationUser
                                    distributionPointStockTransaction.lastUpdated = new Date()

                                    distributionPointStockTransactionList.add(distributionPointStockTransaction)
                                }else if((entertainmentQty > 0)&&((distributionPointStockTransaction.inQuantity - distributionPointStockTransaction.outQuantity) < entertainmentQty)){
                                    productPriceTotal += ((distributionPointStockTransaction.inQuantity - distributionPointStockTransaction.outQuantity) * distributionPointStockTransaction.unitPrice)
                                    entertainmentQty -= (distributionPointStockTransaction.inQuantity - distributionPointStockTransaction.outQuantity)
                                    distributionPointStockTransaction.outQuantity += (distributionPointStockTransaction.inQuantity - distributionPointStockTransaction.outQuantity)

                                    distributionPointStockTransaction.userUpdated = applicationUser
                                    distributionPointStockTransaction.lastUpdated = new Date()

                                    distributionPointStockTransactionList.add(distributionPointStockTransaction)
                                }
                            }
                        }
                    }

                    if(entertainmentQty > 0){
                        checkQty += entertainmentQty
                    }

//                    if(distributionPoint.isFactory == true){
//                        productPriceTotal += productPriceService.getFactoryDPProductPriceByProduct(finishProduct.id, distributionPoint.id).averageRateWithVat * entertainmentQtyTotal
//                    }else{
//                        productPriceTotal += productPriceService.getDPProductPriceByCustomerAndProduct(distributionPointWarehouse.defaultCustomer.id, finishProduct.id).productPriceWithVat * entertainmentQtyTotal
//                    }

                    prefixCode = distributionPoint.code

                    entertainmentMiscellaneousTransactionsList.add(entertainmentMiscellaneousTransactions)
                }
            }

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
            journalHead.tableName = sessionFactory.getClassMetadata(EntertainmentMiscellaneousTransactions).tableName
            journalHead.transactionDate = new Date()
            journalHead.journalStatus = JournalStatus.CHECKED
            journalHead.transactionNo = CodeGenerationUtil.generate(8).toString()
            journalHead.journalNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
            journalHead.moduleName = ApplicationConstants.MODULE_MISCELLANEOUS_TRANSACTIONS
            if(!journalHead.validate()){
                return this.getValidationErrorMessage(journalHead)
            }

            String particular = ""
            journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)

            chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.SALES_DISCOUNT)
            if(!chartOfAccountsMapping){
                return this.getMessage("Miscellaneous Transactions", Message.ERROR, "Sales discount head is not mapped with Chart of Accounts")
            }
            particular = "Sales Discount"


            journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
            journalDetails.prefixCode = prefixCode
            journalDetails.debitAmount = productPriceTotal
            journalDetails.creditAmount = 0.00
            journalDetails.particular = particular + " paid amount"
            if(!journalDetails.validate()){
                return this.getValidationErrorMessage(journalDetails)
            }
            journalDetailsList.add(journalDetails)

            journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
            chartOfAccountsMapping =  ChartOfAccountsMapping.findByCoaType(COAType.FINISH_GOOD_STOCK)
            if(!chartOfAccountsMapping){
                return this.getMessage("Miscellaneous Transactions", Message.ERROR, "Finish good stock head is not mapped with Chart of Accounts")
            }
            particular = "Finish Good Stock"

            journalDetails.chartOfAccounts = chartOfAccountsMapping.chartOfAccounts
            journalDetails.prefixCode = prefixCode
            journalDetails.debitAmount = 0.00
            journalDetails.creditAmount = productPriceTotal
            journalDetails.particular = particular + " received amount"
            if(!journalDetails.validate()){
                return this.getValidationErrorMessage(journalDetails)
            }
            journalDetailsList.add(journalDetails)

            Map map = [:]
            map.put("entertainmentMiscellaneousTransactionsList",entertainmentMiscellaneousTransactionsList)
            map.put("finishGoodStockTransactionList",finishGoodStockTransactionList)
            map.put("distributionPointStockTransactionList",distributionPointStockTransactionList)
            map.put("journalHead",journalHead)
            map.put("journalDetailsList",journalDetailsList)

            if(checkQty > 0){
                message = this.getMessage("Miscellaneous Transactions", Message.WARNING, "Stock is Not Available.")
            }else{
                message = this.preCondition(map,applicationUser)
                if(message.type == 1){
                    Integer row = miscellaneousTransactionsService.createEntertainment(map)
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
