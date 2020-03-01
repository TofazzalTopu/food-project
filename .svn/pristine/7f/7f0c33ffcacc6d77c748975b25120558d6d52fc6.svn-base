package com.bits.bdfp.inventory.retailorder.retailorder

import com.bits.bdfp.inventory.retailorder.RetailOrderDetails
import com.docu.commons.ObjectUtil
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.inventory.retailorder.RetailOrder
import com.bits.bdfp.inventory.retailorder.RetailOrderService
import com.docu.common.Message

@Component("updateRetailOrderAction")
class UpdateRetailOrderAction extends Action {
    public static final Log log = LogFactory.getLog(UpdateRetailOrderAction.class)
    private final String MESSAGE_HEADER = 'Update Retail Order'
    private final String MESSAGE_SUCCESS = 'Retail Order Update Successfully'

    @Autowired
    RetailOrderService retailOrderService
    @Autowired
    SpringSecurityService springSecurityService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        RetailOrder retailOrder = null
        try {
            retailOrder = retailOrderService.read(Long.parseLong(params.id))
            retailOrder.properties = params
            retailOrder.userUpdated = (ApplicationUser) springSecurityService.getCurrentUser()
            if(params.isSubmitted) {
                retailOrder.isOrderSubmitted = true
            }else{
                retailOrder.isOrderSubmitted = false
            }
            if (!retailOrder.validate()) {
                return this.getValidationErrorMessage(retailOrder)
            }
            List<RetailOrderDetails> retailOrderDetailsList = ObjectUtil.instantiateObjects(params.items, RetailOrderDetails.class)
            int orderDetailsCount = retailOrderDetailsList.size()
            if(orderDetailsCount <= 0){
                return this.getMessage(retailOrder, Message.ERROR, "No Items are selected")
            }
            retailOrderDetailsList.each {  retailOrderDetails ->
                if(retailOrderDetails.id){
                    retailOrderDetails.userUpdated = retailOrder.userUpdated
                }else {
                    retailOrderDetails.userCreated = retailOrder.userUpdated
                    retailOrderDetails.retailOrder = retailOrder
                }
                retailOrderDetails.amount = retailOrderDetails.rate * retailOrderDetails.quantity
                if(!retailOrderDetails.validate()){
                    throw new RuntimeException(this.getValidationErrorMessage(retailOrderDetails).messageBody[0].toString())
                }
            }
            retailOrderService.update(retailOrder)
            retailOrderDetailsList.each {
                retailOrderService.updateOrderDetails(it)
            }
            return this.getMessage(retailOrder, Message.SUCCESS, MESSAGE_SUCCESS)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Retail Order", Message.ERROR, "${ex.message}")
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}
