package com.bits.bdfp.inventory.warehouse.transferproducts

import com.bits.bdfp.inventory.sales.DistributionPointStock
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import com.bits.bdfp.inventory.warehouse.FinishGoodStock
import com.bits.bdfp.inventory.warehouse.FinishGoodStockTransaction
import com.bits.bdfp.inventory.warehouse.InventoryToInventoryTransfer
import com.bits.bdfp.inventory.warehouse.InventoryToInventoryTransferService
import com.bits.bdfp.inventory.warehouse.InventoryToInventoryTransferStatus
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
@Component("receiveInventoryToSubInventoryTransferAction")
class ReceiveInventoryToSubInventoryTransferAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    InventoryToInventoryTransferService inventoryToInventoryTransferService

    Message message

    public Object preCondition(Object params, Object object) {
        try {
            Map map = (Map) object
            InventoryToInventoryTransfer inventoryToInventoryTransfer = map.inventoryToInventoryTransfer

            if(!inventoryToInventoryTransfer.validate()){
                message = this.getValidationErrorMessage(inventoryToInventoryTransfer)
            }else{
                message = this.getMessage("Receive Inventory To Inventory Transfer", Message.SUCCESS, SUCCESS_SAVE)
            }

        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object execute(Object params, Object user) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) user

            InventoryToInventoryTransfer inventoryToInventoryTransfer = InventoryToInventoryTransfer.get(Long.parseLong(params.transferId))
            inventoryToInventoryTransfer.transferChallanStatus = InventoryToInventoryTransferStatus.TRANSFERRED
            inventoryToInventoryTransfer.userUpdated = applicationUser
            inventoryToInventoryTransfer.lastUpdated = new Date()

            FinishGoodStock finishGoodStock = null
            FinishGoodStockTransaction finishGoodStockTransaction = null
            DistributionPointStock distributionPointStock = null
            DistributionPointStockTransaction distributionPointStockTransaction = null
            boolean isDpStock = false

            if(inventoryToInventoryTransfer.receiverDp.isFactory){
                if(inventoryToInventoryTransfer){
                    List<FinishGoodStock> finishGoodStockToList = FinishGoodStock.findAllBySubWarehouse(SubWarehouse.get(Long.parseLong(params.receiverSubInventory)))
                    if(finishGoodStockToList.size()>0){
                        finishGoodStockToList.each {
                            if(it.batchNo == inventoryToInventoryTransfer.batch && it.finishProduct.id == inventoryToInventoryTransfer.transferProduct.id){
                                isDpStock = true
                                it.inQuantity += inventoryToInventoryTransfer.transferQty
                                it.userUpdated = applicationUser
                                it.lastUpdated = new Date()

                                finishGoodStockTransaction = new FinishGoodStockTransaction(
                                        finishGoodStock: it,
                                        transactionDate:new Date(),
                                        inQuantity:inventoryToInventoryTransfer.transferQty,
                                        unitPrice:inventoryToInventoryTransfer.unitPrice,
                                        outQuantity:0,

                                        userCreated: applicationUser,
                                        dateCreated: new Date()
                                )

                                finishGoodStock = it
                                return true
                            }
                        }
                    }

                    if(!isDpStock) {
                        finishGoodStock= new FinishGoodStock(
                                subWarehouse: SubWarehouse.get(Long.parseLong(params.receiverSubInventory)),
                                finishProduct: inventoryToInventoryTransfer.transferProduct,
                                batchNo: inventoryToInventoryTransfer.batch,
                                inQuantity: inventoryToInventoryTransfer.transferQty,
                                outQuantity: 0,
                                userCreated: applicationUser,
                                dateCreated: new Date()
                        )

                        finishGoodStockTransaction = new FinishGoodStockTransaction(
                                finishGoodStock: finishGoodStock,
                                transactionDate: new Date(),
                                inQuantity: inventoryToInventoryTransfer.transferQty,
                                unitPrice: inventoryToInventoryTransfer.unitPrice,
                                outQuantity: 0,

                                userCreated: applicationUser,
                                dateCreated: new Date()
                        )
                    }
                }
            }else{
                if(inventoryToInventoryTransfer){
                    List<DistributionPointStock> distributionPointStockToList = DistributionPointStock.findAllBySubWarehouse(SubWarehouse.get(Long.parseLong(params.receiverSubInventory)))
                    if(distributionPointStockToList.size()>0){
                        distributionPointStockToList.each {
                            if(it.batchNo == inventoryToInventoryTransfer.batch && it.finishProduct.id == inventoryToInventoryTransfer.transferProduct.id){
                                isDpStock = true
                                it.inQuantity += inventoryToInventoryTransfer.transferQty
                                it.userUpdated = applicationUser
                                it.lastUpdated = new Date()

                                distributionPointStockTransaction = new DistributionPointStockTransaction(
                                        distributionPointStock:it,
                                        transactionDate:new Date(),
                                        inQuantity:inventoryToInventoryTransfer.transferQty,
                                        unitPrice:inventoryToInventoryTransfer.unitPrice,
                                        outQuantity:0,

                                        userCreated: applicationUser,
                                        dateCreated: new Date()
                                )

                                distributionPointStock = it
                            }
                        }
                    }

                    if(!isDpStock) {
                        distributionPointStock= new DistributionPointStock(
                                subWarehouse: SubWarehouse.get(Long.parseLong(params.receiverSubInventory)),
                                finishProduct: inventoryToInventoryTransfer.transferProduct,
                                batchNo: inventoryToInventoryTransfer.batch,
                                inQuantity: inventoryToInventoryTransfer.transferQty,
                                outQuantity: 0,
                                userCreated: applicationUser,
                                dateCreated: new Date()
                        )

                        distributionPointStockTransaction = new DistributionPointStockTransaction(
                                distributionPointStock: distributionPointStock,
                                transactionDate: new Date(),
                                inQuantity: inventoryToInventoryTransfer.transferQty,
                                unitPrice: inventoryToInventoryTransfer.unitPrice,
                                outQuantity: 0,

                                userCreated: applicationUser,
                                dateCreated: new Date()
                        )
                    }
                }
            }

            Map map = [:]
            map.put("inventoryToInventoryTransfer",inventoryToInventoryTransfer)
            map.put("finishGoodStock",finishGoodStock)
            map.put("finishGoodStockTransaction",finishGoodStockTransaction)
            map.put("distributionPointStock",distributionPointStock)
            map.put("distributionPointStockTransaction",distributionPointStockTransaction)

            message = preCondition(params, map)

            if(message.type == 1){
                Integer noOfRows = inventoryToInventoryTransferService.receiveTransfer(map)
                if (noOfRows>0) {
                    message = this.getMessage("Receive Inventory To Inventory Transfer", Message.SUCCESS, "Inventory To SInventory Transfer Product Received Successfully.")
                } else {
                    message = this.getMessage("Receive Inventory To Inventory Transfer", Message.ERROR, FAIL_SAVE)
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
