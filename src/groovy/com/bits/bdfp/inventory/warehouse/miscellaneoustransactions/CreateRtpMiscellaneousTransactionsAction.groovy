package com.bits.bdfp.inventory.warehouse.miscellaneoustransactions

import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointStock
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import com.bits.bdfp.inventory.warehouse.*
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
@Component("createRtpMiscellaneousTransactionsAction")
class CreateRtpMiscellaneousTransactionsAction extends Action {
    private static final Log log = LogFactory.getLog(this)

    @Autowired
    MiscellaneousTransactionsService miscellaneousTransactionsService
    Message message

    public Object preCondition(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            List<RtpMiscellaneousTransactions> rtpMiscellaneousTransactionsList = (List<RtpMiscellaneousTransactions>) params.rtpMiscellaneousTransactionsList
            Boolean isValidate = true
            rtpMiscellaneousTransactionsList.each {
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
            List<RtpMiscellaneousTransactions> rtpMiscellaneousTransactionsList = []
            List<DistributionPointStockTransaction> distributionPointStockTransactionList = []
            List<FinishGoodStockTransaction> finishGoodStockTransactionList = []

            Float checkQty = 0
            Float returnQty = 0

            params.products.each{key, val ->
                if(val instanceof Map){
                    RtpMiscellaneousTransactions rtpMiscellaneousTransactions = new RtpMiscellaneousTransactions(val)

                    DistributionPoint distributionPoint = DistributionPoint.get(val.distributionPoint.id)
                    FinishProduct finishProduct = FinishProduct.get(val.product.id)
                    SubWarehouse subWarehouse = SubWarehouse.get(val.subInventory.id)
                    returnQty = Float.parseFloat(val.returnQty)

                    rtpMiscellaneousTransactions.userCreated = applicationUser
                    rtpMiscellaneousTransactions.dateCreated = new Date()

                    if(distributionPoint.isFactory){
                        List<FinishGoodStock> finishGoodStockList = FinishGoodStock.findAllBySubWarehouseAndFinishProduct(subWarehouse,finishProduct)
                        finishGoodStockList.each {
                            FinishGoodStockTransaction finishGoodStockTransaction = FinishGoodStockTransaction.findByFinishGoodStock(it)
                            if(finishGoodStockTransaction){
                                if((finishGoodStockTransaction.inQuantity - finishGoodStockTransaction.outQuantity) >= returnQty && returnQty > 0){
                                    finishGoodStockTransaction.outQuantity += returnQty
                                    returnQty = 0

                                    finishGoodStockTransaction.userUpdated = applicationUser
                                    finishGoodStockTransaction.lastUpdated = new Date()

                                    finishGoodStockTransactionList.add(finishGoodStockTransaction)

                                }else if(returnQty > 0 && (finishGoodStockTransaction.inQuantity - finishGoodStockTransaction.outQuantity) < returnQty){
                                    returnQty -= (finishGoodStockTransaction.inQuantity - finishGoodStockTransaction.outQuantity)
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
                                if((returnQty > 0) && ((distributionPointStockTransaction.inQuantity - distributionPointStockTransaction.outQuantity) >= returnQty)){
                                    distributionPointStockTransaction.outQuantity += returnQty
                                    returnQty = 0

                                    distributionPointStockTransaction.userUpdated = applicationUser
                                    distributionPointStockTransaction.lastUpdated = new Date()

                                    distributionPointStockTransactionList.add(distributionPointStockTransaction)
                                }else if((returnQty > 0)&&((distributionPointStockTransaction.inQuantity - distributionPointStockTransaction.outQuantity) < returnQty)){
                                    returnQty -= (distributionPointStockTransaction.inQuantity - distributionPointStockTransaction.outQuantity)
                                    distributionPointStockTransaction.outQuantity += (distributionPointStockTransaction.inQuantity - distributionPointStockTransaction.outQuantity)

                                    distributionPointStockTransaction.userUpdated = applicationUser
                                    distributionPointStockTransaction.lastUpdated = new Date()

                                    distributionPointStockTransactionList.add(distributionPointStockTransaction)
                                }
                            }
                        }
                    }

                    if(returnQty > 0){
                        checkQty += returnQty
                    }

                    rtpMiscellaneousTransactionsList.add(rtpMiscellaneousTransactions)
                }
            }
            
            Map map = [:]
            map.put("rtpMiscellaneousTransactionsList",rtpMiscellaneousTransactionsList)
            map.put("finishGoodStockTransactionList",finishGoodStockTransactionList)
            map.put("distributionPointStockTransactionList",distributionPointStockTransactionList)

            if(checkQty > 0){
                message = this.getMessage("Miscellaneous Transactions", Message.WARNING, "Stock is Not Available.")
            }else{
                message = this.preCondition(map,applicationUser)
                if(message.type == 1){
                    Integer row = miscellaneousTransactionsService.createRtp(map)
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
