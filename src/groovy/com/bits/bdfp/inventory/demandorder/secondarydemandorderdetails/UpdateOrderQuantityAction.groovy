package com.bits.bdfp.inventory.demandorder.secondarydemandorderdetails

import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetails
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetailsService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by prianka.adhikary on 9/23/2015.
 */
@Component("updateOrderQuantityAction")
class UpdateOrderQuantityAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    SecondaryDemandOrderDetailsService secondaryDemandOrderDetailsService
    Message message
    public Object preCondition(Object object, Object params) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            SecondaryDemandOrderDetails secondaryDemandOrderDetails = secondaryDemandOrderDetailsService.read(Long.parseLong(params?.id?.toString()))

            secondaryDemandOrderDetails.quantity=Float.parseFloat(params?.qty?:'0')
            secondaryDemandOrderDetails.amount = secondaryDemandOrderDetails.rate * secondaryDemandOrderDetails.quantity
            secondaryDemandOrderDetails.lastUpdated = new Date()
            secondaryDemandOrderDetails.userUpdated = applicationUser

            if (!secondaryDemandOrderDetails.validate()) {
                message = this.getValidationErrorMessage(secondaryDemandOrderDetails)
                return message
            }
            else{
                return secondaryDemandOrderDetails
            }
        } catch (Exception ex) {
            log.error(ex.message)
            throw ex
        }
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Message execute(Object params, Object object) {

        try {

            ApplicationUser applicationUser = (ApplicationUser) object

            Object result = this.preCondition(applicationUser, params)
            if (result instanceof SecondaryDemandOrderDetails) {
                int noOfRows = (int) secondaryDemandOrderDetailsService.update(result)
                if (noOfRows > 0) {
                    message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage(result, Message.ERROR, this.FAIL_UPDATE)
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            message= this.getMessage("SecondaryDemandOrderDetails", Message.ERROR, "Exception-${ex.message}")
            return message;
        }
    }
}
