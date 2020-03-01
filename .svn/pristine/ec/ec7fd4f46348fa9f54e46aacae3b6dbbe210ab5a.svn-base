package com.bits.bdfp.inventory.warehouse.transferproducts

import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointStock
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import com.bits.bdfp.inventory.sales.DistributionPointWarehouse
import com.bits.bdfp.inventory.sales.MarketReturn
import com.bits.bdfp.inventory.sales.MarketReturnDetails
import com.bits.bdfp.inventory.warehouse.FinishGoodStock
import com.bits.bdfp.inventory.warehouse.FinishGoodStockTransaction
import com.bits.bdfp.inventory.warehouse.SubInventoryToSubInventoryTransfer
import com.bits.bdfp.inventory.warehouse.SubInventoryToSubInventoryTransferService
import com.bits.bdfp.inventory.warehouse.SubWarehouse
import com.bits.bdfp.inventory.warehouse.Warehouse
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
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
@Component("createSubInventoryToSubInventoryTransferAction")
class CreateSubInventoryToSubInventoryTransferAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    SubInventoryToSubInventoryTransferService subInventoryToSubInventoryTransferService

    Message message

    public Object preCondition(Object params, Object object) {
        try {
            Map map = (Map) object
            List<SubInventoryToSubInventoryTransfer> subInventoryToSubInventoryTransferList = map.subInventoryToSubInventoryTransferList

            Integer valCount = 0

            subInventoryToSubInventoryTransferList.each {
                if (!it.validate()) {
                    message = this.getValidationErrorMessage(it)
                    valCount++
                }
            }

            if (valCount == 0) {
                message = this.getMessage("Sub Inventory To Sub Inventory Transfer", Message.SUCCESS, SUCCESS_SAVE)
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object execute(Object params, Object user) {
        try {
            int mr = 0;
            ApplicationUser applicationUser = (ApplicationUser) user

            List<SubInventoryToSubInventoryTransfer> subInventoryToSubInventoryTransferList = []

            List<DistributionPointStock> distributionPointStockList = []
            List<DistributionPointStock> distributionPointStockInList = []
            List<DistributionPointStockTransaction> distributionPointStockTransactionList = []
            List<DistributionPointStockTransaction> distributionPointStockTransactionInList = []

            List<FinishGoodStock> finishGoodStockList = []
            List<FinishGoodStock> finishGoodStockInList = []
            List<FinishGoodStockTransaction> finishGoodStockTransactionList = []
            List<FinishGoodStockTransaction> finishGoodStockTransactionInList = []

            //MR
            MarketReturn marketReturn
            List<MarketReturnDetails> marketReturnDetailsList = []
            //END

            params.products.each { key, val ->
                if (val instanceof Map) {
                    SubInventoryToSubInventoryTransfer subInventoryToSubInventoryTransfer = new SubInventoryToSubInventoryTransfer(params)
                    subInventoryToSubInventoryTransfer.inventory = Warehouse.get(Long.parseLong(params.inventoryId))
                    subInventoryToSubInventoryTransfer.subInventoryFrom = SubWarehouse.get(Long.parseLong(params.subInventoryFromId))
                    subInventoryToSubInventoryTransfer.subInventoryTo = SubWarehouse.get(Long.parseLong(params.subInventoryToId))

                    subInventoryToSubInventoryTransfer.product = FinishProduct.get(Long.parseLong(val.productId))
                    subInventoryToSubInventoryTransfer.batchNo = val.batchNo
                    subInventoryToSubInventoryTransfer.unitPrice = Float.parseFloat(val.unitPrice)
                    subInventoryToSubInventoryTransfer.transferQty = Float.parseFloat(val.transferQty)

                    subInventoryToSubInventoryTransfer.userCreated = applicationUser
                    subInventoryToSubInventoryTransfer.dateCreated = new Date()

                    subInventoryToSubInventoryTransferList.add(subInventoryToSubInventoryTransfer)

                    DistributionPoint distributionPoint = DistributionPoint.get(Long.parseLong(params.dpId))

                    if (distributionPoint.isFactory) {
                        FinishGoodStock finishGoodStock = FinishGoodStock.get(Long.parseLong(val.dpsId))
                        if (finishGoodStock.inQuantity - finishGoodStock.outQuantity >= Float.parseFloat(val.transferQty)) {
                            finishGoodStock.outQuantity += Float.parseFloat(val.transferQty)
                            finishGoodStock.userUpdated = applicationUser
                            finishGoodStock.lastUpdated = new Date()

                            if (finishGoodStock) {
                                FinishGoodStockTransaction finishGoodStockTransaction = new FinishGoodStockTransaction(
                                        finishGoodStock: finishGoodStock,
                                        transactionDate: new Date(),
                                        inQuantity: 0,
                                        unitPrice: Float.parseFloat(val.unitPrice),
                                        outQuantity: Float.parseFloat(val.transferQty),

                                        userCreated: applicationUser,
                                        dateCreated: new Date()
                                )
                                finishGoodStockTransactionList.add(finishGoodStockTransaction)
                            }
                            finishGoodStockList.add(finishGoodStock)
                        } else {
                            return this.getMessage("Sub Inventory To Sub Inventory Transfer", Message.ERROR, "Transfer quantity must less than or equal to available quantity.")
                        }
                        boolean isDpStock = false

                        List<FinishGoodStock> finishGoodStockToList = FinishGoodStock.withCriteria() {
                            eq("subWarehouse", subInventoryToSubInventoryTransfer.subInventoryTo)
                            eq("finishProduct", subInventoryToSubInventoryTransfer.product)
                            if (subInventoryToSubInventoryTransfer.batchNo == "") {
                                isNull("batchNo")
                            } else {
                                eq("batchNo", subInventoryToSubInventoryTransfer.batchNo)
                            }
                            gtProperty("inQuantity", "outQuantity")
                            order("id", "desc")
                        }
//                        List<FinishGoodStock> finishGoodStockToList = FinishGoodStock.findAllBySubWarehouseAndFinishProduct(SubWarehouse.get(Long.parseLong(params.subInventoryToId)),FinishProduct.get(Long.parseLong(val.productId)))
                        if (finishGoodStockToList && finishGoodStockToList.size() > 0) {
                            isDpStock = true
                            finishGoodStockToList[0].inQuantity += Float.parseFloat(val.transferQty)
                            finishGoodStockToList[0].userUpdated = applicationUser
                            finishGoodStockToList[0].lastUpdated = new Date()

                            FinishGoodStockTransaction finishGoodStockTransactionIn = new FinishGoodStockTransaction(
                                    finishGoodStock: finishGoodStockToList[0],
                                    transactionDate: new Date(),
                                    inQuantity: Float.parseFloat(val.transferQty),
                                    unitPrice: Float.parseFloat(val.unitPrice),
                                    outQuantity: 0,

                                    userCreated: applicationUser,
                                    dateCreated: new Date()
                            )

                            finishGoodStockInList.add(finishGoodStockToList[0])
                            finishGoodStockTransactionInList.add(finishGoodStockTransactionIn)
                        }

                        if (!isDpStock) {
                            FinishGoodStock finishGoodStockCreate = new FinishGoodStock(
                                    subWarehouse: SubWarehouse.get(Long.parseLong(params.subInventoryToId)),
                                    finishProduct: FinishProduct.get(Long.parseLong(val.productId)),
                                    batchNo: val.batchNo,
                                    inQuantity: Float.parseFloat(val.transferQty),
                                    outQuantity: 0,
                                    userCreated: applicationUser,
                                    dateCreated: new Date()
                            )

                            FinishGoodStockTransaction finishGoodStockTransactionIn = new FinishGoodStockTransaction(
                                    finishGoodStock: finishGoodStockCreate,
                                    transactionDate: new Date(),
                                    inQuantity: Float.parseFloat(val.transferQty),
                                    unitPrice: Float.parseFloat(val.unitPrice),
                                    outQuantity: 0,

                                    userCreated: applicationUser,
                                    dateCreated: new Date()
                            )

                            finishGoodStockInList.add(finishGoodStockCreate)
                            finishGoodStockTransactionInList.add(finishGoodStockTransactionIn)
                        }

                    } else {
                        //ISSUE MARKET RETURN
                        if (mr == 0 && subInventoryToSubInventoryTransfer.subInventoryTo.subWarehouseType.id == ApplicationConstants.MARKET_RETURN_TYPE_INVENTORY_ID) {
                            marketReturn = new MarketReturn()
                            marketReturn.isDpCustomer = true
                            marketReturn.destinationDistributionPoint = DistributionPoint.findByIsFactory(true)
                            //NEEDS TO CHANGE LATER
                            marketReturn.sourceDistributionPoint = distributionPoint
                            marketReturn.warehouse = subInventoryToSubInventoryTransfer.inventory
                            marketReturn.subWarehouse = subInventoryToSubInventoryTransfer.subInventoryTo
                            marketReturn.primaryCustomer = DistributionPointWarehouse.findByDistributionPoint(distributionPoint).defaultCustomer
                            marketReturn.mrNo = CodeGenerationUtil.generate(8).toString()
                            marketReturn.mrStatus = 'MR_IN_TRANSIT'
                            marketReturn.userCreated = applicationUser
                            marketReturn.dateCreated = new Date()
                            mr = 1
                        }
                        //END

                        DistributionPointStock distributionPointStock = DistributionPointStock.get(Long.parseLong(val.dpsId))
                        if (distributionPointStock.inQuantity - distributionPointStock.outQuantity >= Float.parseFloat(val.transferQty)) {
                            distributionPointStock.outQuantity += Float.parseFloat(val.transferQty)
                            distributionPointStock.userUpdated = applicationUser
                            distributionPointStock.lastUpdated = new Date()

                            if (distributionPointStock) {
                                DistributionPointStockTransaction distributionPointStockTransaction = new DistributionPointStockTransaction(
                                        distributionPointStock: distributionPointStock,
                                        transactionDate: new Date(),
                                        inQuantity: 0,
                                        unitPrice: Float.parseFloat(val.unitPrice),
                                        outQuantity: Float.parseFloat(val.transferQty),

                                        userCreated: applicationUser,
                                        dateCreated: new Date()
                                )

                                distributionPointStockTransactionList.add(distributionPointStockTransaction)
                            }

                            distributionPointStockList.add(distributionPointStock)
                        } else {
                            return this.getMessage("Sub Inventory To Sub Inventory Transfer", Message.ERROR, "Transfer quantity must less than or equal to available quantity.")
                        }

                        boolean isDpStock = false

                        //MARKET RETURN DETAILS
                        MarketReturnDetails marketReturnDetails
                        if (subInventoryToSubInventoryTransfer.subInventoryTo.subWarehouseType.id == ApplicationConstants.MARKET_RETURN_TYPE_INVENTORY_ID) {
                            marketReturnDetails = new MarketReturnDetails()
                            marketReturnDetails.mrType = 'Market Return'
                            marketReturnDetails.finishProduct = FinishProduct.get(Long.parseLong(val.productId))
                            marketReturnDetails.batch = val.batchNo
                            marketReturnDetails.quantity = Float.parseFloat(val.transferQty)
                            marketReturnDetails.price = Float.parseFloat(val.unitPrice)
                            marketReturnDetails.marketReturn = marketReturn
                            marketReturnDetailsList.add(marketReturnDetails)
                        }
                        //END

                        List<DistributionPointStock> distributionPointStockToList = DistributionPointStock.withCriteria() {
                            eq("subWarehouse", subInventoryToSubInventoryTransfer.subInventoryTo)
                            eq("finishProduct", subInventoryToSubInventoryTransfer.product)
                            if (subInventoryToSubInventoryTransfer.batchNo == "") {
                                isNull("batchNo")
                            } else {
                                eq("batchNo", subInventoryToSubInventoryTransfer.batchNo)
                            }
                            gtProperty("inQuantity", "outQuantity")
                            order("id", "desc")
                        }

                        if (distributionPointStockToList && distributionPointStockToList.size() > 0) {
                            isDpStock = true
                            distributionPointStockToList[0].inQuantity += Float.parseFloat(val.transferQty)
                            distributionPointStockToList[0].userUpdated = applicationUser
                            distributionPointStockToList[0].lastUpdated = new Date()

                            DistributionPointStockTransaction distributionPointStockTransactionIn = new DistributionPointStockTransaction(
                                    distributionPointStock: distributionPointStockToList[0],
                                    transactionDate: new Date(),
                                    inQuantity: Float.parseFloat(val.transferQty),
                                    unitPrice: Float.parseFloat(val.unitPrice),
                                    outQuantity: 0,

                                    userCreated: applicationUser,
                                    dateCreated: new Date()
                            )

                            //MR
                            if (marketReturnDetails) {
                                marketReturnDetails.distributionPointStock = distributionPointStockToList[0]
                            }
                            //END
                            distributionPointStockInList.add(distributionPointStockToList[0])
                            distributionPointStockTransactionInList.add(distributionPointStockTransactionIn)
                        }

                        if (!isDpStock) {
                            DistributionPointStock distributionPointStockCreate = new DistributionPointStock(
                                    subWarehouse: SubWarehouse.get(Long.parseLong(params.subInventoryToId)),
                                    finishProduct: FinishProduct.get(Long.parseLong(val.productId)),
                                    batchNo: val.batchNo,
                                    inQuantity: Float.parseFloat(val.transferQty),
                                    outQuantity: 0,
                                    userCreated: applicationUser,
                                    dateCreated: new Date()
                            )

                            DistributionPointStockTransaction distributionPointStockTransactionIn = new DistributionPointStockTransaction(
                                    distributionPointStock: distributionPointStockCreate,
                                    transactionDate: new Date(),
                                    inQuantity: Float.parseFloat(val.transferQty),
                                    unitPrice: Float.parseFloat(val.unitPrice),
                                    outQuantity: 0,

                                    userCreated: applicationUser,
                                    dateCreated: new Date()
                            )

                            //MR
                            if (marketReturnDetails) {
                                marketReturnDetails.distributionPointStock = distributionPointStockCreate
                            }
                            //END
                            distributionPointStockInList.add(distributionPointStockCreate)
                            distributionPointStockTransactionInList.add(distributionPointStockTransactionIn)
                        }
                    }
                }
            }

            Map map = [:]
            map.put("subInventoryToSubInventoryTransferList", subInventoryToSubInventoryTransferList)

            map.put("distributionPointStockList", distributionPointStockList)
            map.put("distributionPointStockInList", distributionPointStockInList)
            map.put("distributionPointStockTransactionList", distributionPointStockTransactionList)
            map.put("distributionPointStockTransactionInList", distributionPointStockTransactionInList)

            map.put("finishGoodStockList", finishGoodStockList)
            map.put("finishGoodStockInList", finishGoodStockInList)
            map.put("finishGoodStockTransactionList", finishGoodStockTransactionList)
            map.put("finishGoodStockTransactionInList", finishGoodStockTransactionInList)

            //MR
            if (mr == 1) {
                map.put("marketReturn", marketReturn)
                map.put("marketReturnDetailsList", marketReturnDetailsList)
            }
            //END

            message = preCondition(params, map)

            if (message.type == 1) {
                Integer subInventoryToSubInventoryTransferRow = subInventoryToSubInventoryTransferService.createSubInventoryToSubInventoryTransfer(map)
                if (subInventoryToSubInventoryTransferRow > 0) {
                    message = this.getMessage("Sub Inventory To Sub Inventory Transfer", Message.SUCCESS, "Sub Inventory To Sub Inventory Transfer Created Successfully.")
                } else {
                    message = this.getMessage("Sub Inventory To Sub Inventory Transfer", Message.ERROR, FAIL_SAVE)
                }
            }

            return message
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }

    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}
