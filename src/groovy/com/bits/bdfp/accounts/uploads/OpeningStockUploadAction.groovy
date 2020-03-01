package com.bits.bdfp.accounts.uploads

import com.bits.bdfp.accounts.*
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.sales.DistributionPointStock
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import com.bits.bdfp.inventory.warehouse.SubWarehouse
import com.bits.bdfp.inventory.warehouse.Warehouse
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * Created by liton.miah on 10/25/2016.
 */

@Component("openingStockUploadAction")
class OpeningStockUploadAction extends Action{
    private static final Log log = LogFactory.getLog(this)

    @Autowired
    ChartOfAccountUploadService chartOfAccountUploadService
    Message message

    public Map preCondition(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            String productLine = ''
            String inventoryLine = ''
            String subInventoryLine = ''
            String msg = ''
            String lines = ''

            params.dataList.each{key, val->
                if(val instanceof Map){
                    FinishProduct finishProduct = FinishProduct.read(Long.parseLong(val.productId))
                    if(!finishProduct){
                        if(productLine){
                            productLine += ","+val.rowNum
                        }else{
                            productLine = val.rowNum
                        }
                    }

                    Warehouse warehouse = Warehouse.read(Long.parseLong(val.inventoryId))
                    if(!warehouse){
                        if(inventoryLine){
                            inventoryLine += ","+val.rowNum
                        }else{
                            inventoryLine = val.rowNum
                        }
                    }

                    SubWarehouse subWarehouse = SubWarehouse.read(Long.parseLong(val.subInventoryId))
                    if(!subWarehouse){
                        if(subInventoryLine){
                            subInventoryLine += ","+val.rowNum
                        }else{
                            subInventoryLine = val.rowNum
                        }
                    }
                }
            }

            if(productLine){
                if(lines){
                    lines += ","+productLine
                }else{
                    lines = productLine
                }
            }

            if(inventoryLine){
                if(lines){
                    lines += ","+inventoryLine
                }else{
                    lines = inventoryLine
                }
            }

            if(subInventoryLine){
                if(lines){
                    lines += ","+subInventoryLine
                }else{
                    lines = subInventoryLine
                }
            }

            if(lines){
                message = this.getMessage("Opening Stock Upload", Message.ERROR, "Error!")
            }else{
                message = this.getMessage("Opening Stock Upload", Message.SUCCESS, "System checked all data successfully.")
            }

            return  [message:message, lines:lines, productLine:productLine, inventoryLine:inventoryLine, subInventoryLine:subInventoryLine]
        } catch (Exception ex) {
            log.error(ex.message)
            throw  new RuntimeException(ex.message)
        }
    }

    public Object execute(Object object, Object params) {
        try {
            ApplicationUser applicationUser=(ApplicationUser) object

            List<DistributionPointStock> distributionPointStockList = []
            List<DistributionPointStockTransaction> distributionPointStockTransactionList = []

            DateFormat df = new SimpleDateFormat("dd-MM-yyyy")
            Date transactionDate =  df.parse(params.transactionDate)
            Float quantity = 0

            params.dataList.each{key, val->
                if(val instanceof Map){
                    FinishProduct finishProduct = FinishProduct.read(Long.parseLong(val.productId))
                    Warehouse warehouse = Warehouse.read(Long.parseLong(val.inventoryId))
                    SubWarehouse subWarehouse = SubWarehouse.findByIdAndWarehouse(Long.parseLong(val.subInventoryId),warehouse)
                    boolean isDpStock = false
                    quantity = Float.parseFloat(val.quantity)

                    List<DistributionPointStock> distributionPointStockToList = DistributionPointStock.findAllBySubWarehouseAndFinishProduct(subWarehouse,finishProduct)
                    if(distributionPointStockToList.size()>0){
                        distributionPointStockToList.each {
                            if(it.batchNo == (val.batchNumber?val.batchNumber:null) && quantity>0){
                                isDpStock = true
                                it.inQuantity += quantity
                                it.userUpdated = applicationUser
                                it.lastUpdated = new Date()

                                DistributionPointStockTransaction distributionPointStockTransaction = new DistributionPointStockTransaction(
                                        distributionPointStock:it,
                                        transactionDate:transactionDate,
                                        inQuantity:quantity,
                                        unitPrice:Float.parseFloat(val.perUnitCostValue),
                                        outQuantity:0,

                                        userCreated: applicationUser,
                                        dateCreated: new Date()
                                )

                                quantity = 0

                                distributionPointStockTransactionList.add(distributionPointStockTransaction)
                                distributionPointStockList.add(it)
                            }
                        }
                    }

                    if(!isDpStock && quantity>0) {
                        DistributionPointStock distributionPointStock= new DistributionPointStock(
                                subWarehouse: subWarehouse,
                                finishProduct: finishProduct,
                                batchNo: val.batchNumber,
                                inQuantity: quantity,
                                outQuantity: 0,
                                userCreated: applicationUser,
                                dateCreated: new Date()
                        )

                        DistributionPointStockTransaction distributionPointStockTransaction = new DistributionPointStockTransaction(
                                distributionPointStock: distributionPointStock,
                                transactionDate: transactionDate,
                                inQuantity: quantity,
                                unitPrice: Float.parseFloat(val.perUnitCostValue),
                                outQuantity: 0,

                                userCreated: applicationUser,
                                dateCreated: new Date()
                        )

                        distributionPointStockTransactionList.add(distributionPointStockTransaction)
                        distributionPointStockList.add(distributionPointStock)
                    }
                }
            }

            if(distributionPointStockList && distributionPointStockList.size()>0){
                distributionPointStockList.each {
                    if(!it.validate()){
                        return this.getValidationErrorMessage(it)
                    }else{
                        message = this.getMessage("Opening Stock Upload", Message.SUCCESS, SUCCESS_SAVE)
                    }
                }
            }else{
                message = this.getMessage("Opening Stock Upload", Message.ERROR, FAIL_SAVE)
            }

            if(distributionPointStockTransactionList && distributionPointStockTransactionList.size()>0){
                distributionPointStockTransactionList.each {
                    if(!it.validate()){
                        return this.getValidationErrorMessage(it)
                    }else{
                        message = this.getMessage("Opening Stock Upload", Message.SUCCESS, SUCCESS_SAVE)
                    }
                }
            }else{
                message = this.getMessage("Opening Stock Upload", Message.ERROR, FAIL_SAVE)
            }


            Map map = [:]
            map.put("distributionPointStockList",distributionPointStockList)
            map.put("distributionPointStockTransactionList",distributionPointStockTransactionList)

            if(message.type == 1){
                Integer rows = chartOfAccountUploadService.migrateAllOpeningStock(map)
                if(rows>0){
                    message = this.getMessage("Opening Stock Upload", Message.SUCCESS, "All data has been migrated successfully.")
                }else{
                    message = this.getMessage("Opening Stock Upload", Message.ERROR, FAIL_SAVE)
                }
            }
            return message

        } catch (Exception ex) {
            log.error(ex.message)
            throw  new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }
}
