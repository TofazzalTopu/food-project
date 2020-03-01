package com.bits.bdfp.inventory.sales.marketreturn

import com.bits.bdfp.inventory.sales.DistributionPointStock
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import com.bits.bdfp.inventory.sales.MarketReturnDetails
import com.bits.bdfp.inventory.sales.ReceiveProductsFromMarketService
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.commons.ObjectUtil
import com.docu.security.ApplicationUser
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.inventory.sales.MarketReturn
import com.bits.bdfp.inventory.sales.MarketReturnService

@Component("createMarketReturnAction")
class CreateMarketReturnAction extends Action {
    public static final Log log = LogFactory.getLog(CreateMarketReturnAction.class)
    private final String MESSAGE_HEADER = 'marketReturn.header'
    private final String MESSAGE_SUCCESS = 'marketReturn.save.success'

    @Autowired
    SessionFactory sessionFactory

    @Autowired
    MarketReturnService marketReturnService
    @Autowired
    ReceiveProductsFromMarketService receiveProductsFromMarketService

    Message message

    @Override
    protected Object preCondition(Object object, Object params) {
        try {
            Map map = (Map) object
            MarketReturn marketReturn = map.marketReturn
            if (!marketReturn.validate()) {
                message = this.getValidationErrorMessage(marketReturn)
            }else{
                message = this.getMessage(marketReturn, Message.SUCCESS, SUCCESS_SAVE)
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("MarketReturn", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    @Override
    protected Object postCondition(def Object object1,def Object object2) {
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            MarketReturn marketReturn = new MarketReturn(params)
            marketReturn.userCreated = (ApplicationUser) object
            marketReturn.dateCreated = new Date()
            marketReturn.mrNo = CodeGenerationUtil.generate(8).toString()
            marketReturn.mrStatus = 'MR_IN_TRANSIT'

            List<DistributionPointStock> distributionPointStocks = new ArrayList<DistributionPointStock>()
            List<DistributionPointStockTransaction> distributionPointStockTransactions = new ArrayList<DistributionPointStockTransaction>()
            List<MarketReturnDetails> marketReturnDetailsList = ObjectUtil.instantiateObjects(params.items, MarketReturnDetails.class)

            for (int i = 0; i < marketReturnDetailsList.size(); i++) {
                marketReturnDetailsList[i].marketReturn = marketReturn
                if (params.isDpCustomer == 'true') {
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
                        marketReturnDetailsList[i].price = list[0].unit_price
                        distributionPointStockTransaction.outInvoice = marketReturnDetailsList[i].invoice
                    } else {
//                        distributionPointStockTransaction.unitPrice = Float.parseFloat(params.('priceOf' + marketReturnDetailsList[i].finishProduct.id.toString()))
                        distributionPointStockTransaction.unitPrice = marketReturnDetailsList[i].price
                    }
                    distributionPointStockTransaction.inQuantity = 0
                    distributionPointStockTransaction.outQuantity = marketReturnDetailsList[i].quantity
                    distributionPointStockTransaction.dateCreated = new Date()
                    distributionPointStockTransaction.userCreated = (ApplicationUser) object
                    distributionPointStockTransaction.transactionDate = marketReturn.dateCreated
                    distributionPointStockTransaction.distributionPointStock = distributionPointStock
                    distributionPointStockTransactions.add(distributionPointStockTransaction)
                    distributionPointStocks.add(distributionPointStock)
                }else{
                    List list = receiveProductsFromMarketService.listInvoiceAndPrice(
                            marketReturnDetailsList[i].invoice?.id, marketReturnDetailsList[i].finishProduct.id,
                            marketReturn.primaryCustomer.id, marketReturnDetailsList[i].invoice? marketReturnDetailsList[i].batch:null)
                    if(!list){
                        message = this.getMessage('Market Return', Message.ERROR, "No invoice with the selected item generated for this customer.")
                        return message
                    }
                    marketReturnDetailsList[i].price = list[0].unit_price
                }
            }

            Map map = [:]
            map.put('marketReturn', marketReturn)
            map.put('marketReturnDetailsList', marketReturnDetailsList)
            if (params.isDpCustomer == 'true') {
                map.put('distributionPointStocks', distributionPointStocks)
                map.put('distributionPointStockTransactions', distributionPointStockTransactions)
            }

            message = this.preCondition(map, params)
            if (message.type == 1) {
                int noOfRows = marketReturnService.create(map)
                if (noOfRows > 0) {
                    message = this.getMessage(marketReturn, Message.SUCCESS, 'Market Return Successfully Saved For MR no: ' + marketReturn.mrNo)
                } else {
                    message = this.getMessage(marketReturn, Message.ERROR, 'Market Return Could Not Be Saved.')
                }
            }
            return message

        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

}