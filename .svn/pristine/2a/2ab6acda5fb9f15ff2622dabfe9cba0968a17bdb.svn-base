package com.bits.bdfp.inventory.sales.marketreturn

import com.bits.bdfp.inventory.sales.DistributionPointStock
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import com.bits.bdfp.inventory.sales.MarketReturnDetails
import com.bits.bdfp.inventory.sales.ReceiveProductsFromMarket
import com.bits.bdfp.inventory.sales.ReceiveProductsFromMarketService
import com.docu.common.Action
import com.docu.commons.ObjectUtil
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.inventory.sales.MarketReturn
import com.bits.bdfp.inventory.sales.MarketReturnService


@Component("updateMarketReturnAction")
class UpdateMarketReturnAction extends Action {
    public static final Log log = LogFactory.getLog(UpdateMarketReturnAction.class)
    private final String MESSAGE_HEADER = 'marketReturn.header'
    private final String MESSAGE_SUCCESS = 'marketReturn.update.success'

    @Autowired
    MarketReturnService marketReturnService
    @Autowired
    ReceiveProductsFromMarketService receiveProductsFromMarketService

    Message message

    @Override
    protected Object preCondition(Object params, Object object) {
        try {
            MarketReturn marketReturn = (MarketReturn) object.get('marketReturn')
            if (!marketReturn.validate()) {
                message = this.getValidationErrorMessage(marketReturn)
            } else {
                message = this.getMessage(marketReturn, Message.SUCCESS, this.SUCCESS_SAVE)
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Market Return", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    @Override
    protected Object postCondition(def Object object1, def Object object2) {
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            Map map = [:]
            MarketReturn marketReturn = MarketReturn.read(Long.parseLong(params.id))
            marketReturn.properties = params
            marketReturn.mrStatus = "MR_IN_TRANSIT"
            marketReturn.userUpdated = (ApplicationUser) object
            marketReturn.dateUpdated = new Date()

            List<DistributionPointStock> distributionPointStocks = new ArrayList<DistributionPointStock>()
            List<MarketReturnDetails> marketReturnDetailsList = ObjectUtil.instantiateObjects(params.items, MarketReturnDetails.class)
            List<DistributionPointStockTransaction> distributionPointStockTransactions = new ArrayList<DistributionPointStockTransaction>()
            for (int i = 0; i < marketReturnDetailsList.size(); i++) {
                if(!marketReturnDetailsList[i].id){
                    marketReturnDetailsList[i].marketReturn = marketReturn
                    if(marketReturn.isDpCustomer) {
                        DistributionPointStock distributionPointStock = marketReturnDetailsList[i].distributionPointStock
                        distributionPointStock.outQuantity += marketReturnDetailsList[i].quantity
                        distributionPointStock.lastUpdated = new Date()
                        distributionPointStock.userUpdated = (ApplicationUser) object
                        DistributionPointStockTransaction distributionPointStockTransaction = new DistributionPointStockTransaction()
                        if (marketReturnDetailsList[i].invoice) {
                            List list = receiveProductsFromMarketService.listInvoiceAndPrice(
                                    marketReturnDetailsList[i].invoice.id, marketReturnDetailsList[i].finishProduct.id,
                                    null, marketReturnDetailsList[i].batch)
                            distributionPointStockTransaction.unitPrice = list[0].unit_price
                            distributionPointStockTransaction.outInvoice = marketReturnDetailsList[i].invoice
                            marketReturnDetailsList[i].price = list[0].unit_price
                        } else {
//                            distributionPointStockTransaction.unitPrice = Float.parseFloat(params.('priceOf' + marketReturnDetailsList[i].finishProduct.id.toString()))
                            distributionPointStockTransaction.unitPrice = marketReturnDetailsList[i].price
                        }
                        distributionPointStockTransaction.inQuantity = 0
                        distributionPointStockTransaction.outQuantity = marketReturnDetailsList[i].quantity
                        distributionPointStockTransaction.dateCreated = new Date()
                        distributionPointStockTransaction.userCreated = (ApplicationUser) object
                        distributionPointStockTransaction.transactionDate = marketReturn.dateCreated
                        distributionPointStockTransaction.distributionPointStock = marketReturnDetailsList[i].distributionPointStock
                        distributionPointStockTransactions.add(distributionPointStockTransaction)
                        distributionPointStocks.add(distributionPointStock)
                    }
                }
            }

            map.put('marketReturn', marketReturn)
            map.put('marketReturnDetailsList', marketReturnDetailsList)
            if (marketReturn.isDpCustomer) {
                map.put('distributionPointStocks', distributionPointStocks)
                map.put('distributionPointStockTransactions', distributionPointStockTransactions)
            }

            message = this.preCondition(null, map)
            if (message.type == 1) {
                Integer integer = marketReturnService.update(map)
                if (integer == 1) {
                    message = this.getMessage(marketReturn, Message.SUCCESS, "Market Return Updated Successfully")
                } else {
                    message = this.getMessage(marketReturn, Message.ERROR, this.FAIL_UPDATE)
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Market Return", Message.ERROR, "Exception-${ex.message}")
        }
    }

}
