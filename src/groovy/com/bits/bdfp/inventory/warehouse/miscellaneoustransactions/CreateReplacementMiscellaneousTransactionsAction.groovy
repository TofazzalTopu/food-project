package com.bits.bdfp.inventory.warehouse.miscellaneoustransactions

import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointStock
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import com.bits.bdfp.inventory.sales.MarketReturnDetails
import com.bits.bdfp.inventory.warehouse.FinishGoodStock
import com.bits.bdfp.inventory.warehouse.FinishGoodStockTransaction
import com.bits.bdfp.inventory.warehouse.FinishGoodWarehouse
import com.bits.bdfp.inventory.warehouse.FinishGoodWarehouseDetails
import com.bits.bdfp.inventory.warehouse.MiscellaneousTransactionsService
import com.bits.bdfp.inventory.warehouse.ReplacementMiscellaneousTransactions
import com.bits.bdfp.inventory.warehouse.SubWarehouse
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by liton.miah on 4/27/2016.
 */
@Component("createReplacementMiscellaneousTransactionsAction")
class CreateReplacementMiscellaneousTransactionsAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    MiscellaneousTransactionsService miscellaneousTransactionsService
    Message message

    public Object preCondition(Object params, Object object) {
        try {
            Map map = (Map) params
            ApplicationUser applicationUser = (ApplicationUser) object
            List<ReplacementMiscellaneousTransactions> replacementMiscellaneousTransactionsList = (List<ReplacementMiscellaneousTransactions>) map.replacementMiscellaneousTransactionsList
            Boolean isValidate = true
            replacementMiscellaneousTransactionsList.each {
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
            List<ReplacementMiscellaneousTransactions> replacementMiscellaneousTransactionsList = []
            List<FinishGoodStockTransaction> finishGoodStockTransactionList = []
            List<DistributionPointStockTransaction> distributionPointStockTransactionList = []
            List<MarketReturnDetails> marketReturnDetailsList = []

            Float checkQty = 0
            Float replacementQty = 0

            params.products.each{key, val ->
                if(val instanceof Map){
                    ReplacementMiscellaneousTransactions replacementMiscellaneousTransactions = new ReplacementMiscellaneousTransactions(val)
                    replacementMiscellaneousTransactions.userCreated = applicationUser
                    replacementMiscellaneousTransactions.dateCreated = new Date()

                    DistributionPoint distributionPoint = DistributionPoint.get(val.distributionPoint.id)
                    FinishProduct finishProduct = FinishProduct.get(val.product.id)
                    SubWarehouse subWarehouse = SubWarehouse.get(val.subInventory.id)

                    replacementQty = Float.parseFloat(val.replacementQty)

                    if(distributionPoint.isFactory){
                        List<FinishGoodStock> finishGoodStockList = FinishGoodStock.findAllBySubWarehouseAndFinishProduct(subWarehouse,finishProduct)
                        finishGoodStockList.each {
                            FinishGoodStockTransaction finishGoodStockTransaction = FinishGoodStockTransaction.findByFinishGoodStock(it)
                            if(finishGoodStockTransaction){
                                if((replacementQty > 0) && ((finishGoodStockTransaction.inQuantity - finishGoodStockTransaction.outQuantity) >= replacementQty)){
                                    finishGoodStockTransaction.outQuantity += replacementQty
                                    replacementQty = 0

                                    finishGoodStockTransaction.userUpdated = applicationUser
                                    finishGoodStockTransaction.lastUpdated = new Date()

                                    finishGoodStockTransactionList.add(finishGoodStockTransaction)
                                }
                                else if((replacementQty > 0)&&((finishGoodStockTransaction.inQuantity - finishGoodStockTransaction.outQuantity) < replacementQty)){
                                    replacementQty -= (finishGoodStockTransaction.inQuantity - finishGoodStockTransaction.outQuantity)
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
                                if((replacementQty > 0) && ((distributionPointStockTransaction.inQuantity - distributionPointStockTransaction.outQuantity) >= replacementQty)){
                                    distributionPointStockTransaction.outQuantity += replacementQty
                                    replacementQty = 0

                                    distributionPointStockTransaction.userUpdated = applicationUser
                                    distributionPointStockTransaction.lastUpdated = new Date()

                                    distributionPointStockTransactionList.add(distributionPointStockTransaction)
                                }else if((replacementQty > 0)&&((distributionPointStockTransaction.inQuantity - distributionPointStockTransaction.outQuantity) < replacementQty)){
                                    replacementQty -= (distributionPointStockTransaction.inQuantity - distributionPointStockTransaction.outQuantity)
                                    distributionPointStockTransaction.outQuantity += (distributionPointStockTransaction.inQuantity - distributionPointStockTransaction.outQuantity)

                                    distributionPointStockTransaction.userUpdated = applicationUser
                                    distributionPointStockTransaction.lastUpdated = new Date()

                                    distributionPointStockTransactionList.add(distributionPointStockTransaction)
                                }
                            }
                        }
                    }

                    if(replacementQty > 0){
                        checkQty += replacementQty
                    }

                    String[] mrdIds = val.mrdId.split(',')
                    for(int i=0; i<mrdIds.length; i++){
                        MarketReturnDetails marketReturnDetails = MarketReturnDetails.get(Long.parseLong(mrdIds[i]))
                        marketReturnDetails.mrSattelementStatus = 'SETTLED'
                        marketReturnDetailsList.add(marketReturnDetails)
                    }

                    replacementMiscellaneousTransactionsList.add(replacementMiscellaneousTransactions)
                }
            }
            
            Map map = [:]
            map.put("replacementMiscellaneousTransactionsList",replacementMiscellaneousTransactionsList)
            map.put("finishGoodStockTransactionList",finishGoodStockTransactionList)
            map.put("distributionPointStockTransactionList",distributionPointStockTransactionList)
            map.put("marketReturnDetailsList",marketReturnDetailsList)

            if(checkQty > 0){
                message = this.getMessage("Miscellaneous Transactions", Message.WARNING, "Stock is Not Available.")
            }else{
                message = this.preCondition(map,applicationUser)
                if(message.type == 1){
                    Integer row = miscellaneousTransactionsService.createReplacement(map)
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
