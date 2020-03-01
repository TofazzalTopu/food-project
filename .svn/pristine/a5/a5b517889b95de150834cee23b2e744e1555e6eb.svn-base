package com.bits.bdfp.inventory.demandorder.processanorder

import com.bits.bdfp.history.PrimaryOrderHistory
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderDetails
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.bits.bdfp.inventory.demandorder.ProcessOrderService
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetails
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("createPrimaryDemandOrderDetailsAction")
class CreatePrimaryDemandOrderDetailsAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    ProcessOrderService processOrderService
    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService
    Message message

    public Object preCondition(Object params, Object object) {
        try {
            if(params.secondary){
                SecondaryDemandOrderDetails secondaryDemandOrderDetails = (SecondaryDemandOrderDetails) object
                if (!secondaryDemandOrderDetails.validate()) {
                    message = this.getValidationErrorMessage(secondaryDemandOrderDetails)
                } else {
                    message = this.getMessage(secondaryDemandOrderDetails, Message.SUCCESS, this.SUCCESS_SAVE)
                }
            }else {
                PrimaryDemandOrderDetails primaryDemandOrderDetails = (PrimaryDemandOrderDetails) object
                if (!primaryDemandOrderDetails.validate()) {
                    message = this.getValidationErrorMessage(primaryDemandOrderDetails)
                } else {
                    message = this.getMessage(primaryDemandOrderDetails, Message.SUCCESS, this.SUCCESS_SAVE)
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Demand order", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object execute(Object params, Object object) {
        try {
            if (params.secondary) {
                ApplicationUser applicationUser = (ApplicationUser) object
                SecondaryDemandOrderDetails secondaryDemandOrderDetails = new SecondaryDemandOrderDetails(params)
                secondaryDemandOrderDetails.dateCreated = new Date()
                secondaryDemandOrderDetails.userCreated = applicationUser
                secondaryDemandOrderDetails.amount = Float.parseFloat(params?.quantity?.toString()) * Float.parseFloat(params?.rate?.toString())

                message = this.preCondition(params, secondaryDemandOrderDetails)
                if (message.type == 1) {
                    secondaryDemandOrderDetails = processOrderService.createSecondary(secondaryDemandOrderDetails)
                    if (secondaryDemandOrderDetails) {
                        message = this.getMessage(secondaryDemandOrderDetails, Message.SUCCESS, this.SUCCESS_SAVE)
                    } else {
                        message = this.getMessage(secondaryDemandOrderDetails, Message.ERROR, this.FAIL_SAVE)
                    }
                }
            } else {
                ApplicationUser applicationUser = (ApplicationUser) object
                PrimaryDemandOrderDetails primaryDemandOrderDetails = new PrimaryDemandOrderDetails(params)
                primaryDemandOrderDetails.dateCreated = new Date()
                primaryDemandOrderDetails.userCreated = applicationUser
                primaryDemandOrderDetails.amount = Float.parseFloat(params?.quantity?.toString()) * Float.parseFloat(params?.rate?.toString())

                //Entry Primary Order History -- Start
                PrimaryOrderHistory existHistory = PrimaryOrderHistory.findByPrimaryDemandOrderAndFinishProduct(primaryDemandOrderDetails.primaryDemandOrder, primaryDemandOrderDetails.finishProduct);
                if (existHistory) {
                    existHistory.newQuantity =  Float.parseFloat(params.qty)
                    existHistory.updatedBy = applicationUser
                    primaryDemandOrderService.createPrimaryOrderHistory(existHistory)
                } else {
                    PrimaryOrderHistory primaryOrderHistory = new PrimaryOrderHistory()
                    primaryOrderHistory.primaryDemandOrder = primaryDemandOrderDetails.primaryDemandOrder
                    primaryOrderHistory.finishProduct = primaryDemandOrderDetails.finishProduct
                    primaryOrderHistory.previousQuantity = Float.parseFloat(params.qty)
                    primaryOrderHistory.newQuantity = Float.parseFloat(params.qty)
                    primaryOrderHistory.dateCreated = new Date()
                    primaryOrderHistory.updatedBy = applicationUser
                    primaryDemandOrderService.createPrimaryOrderHistory(primaryOrderHistory)
                }
                //Entry Primary Order History -- End

                message = this.preCondition(params, primaryDemandOrderDetails)
                if (message.type == 1) {
                    primaryDemandOrderDetails = processOrderService.create(primaryDemandOrderDetails)
                    if (primaryDemandOrderDetails) {
                        message = this.getMessage(primaryDemandOrderDetails, Message.SUCCESS, this.SUCCESS_SAVE)
                    } else {
                        message = this.getMessage(primaryDemandOrderDetails, Message.ERROR, this.FAIL_SAVE)
                    }
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Demand Order", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}