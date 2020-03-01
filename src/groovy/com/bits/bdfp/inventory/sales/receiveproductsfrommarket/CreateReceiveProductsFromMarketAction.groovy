package com.bits.bdfp.inventory.sales.receiveproductsfrommarket

import com.bits.bdfp.inventory.sales.DistributionPointStock
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import com.bits.bdfp.inventory.sales.ReceiveProductsFromMarketDetails
import com.docu.common.Action
import com.docu.commons.ObjectUtil
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.commons.UserMessageBuilder
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.inventory.sales.ReceiveProductsFromMarket
import com.bits.bdfp.inventory.sales.ReceiveProductsFromMarketService

@Component("createReceiveProductsFromMarketAction")
class CreateReceiveProductsFromMarketAction extends Action {
    public static final Log log = LogFactory.getLog(CreateReceiveProductsFromMarketAction.class)
    private final String MESSAGE_HEADER = 'receiveProductsFromMarket.header'
    private final String MESSAGE_SUCCESS = 'receiveProductsFromMarket.save.success'

    @Autowired
    ReceiveProductsFromMarketService receiveProductsFromMarketService
    Message message

    public Object preCondition(def params) {
        Map mapInstance = [:]
        ReceiveProductsFromMarket receiveProductsFromMarket = null
        try {
            receiveProductsFromMarket = new ReceiveProductsFromMarket()
            receiveProductsFromMarket.properties = params


            if (!receiveProductsFromMarket.validate()) {
                return message = this.getValidationErrorMessage(receiveProductsFromMarket)
            }
            mapInstance = (Map) UserMessageBuilder.createMessage(receiveProductsFromMarketService.getUserMessage(MESSAGE_HEADER, null), Message.SUCCESS, null)
            mapInstance.put(ReceiveProductsFromMarket.class.simpleName, receiveProductsFromMarket)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Receive Product From Market", Message.ERROR, "Exception-${ex.message}")
        }

        return mapInstance
    }

    public Object execute(Object params, Object object) {
        try {
            ReceiveProductsFromMarket receiveProductsFromMarket = new ReceiveProductsFromMarket(params)
            receiveProductsFromMarket.userCreated = (ApplicationUser) object
            receiveProductsFromMarket.dateCreated = new Date()
            List<ReceiveProductsFromMarketDetails> receiveProductsFromMarketDetailsList = ObjectUtil.instantiateObjects(params.items, ReceiveProductsFromMarketDetails.class)
            for(int i = 0; i < receiveProductsFromMarketDetailsList.size(); i++){
                receiveProductsFromMarketDetailsList[i].receiveProductsFromMarket = receiveProductsFromMarket
            }
            List<DistributionPointStock> distributionPointStocks = new ArrayList<DistributionPointStock>()
            List<DistributionPointStockTransaction> distributionPointStockTransactions = new ArrayList<DistributionPointStockTransaction>()
            for (int i = 0; i < receiveProductsFromMarketDetailsList.size(); i++) {
                DistributionPointStock distributionPointStock = receiveProductsFromMarketService.listDistributionTransaction(params, receiveProductsFromMarketDetailsList[i])
                DistributionPointStockTransaction distributionPointStockTransaction = new DistributionPointStockTransaction()
                List list = receiveProductsFromMarketService.listInvoiceAndPrice(
                        receiveProductsFromMarketDetailsList[i].batchAvailable? receiveProductsFromMarketDetailsList[i].invoice.id : null,
                        receiveProductsFromMarketDetailsList[i].finishProduct.id,
                        receiveProductsFromMarket.customerMaster.id,
                        receiveProductsFromMarketDetailsList[i].batchAvailable? receiveProductsFromMarketDetailsList[i].batch : null)
                if(!list){
                    message = this.getMessage('Receive Products From Market', Message.ERROR, "No invoice with the selected item generated for this salesman.")
                    return message
                }
                if(distributionPointStock){
                    distributionPointStock.lastUpdated = new Date()
                    distributionPointStock.userUpdated = (ApplicationUser) object
                    distributionPointStock.inQuantity += receiveProductsFromMarketDetailsList[i].quantity
                }else {
                    distributionPointStock = new DistributionPointStock()
                    distributionPointStock.userCreated = (ApplicationUser) object
                    distributionPointStock.dateCreated = new Date()
                    distributionPointStock.inQuantity = receiveProductsFromMarketDetailsList[i].quantity
                    distributionPointStock.subWarehouse = receiveProductsFromMarket.receivingSubInventory
                    distributionPointStock.finishProduct = receiveProductsFromMarketDetailsList[i].finishProduct
                    distributionPointStock.outQuantity = 0
                    if (receiveProductsFromMarketDetailsList[i].batchAvailable) {
                        distributionPointStock.batchNo = receiveProductsFromMarketDetailsList[i].batch
                        distributionPointStockTransaction.inInvoice = receiveProductsFromMarketDetailsList[i].invoice
                    } else {
                        distributionPointStock.batchNo = null
                    }
                }
                distributionPointStockTransaction.dateCreated = new Date()
                distributionPointStockTransaction.userCreated = (ApplicationUser) object
                distributionPointStockTransaction.inQuantity = receiveProductsFromMarketDetailsList[i].quantity
                distributionPointStockTransaction.outQuantity = 0
                distributionPointStockTransaction.transactionDate = receiveProductsFromMarket.dateCreated
                distributionPointStockTransaction.unitPrice = list[0].unit_price
                distributionPointStockTransaction.distributionPointStock = distributionPointStock
                receiveProductsFromMarketDetailsList[i].distributionPointStock = distributionPointStock
                distributionPointStocks.add(distributionPointStock)
                distributionPointStockTransactions.add(distributionPointStockTransaction)
            }

            Map map = [:]
            map.put('receiveProductsFromMarket', receiveProductsFromMarket)
            map.put('receiveProductsFromMarketDetailsList', receiveProductsFromMarketDetailsList)
            map.put("distributionPointStocks", distributionPointStocks)
            map.put("distributionPointStockTransactions", distributionPointStockTransactions)

            message = this.preCondition(map, params)
            if (message.type == 1) {
                int noOfRows = receiveProductsFromMarketService.create(map)
                if (noOfRows > 0) {
                    message = this.getMessage('Receive Products From Market', Message.SUCCESS, SUCCESS_SAVE)
                } else {
                    message = this.getMessage('Receive Products From Market', Message.ERROR, FAIL_SAVE)
                }
            }
            return message
        }catch (Exception e){
            log.error(e.message)
            throw new RuntimeException(e.message)
        }
    }

    @Override
    protected Object preCondition(Object object, Object params) {
        try {
            Map map = (Map) object
            ReceiveProductsFromMarket receiveProductsFromMarket = map.receiveProductsFromMarket
            if (!receiveProductsFromMarket.validate()) {
                message = this.getValidationErrorMessage(receiveProductsFromMarket)
            }else{
                message = this.getMessage(receiveProductsFromMarket, Message.SUCCESS, SUCCESS_SAVE)
            }
            return  message
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("ReceiveProductsFromMarket", Message.ERROR, "Exception-${ex.message}")
        }
    }

    public Object postCondition(Object object, Object params) {
        return null
    }
}