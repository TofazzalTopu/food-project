package com.bits.bdfp.inventory.finishgood

import com.bits.bdfp.inventory.product.FinishProductService
import com.bits.bdfp.inventory.warehouse.FinishGoodBatchStock
import com.bits.bdfp.inventory.warehouse.FinishGoodStock
import com.bits.bdfp.inventory.warehouse.FinishGoodStockTransaction
import com.bits.bdfp.inventory.warehouse.FinishGoodWarehouseDetails
import com.bits.bdfp.inventory.warehouse.FinishGoodWarehouseService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("reverseFinishGoodAction")
class ReverseFinishGoodAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    FinishGoodWarehouseService finishGoodWarehouseService
    @Autowired
    FinishProductService finishProductService
    @Autowired
    SpringSecurityService springSecurityService
    Message message

    public Object preCondition(Object params, Object object) {
        List<FinishGoodStock> finishGoodStockList = new ArrayList<FinishGoodStock>()
        List<FinishGoodWarehouseDetails> detailsUpdateList = new ArrayList<FinishGoodWarehouseDetails>()
        List<FinishGoodStockTransaction> finishGoodStockTransactionList = new ArrayList<FinishGoodStockTransaction>()
        List<FinishGoodStockTransaction> updateFinishGoodStockTransactionList = new ArrayList<FinishGoodStockTransaction>()
        FinishGoodStock finishGoodStock = null
        try {
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
            params.items.each { key, val ->
                if (val instanceof Map) {
                    FinishGoodWarehouseDetails finishGoodWarehouseDetails = finishGoodWarehouseService.finishGoodWarehouseDetailsById(Long.parseLong(val.detailsId))
                    finishGoodStockTransactionList = FinishGoodStockTransaction.findAllByFinishGoodWarehouseDetailsAndOutQuantity(finishGoodWarehouseDetails, 0)
                    finishGoodStockTransactionList.each {  finishGoodStockTransaction->
                        finishGoodStock = finishGoodStockTransaction.finishGoodStock
                        finishGoodStock.inQuantity -= finishGoodStockTransaction.inQuantity
                        finishGoodStock.userUpdated = applicationUser
                        finishGoodStockTransaction.inQuantity = 0
                        finishGoodStockTransaction.unitPrice = 0.00
                        finishGoodStockTransaction.userUpdated = applicationUser
                        finishGoodStockList.add(finishGoodStock)
                        updateFinishGoodStockTransactionList.add(finishGoodStockTransaction)
                    }
                    finishGoodWarehouseDetails.quantity = 0
                    finishGoodWarehouseDetails.cost = 0.00
                    finishGoodWarehouseDetails.confirmCost = 0.00
                    finishGoodWarehouseDetails.userUpdated = applicationUser
                    detailsUpdateList.add(finishGoodWarehouseDetails)
                }
            }
            message = this.getMessage("Reverse Finish Good", Message.SUCCESS, "Success")
            return [message: message, finishGoodStockList: finishGoodStockList, finishGoodStockTransactionList: updateFinishGoodStockTransactionList, detailsUpdateList: detailsUpdateList]
        }
        catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Reverse Finish Good", Message.ERROR, "${ex.message}")
            return [message: message, finishGoodStockList: finishGoodStockList, finishGoodStockTransactionList: updateFinishGoodStockTransactionList, detailsUpdateList: detailsUpdateList]
        }
    }

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser)object
            Map finishGoodWarehouseMap = (Map)this.preCondition(params, applicationUser)
            if (finishGoodWarehouseMap?.message.type == 1) {
                int noOfRow = (int) finishGoodWarehouseService.reverseFinishGood(finishGoodWarehouseMap)
                if (noOfRow > 0) {
                    message = this.getMessage("Finish Good Reverse", Message.SUCCESS, "Reversed Finish Good Entry Successfully")
                } else {
                    message = this.getMessage("Finish Good Reverse", Message.ERROR, "Failed to Reverse Finish Good Entry")
                }
            } else {
                message = finishGoodWarehouseMap?.message
            }

        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Finish Good  Reverse", Message.ERROR, "${ex.message}")
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }

}