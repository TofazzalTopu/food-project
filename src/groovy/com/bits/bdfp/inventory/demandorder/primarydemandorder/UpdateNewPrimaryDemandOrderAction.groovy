package com.bits.bdfp.inventory.demandorder.primarydemandorder

import com.bits.bdfp.customer.CustomerShippingAddress
import com.bits.bdfp.history.PrimaryOrderHistory
import com.bits.bdfp.inventory.demandorder.*
import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.ObjectUtil
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 9/21/15.
 */
@Component("updateNewPrimaryDemandOrderAction")
class UpdateNewPrimaryDemandOrderAction extends Action {
    private static final Log log = LogFactory.getLog(this)

    Message message
    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService
    @Autowired
    ReadPrimaryDemandOrderAction readPrimaryDemandOrderAction


    public Object preCondition(Object params, Object object) {
        try {
            PrimaryDemandOrder primaryDemandOrder = (PrimaryDemandOrder) object
            if (!primaryDemandOrder.validate()) {
                return null
            }
            return primaryDemandOrder
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            PrimaryDemandOrder primaryDemandOrder = (PrimaryDemandOrder) readPrimaryDemandOrderAction.execute(params, null)
            if (primaryDemandOrder.demandOrderStatus == DemandOrderStatus.REJECTED) {
                List<PrimaryDemandOrderApprovalStatus> primaryDemandOrderApprovalStatusList = PrimaryDemandOrderApprovalStatus.findAllByPrimaryDemandOrder(primaryDemandOrder)
                for (int i = 0; i < primaryDemandOrderApprovalStatusList.size(); i++) {
                    primaryDemandOrderApprovalStatusList[i].delete()
                }
            }
            if (primaryDemandOrder.demandOrderStatus == DemandOrderStatus.APPROVED) {
                return this.getMessage(primaryDemandOrder, Message.ERROR, "Your Order is approved. You cannot update it")
            }
            primaryDemandOrder.properties = params
            primaryDemandOrder.lastUpdated = new Date()
            if (!params.customerId) {
                return this.getMessage(primaryDemandOrder, Message.ERROR, "Customer is not selected")
            }
            if (params.ship == 'ship') {
                CustomerShippingAddress shippingAddress = CustomerShippingAddress.get(Long.parseLong(params.shippingAddressId))
                primaryDemandOrder.shippingAddress = shippingAddress
            }else {
                primaryDemandOrder.shippingAddress = null
            }
            primaryDemandOrder.dateExpectedDeliver = primaryDemandOrder.dateProposedDelivery
            primaryDemandOrder.userUpdated = applicationUser
            primaryDemandOrder.demandOrderStatus = DemandOrderStatus.WAITING_FOR_APPROVAL
            if (!primaryDemandOrder.validate()) {
                return this.getValidationErrorMessage(primaryDemandOrder)
            }
            List<PrimaryDemandOrderDetails> primaryDemandOrderDetailList = ObjectUtil.instantiateObjects(params.items, PrimaryDemandOrderDetails.class)
            int orderDetailsCount = primaryDemandOrderDetailList.size()
            if (orderDetailsCount <= 0) {
                return this.getMessage(primaryDemandOrder, Message.ERROR, "No Item Selected")
            }

            for (int i = 0; i < primaryDemandOrderDetailList.size(); i++) {
                if (primaryDemandOrderDetailList[i].id) {
                    primaryDemandOrderDetailList[i].lastUpdated = new Date()
                    primaryDemandOrderDetailList[i].userUpdated = primaryDemandOrder.userUpdated
                } else {
                    primaryDemandOrderDetailList[i].dateCreated = new Date()
                    primaryDemandOrderDetailList[i].userCreated = primaryDemandOrder.userCreated
                    primaryDemandOrderDetailList[i].primaryDemandOrder = primaryDemandOrder
                }

                primaryDemandOrderDetailList[i].amount = primaryDemandOrderDetailList[i].rate * primaryDemandOrderDetailList[i].quantity
                if (!primaryDemandOrderDetailList[i].validate()) {
                    return this.getValidationErrorMessage(primaryDemandOrderDetailList[i])
                }
            }

            //Primary Order History Entry -- Start
            List<PrimaryOrderHistory> primaryOrderHistoryList = []
            params.items.each { key, val ->
                if (val instanceof Map) {
                    PrimaryOrderHistory orderHistory = PrimaryOrderHistory.findByPrimaryDemandOrderAndFinishProduct(primaryDemandOrder, FinishProduct.get(Long.parseLong(val.finishProduct.id)))
                    if (orderHistory) {
                        orderHistory.newQuantity = Float.parseFloat(val.quantity)
                        orderHistory.updatedBy = applicationUser
                        primaryOrderHistoryList.add(orderHistory)
                    } else {
                        PrimaryOrderHistory primaryOrderHistory = new PrimaryOrderHistory()
                        primaryOrderHistory.primaryDemandOrder = primaryDemandOrder
                        primaryOrderHistory.finishProduct = FinishProduct.get(Long.parseLong(val.finishProduct.id))
                        primaryOrderHistory.previousQuantity = val.quantity ? Float.parseFloat(val.quantity) : 0
                        primaryOrderHistory.newQuantity = val.quantity ? Float.parseFloat(val.quantity) : 0
                        primaryOrderHistory.dateCreated = new Date()
                        primaryOrderHistory.updatedBy = applicationUser
                        primaryOrderHistoryList.add(primaryOrderHistory)
                    }
                }
            }
            //-- End ---


            Map mapInstance = [:]
            mapInstance.put(PrimaryDemandOrder.class.simpleName, primaryDemandOrder)
            mapInstance.put(PrimaryDemandOrderDetails.class.simpleName, primaryDemandOrderDetailList)
            primaryDemandOrder = primaryDemandOrderService.updateNew(mapInstance)

            primaryOrderHistoryList.each {
                if (it.validate()) {
                    primaryDemandOrderService.createPrimaryOrderHistory(it)
                }
            }

            if (primaryDemandOrder) {
                message = this.getMessage(primaryDemandOrder, Message.SUCCESS, "New Primary Demand Order Updated Successfully")
            } else {
                message = this.getMessage(primaryDemandOrder, Message.ERROR, this.FAIL_SAVE)
            }
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("New Primary Demand Order", Message.ERROR, "Exception-${ex.message}")
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}