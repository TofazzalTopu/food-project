package com.bits.bdfp.inventory.demandorder.primarydemandorder

import com.bits.bdfp.history.PrimaryOrderHistory
import com.bits.bdfp.inventory.demandorder.*
import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 9/22/2015.
 */
@Component("updatePrimaryOrderFromSecondaryOrderAction")
class UpdatePrimaryOrderFromSecondaryOrderAction extends Action {

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService

    Message message

    public Object preCondition(Object params, Object object) {
        try {
            PrimaryDemandOrder primaryDemandOrder = (PrimaryDemandOrder) object.get('primaryDemandOrder')
            if (!primaryDemandOrder.validate()) {
                message = this.getValidationErrorMessage(primaryDemandOrder)
            } else {
                message = this.getMessage(primaryDemandOrder, Message.SUCCESS, this.SUCCESS_SAVE)
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("PrimaryDemandOrder", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            PrimaryDemandOrder primaryDemandOrder = PrimaryDemandOrder.read(Long.parseLong(params.id))
            if (primaryDemandOrder.demandOrderStatus == DemandOrderStatus.REJECTED) {
                List<PrimaryDemandOrderApprovalStatus> primaryDemandOrderApprovalStatusList = PrimaryDemandOrderApprovalStatus.findAllByPrimaryDemandOrder(primaryDemandOrder)
                for (int i = 0; i < primaryDemandOrderApprovalStatusList.size(); i++) {
                    primaryDemandOrderApprovalStatusList[i].delete()
                }
            }
            primaryDemandOrder.lastUpdated = new Date()
            primaryDemandOrder.userUpdated = applicationUser
            primaryDemandOrder.properties = params.properties
            primaryDemandOrder.demandOrderStatus = DemandOrderStatus.WAITING_FOR_APPROVAL

            int length = Integer.parseInt(params.count)
            PrimaryDemandOrderDetails[] primaryDemandOrderDetails = new PrimaryDemandOrderDetails[length]
            params.items.each { key, val ->
                for (int i = 0; i < length; i++) {
                    if (key == "details[" + i + "]") {
                        if (val.id == '') {
                            primaryDemandOrderDetails[i] = new PrimaryDemandOrderDetails()
                            primaryDemandOrderDetails[i].primaryDemandOrder = primaryDemandOrder
                            primaryDemandOrderDetails[i].dateCreated = new Date()
                            primaryDemandOrderDetails[i].userCreated = applicationUser
                        } else {
                            primaryDemandOrderDetails[i] = PrimaryDemandOrderDetails.read(Long.parseLong(val.id))
                            primaryDemandOrderDetails[i].lastUpdated = new Date()
                            primaryDemandOrderDetails[i].userUpdated = applicationUser
                        }
                        primaryDemandOrderDetails[i].finishProduct = FinishProduct.read(Long.parseLong(val.productId))
                        primaryDemandOrderDetails[i].amount = Float.parseFloat(val.amount)
                        primaryDemandOrderDetails[i].quantity = Float.parseFloat(val.quantity)
                        primaryDemandOrderDetails[i].rate = Float.parseFloat(val.rate)
                    }
                }
            }

            //Primary Order History Entry -- Start
            List<PrimaryOrderHistory> primaryOrderHistoryList = []
            params.items.each { key, val ->
                if (val instanceof Map) {
                    PrimaryOrderHistory orderHistory = PrimaryOrderHistory.findByPrimaryDemandOrderAndFinishProduct(primaryDemandOrder, FinishProduct.get(Long.parseLong(val.productId)))
                    if (orderHistory) {
                        orderHistory.newQuantity = Float.parseFloat(val.quantity)
                        orderHistory.updatedBy = applicationUser
                        primaryOrderHistoryList.add(orderHistory)
                    } else {
                        PrimaryOrderHistory primaryOrderHistory = new PrimaryOrderHistory()
                        primaryOrderHistory.primaryDemandOrder = primaryDemandOrder
                        primaryOrderHistory.finishProduct = FinishProduct.get(Long.parseLong(val.productId))
                        primaryOrderHistory.previousQuantity = val.quantity ? Float.parseFloat(val.quantity) : 0
                        primaryOrderHistory.newQuantity = val.quantity ? Float.parseFloat(val.quantity) : 0
                        primaryOrderHistory.dateCreated = new Date()
                        primaryOrderHistory.updatedBy = applicationUser
                        primaryOrderHistoryList.add(primaryOrderHistory)
                    }
                }
            }
            //-- End ---

            String[] list = params.deletedIds.split(',')

            Map map = new LinkedHashMap()
            map.put('primaryDemandOrder', primaryDemandOrder)
            map.put('primaryDemandOrderDetails', primaryDemandOrderDetails)
            map.put('deletedIds', list)

            message = this.preCondition(null, map)
            if (message.type == 1) {
                Integer integer = primaryDemandOrderService.updatePrimaryOrder(map)
                if (integer == 1) {
                    primaryOrderHistoryList.each {
                        if (it.validate()) {
                            primaryDemandOrderService.createPrimaryOrderHistory(it)
                        }
                    }
                    message = this.getMessage(primaryDemandOrder, Message.SUCCESS, "Primary Demand Order Updated Successfully")
                } else {
                    message = this.getMessage(primaryDemandOrder, Message.ERROR, this.FAIL_UPDATE)
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Primary Demand Order", Message.ERROR, "Exception-${ex.message}")
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}
