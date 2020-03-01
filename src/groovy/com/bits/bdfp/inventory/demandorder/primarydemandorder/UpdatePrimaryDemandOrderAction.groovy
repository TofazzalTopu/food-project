package com.bits.bdfp.inventory.demandorder.primarydemandorder

import com.bits.bdfp.history.PrimaryOrderHistory
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderDetails
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updatePrimaryDemandOrderAction")
class UpdatePrimaryDemandOrderAction extends Action {
    private static final Log log = LogFactory.getLog(this)

    private final String MESSAGE_HEADER = 'Update Primary Demand Order'
    private final String MESSAGE_SUCCESS = 'Primary Demand Order Updated Successfully'
    private final String MESSAGE_FAIL = 'Primary Demand Order Not Updated'

    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService
    @Autowired
    SpringSecurityService springSecurityService

    public Object preCondition(Object params, Object object) {
        PrimaryDemandOrder primaryDemandOrder = PrimaryDemandOrder.read(Long.parseLong(params?.id?.toString()))
        primaryDemandOrder.properties = params
        if (!primaryDemandOrder.validate()) {
            return null
        }
        return primaryDemandOrder
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return primaryDemandOrderService.update(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object updatePrimaryDemandOrder(def params, def object) {
        PrimaryDemandOrder primaryDemandOrder = null
        try {
            primaryDemandOrder = primaryDemandOrderService.read(Long.parseLong(params.primaryOrderId))
            primaryDemandOrder.properties = params
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
            primaryDemandOrder.userUpdated = applicationUser

            if (!primaryDemandOrder.validate()) {
                return this.getValidationErrorMessage(primaryDemandOrder)
            }

            List<PrimaryDemandOrderDetails> primaryDemandOrderDetailsList = []
            List<PrimaryOrderHistory> primaryOrderHistoryList = []
            params.items.each { key, val ->
                if (val instanceof Map) {
                    PrimaryOrderHistory existHistory = PrimaryOrderHistory.findByPrimaryDemandOrderAndFinishProduct(primaryDemandOrder, FinishProduct.get(Long.parseLong(val.finishProduct.id)))
                    if (existHistory) {
                        existHistory.newQuantity = Float.parseFloat(val.quantity)
                        existHistory.updatedBy = applicationUser
                        primaryOrderHistoryList.add(existHistory)
                    } else {
                        PrimaryOrderHistory primaryOrderHistory = new PrimaryOrderHistory()
                        primaryOrderHistory.primaryDemandOrder = primaryDemandOrder
                        primaryOrderHistory.finishProduct = FinishProduct.get(Long.parseLong(val.finishProduct.id))
                        primaryOrderHistory.previousQuantity = Float.parseFloat(val.quantity)
                        primaryOrderHistory.newQuantity = Float.parseFloat(val.quantity)
                        primaryOrderHistory.dateCreated = new Date()
                        primaryOrderHistory.updatedBy = applicationUser
                        primaryOrderHistoryList.add(primaryOrderHistory)
                    }
                    if (val.id) {
                        PrimaryDemandOrderDetails primaryDemandOrderDetails1 = PrimaryDemandOrderDetails.read(Long.parseLong(val.id))
                        primaryDemandOrderDetails1.quantity = Float.parseFloat(val.quantity)
                        primaryDemandOrderDetails1.userUpdated = applicationUser
                        primaryDemandOrderDetails1.lastUpdated = new Date();
                        primaryDemandOrderDetails1.amount = Float.parseFloat(val.rate) * Float.parseFloat(val.quantity)
                        primaryDemandOrderDetailsList.add(primaryDemandOrderDetails1)

                    } else {
                        PrimaryDemandOrderDetails primaryDemandOrderDetails = new PrimaryDemandOrderDetails(val)
                        primaryDemandOrderDetails.finishProduct = FinishProduct.get(Long.parseLong(val.finishProduct.id))
                        primaryDemandOrderDetails.userCreated = applicationUser
                        primaryDemandOrderDetails.dateCreated = new Date()
                        primaryDemandOrderDetails.primaryDemandOrder = primaryDemandOrder
                        primaryDemandOrderDetails.rate = Float.parseFloat(val.rate)
                        primaryDemandOrderDetails.quantity = Float.parseFloat(val.quantity)
                        primaryDemandOrderDetails.amount = Float.parseFloat(val.rate) * Float.parseFloat(val.quantity)
                        primaryDemandOrderDetailsList.add(primaryDemandOrderDetails)
                    }
                }
            }

            primaryDemandOrderService.update(primaryDemandOrder)
            primaryDemandOrderDetailsList.each {
                if (it.validate()) {
                    primaryDemandOrderService.updatePrimaryDemandOrderDetails(it)
                } else {
                    return this.getMessage("Primary Demand Order", Message.ERROR, MESSAGE_FAIL)
                }
            }
            primaryOrderHistoryList.each {
                if (it.validate()) {
                    primaryDemandOrderService.createPrimaryOrderHistory(it)
                }
            }
            return this.getMessage(primaryDemandOrder, Message.SUCCESS, MESSAGE_SUCCESS)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Primary Demand Order", Message.ERROR, "${ex.message}")
        }
    }
}
