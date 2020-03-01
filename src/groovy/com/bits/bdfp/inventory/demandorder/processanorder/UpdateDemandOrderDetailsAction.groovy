package com.bits.bdfp.inventory.demandorder.processanorder

import com.bits.bdfp.history.PrimaryOrderHistory
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderDetails
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.bits.bdfp.inventory.demandorder.ProcessOrderService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("updateDemandOrderDetailsAction")
class UpdateDemandOrderDetailsAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    ProcessOrderService processOrderService
    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService
    Message message

    public Object preCondition(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            PrimaryDemandOrderDetails primaryDemandOrderDetails = processOrderService.readPrimaryDemandOrderDetails(Long.parseLong(params?.id?.toString()))
            Float qty=Float.parseFloat(params.qty)
            primaryDemandOrderDetails.quantity = Float.parseFloat(params.qty)
            primaryDemandOrderDetails.amount = (qty*primaryDemandOrderDetails.rate)
            primaryDemandOrderDetails.userUpdated = applicationUser
            primaryDemandOrderDetails.lastUpdated=new Date()

            if (!primaryDemandOrderDetails.validate()) {
                message = this.getValidationErrorMessage(primaryDemandOrderDetails)
                return message
            }
            else{
                return primaryDemandOrderDetails
            }
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Update demand order details", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {

            ApplicationUser applicationUser = (ApplicationUser) object

            Object result = this.preCondition(params, applicationUser)

          /*  //Entry Primary Order History -- Start
            PrimaryOrderHistory existHistory = PrimaryOrderHistory.findByPrimaryDemandOrderAndFinishProduct(result.primaryDemandOrder, result.finishProduct)
            if (existHistory) {
                existHistory.newQuantity =  Float.parseFloat(params.qty)
                existHistory.updatedBy = applicationUser
                primaryDemandOrderService.createPrimaryOrderHistory(existHistory)
            } else {
                PrimaryOrderHistory primaryOrderHistory = new PrimaryOrderHistory()
                primaryOrderHistory.primaryDemandOrder = result.primaryDemandOrder
                primaryOrderHistory.finishProduct = result.finishProduct
                primaryOrderHistory.previousQuantity = Float.parseFloat(params.previousQty)
                primaryOrderHistory.newQuantity = Float.parseFloat(params.qty)
                primaryOrderHistory.dateCreated = new Date()
                primaryOrderHistory.updatedBy = applicationUser
                primaryDemandOrderService.createPrimaryOrderHistory(primaryOrderHistory)
            }
            //Entry Primary Order History -- End
            */
            if (result instanceof PrimaryDemandOrderDetails) {
                int noOfRows = (int) processOrderService.updatePrimaryDemandOrderDetails(result)
                if (noOfRows > 0) {
                    message = this.getMessage(result, Message.SUCCESS, "Demand Order Updated Successfully")
                } else {
                    message = this.getMessage(result, Message.ERROR, "Demand Order Update Failed")
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
