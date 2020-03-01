package com.bits.bdfp.inventory.demandorder.processanorder

import com.bits.bdfp.inventory.demandorder.ProcessOrderService
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetails
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("scondaryDemandOrderUpdateDetailsAction")
class ScondaryDemandOrderUpdateDetailsAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    ProcessOrderService processOrderService
    Message message

    public Object preCondition(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            SecondaryDemandOrderDetails secondaryDemandOrderDetails = processOrderService.secondaryDemandOrderDetails(Long.parseLong(params?.id?.toString()))
            Float qty=Float.parseFloat(params.qty)
            secondaryDemandOrderDetails.quantity = Float.parseFloat(params.qty)
            secondaryDemandOrderDetails.amount = (qty*secondaryDemandOrderDetails.rate)
            secondaryDemandOrderDetails.userUpdated = applicationUser
            secondaryDemandOrderDetails.lastUpdated=new Date()

            if (!secondaryDemandOrderDetails.validate()) {
                message = this.getValidationErrorMessage(secondaryDemandOrderDetails)
                return message
            }
            else{
                return secondaryDemandOrderDetails
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
            if (result instanceof SecondaryDemandOrderDetails) {
                int noOfRows = (int) processOrderService.updateSecondaryDemandOrderDetails(result)
                if (noOfRows > 0) {
                    message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage(result, Message.ERROR, this.FAIL_UPDATE)
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            message= this.getMessage("Update demand order", Message.ERROR, "Exception-${ex.message}")
            return message;
        }
    }
}
