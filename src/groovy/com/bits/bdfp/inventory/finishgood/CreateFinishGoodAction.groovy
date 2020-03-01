package com.bits.bdfp.inventory.finishgood

import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.product.FinishProductService
import com.bits.bdfp.inventory.warehouse.FinishGoodStock
import com.bits.bdfp.inventory.warehouse.FinishGoodStockTransaction
import com.bits.bdfp.inventory.warehouse.FinishGoodWarehouse
import com.bits.bdfp.inventory.warehouse.FinishGoodWarehouseDetails
import com.bits.bdfp.inventory.warehouse.FinishGoodWarehouseService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

@Component("createFinishGoodAction")
class CreateFinishGoodAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    FinishGoodWarehouseService finishGoodWarehouseService
    @Autowired
    FinishProductService finishProductService
    @Autowired
    SpringSecurityService   springSecurityService
    Message message

    public Object preCondition(Object params, Object object) {
        List<FinishGoodWarehouseDetails> finishGoodWarehouseDetailsArrayList = new ArrayList<FinishGoodWarehouseDetails>()
        List<FinishGoodStock> finishGoodStockList= new ArrayList<FinishGoodStock>()
        List<FinishGoodStockTransaction> finishGoodStockTransactionList = new ArrayList<FinishGoodStockTransaction>()
        Boolean isValid = false
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        FinishGoodWarehouse finishGoodWarehouse = (FinishGoodWarehouse) object
        try {
            String yearPart = finishGoodWarehouse.dateCreated.format("yyyy")//toString().split("-")[2]
            if(params.finishProductId){
                FinishProduct finishProduct = finishProductService.read(Long.parseLong(params.finishProductId))
                EnterpriseConfiguration enterpriseConfiguration = finishProduct.enterpriseConfiguration
                finishGoodWarehouse.transactionNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration,"FINISH_PRODUCT_TRANSACTION_REF",null,null,null,null,null,null,yearPart,null,null)
            }
            if (finishGoodWarehouse.validate()) {
                List goodsByDateTimeAndBatchCtl = finishGoodWarehouseService.findByDateTimeAndBatchControl(finishGoodWarehouse)
                if(goodsByDateTimeAndBatchCtl && goodsByDateTimeAndBatchCtl.size()>0){
                    message = this.getMessage("Finish Good  Setup", Message.ERROR, "Finish good with same batch no,date and time already exist")
                    return [message: message, isValid: isValid, finishGoodWarehouseDetailsArrayList: finishGoodWarehouseDetailsArrayList, finishGoodStockList: finishGoodStockList,finishGood:finishGoodWarehouse]
                }
                params.items.each { key, val ->
                    if (val instanceof Map) {
                        Float costValue = Double.parseDouble(val.cost)
                        Float quantity = Double.parseDouble(val.quantity)

                        FinishProduct finishProduct = finishProductService.read(Long.parseLong(val.finishProduct.id))
                        if(finishProduct){
                            EnterpriseConfiguration finishProductEnterprise = finishProduct.enterpriseConfiguration
                            FinishGoodStock finishGoodStock = finishGoodWarehouseService.findBySubWarehouseAndFinishProductAndBatchNo(finishGoodWarehouse.subWarehouse, finishProduct, finishGoodWarehouse.batchNo)
                            if(finishGoodStock){
                                finishGoodStock.inQuantity += quantity
                                finishGoodStock.userUpdated = applicationUser
                            }
                            else{
                                finishGoodStock = new FinishGoodStock()
                                finishGoodStock.finishProduct = finishProduct
                                finishGoodStock.inQuantity = quantity
                                finishGoodStock.outQuantity = 0
                                finishGoodStock.subWarehouse = finishGoodWarehouse.subWarehouse
                                finishGoodStock.batchNo = finishGoodWarehouse.batchNo
                                finishGoodStock.userCreated = applicationUser
                            }

                            if(!finishGoodStock.validate()){
                                throw new RuntimeException(this.getValidationErrorMessage(finishGoodStock).messageBody[0].toString())
                            }

                            finishGoodStockList.add(finishGoodStock)

                            FinishGoodWarehouseDetails finishGoodWarehouseDetails = new FinishGoodWarehouseDetails()
                            finishGoodWarehouseDetails.finishGoodWarehouse = finishGoodWarehouse
                            finishGoodWarehouseDetails.finishProduct = finishProduct
                            finishGoodWarehouseDetails.productRefNo = CodeGenerationUtil.instance.generateCode(finishProductEnterprise, "FINISH_PRODUCT_PRODUCT_REF", null,null,null,null,null,null,yearPart,finishProduct.code,null)
                            finishGoodWarehouseDetails.userCreated = applicationUser
                            finishGoodWarehouseDetails.dateCreated = new Date()
                            finishGoodWarehouseDetails.cost = costValue
                            finishGoodWarehouseDetails.confirmCost = costValue
                            finishGoodWarehouseDetails.quantity = quantity
                            if (!finishGoodWarehouseDetails.validate()) {
                                throw new RuntimeException(this.getValidationErrorMessage(finishGoodWarehouseDetails).messageBody[0].toString())
                            }
                            finishGoodWarehouseDetailsArrayList.add(finishGoodWarehouseDetails)

                            FinishGoodStockTransaction finishGoodStockTransaction = new FinishGoodStockTransaction()
                            finishGoodStockTransaction.finishGoodStock = finishGoodStock
                            finishGoodStockTransaction.inQuantity = quantity
                            finishGoodStockTransaction.outQuantity = 0
                            finishGoodStockTransaction.finishGoodWarehouseDetails =  finishGoodWarehouseDetails
                            finishGoodStockTransaction.unitPrice = costValue
                            finishGoodStockTransaction.transactionDate = new Date()
                            finishGoodStockTransaction.userCreated = applicationUser

                            if (!finishGoodStockTransaction.validate()) {
                                throw new RuntimeException(this.getValidationErrorMessage(finishGoodStockTransaction).messageBody[0].toString())
                            }

                            finishGoodStockTransactionList.add(finishGoodStockTransaction)
                        }
                    }
                }
                isValid = true
            } else {
                message = this.getValidationErrorMessage(finishGoodWarehouse)
            }
            return [message: message, isValid: isValid, finishGoodWarehouseDetailsList: finishGoodWarehouseDetailsArrayList, finishGoodStockList: finishGoodStockList,finishGoodWirehouse:finishGoodWarehouse,finishGoodStockTransactionList:finishGoodStockTransactionList]
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            FinishGoodWarehouse finishGoodWarehouse = new FinishGoodWarehouse()
            finishGoodWarehouse.properties = params
            if(!finishGoodWarehouse.isBatchControl){
                Date dateNow = new Date()
                SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
                String currentMonth = formatMonth.format(dateNow)
                SimpleDateFormat formatYear = new SimpleDateFormat("YY")
                String currentYear = formatYear.format(dateNow)
                SimpleDateFormat formatDay = new SimpleDateFormat ("dd")
                String currentDay = formatDay.format(dateNow)
                finishGoodWarehouse.batchNo = '' + currentYear + '' + currentMonth + '' + currentDay
            }
            finishGoodWarehouse.userCreated = applicationUser
            finishGoodWarehouse.dateCreated = new Date()
            Map finishGoodWarehouseMap = (Map) this.preCondition(params, finishGoodWarehouse)
            if (finishGoodWarehouseMap?.isValid) {
                if(finishGoodWarehouseMap.finishGoodWarehouseDetailsList.size() <= 0){
                    return this.getMessage("Message", Message.ERROR, "No Stock Items in grid")
                }
                finishGoodWarehouse = finishGoodWarehouseService.create(finishGoodWarehouseMap)
                if (finishGoodWarehouse) {
                    return this.getMessage("Message", Message.SUCCESS, "Factory Stock Created Successfully")
                } else {
                    return this.getMessage("Message", Message.ERROR, "Factory Stock Create Failed")
                }
            } else {
                return finishGoodWarehouseMap?.message
            }

        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}