package com.bits.bdfp.inventory.demandorder.secondarydemandorder

import com.bits.bdfp.inventory.demandorder.DemandOrderStatus
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetails
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import groovy.mock.interceptor.Demand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateSecondaryDemandOrderAction")
class UpdateSecondaryDemandOrderAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    SecondaryDemandOrderService secondaryDemandOrderService
    Message message

    public Object preCondition(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            SecondaryDemandOrder secondaryDemandOrder = secondaryDemandOrderService.read(Long.parseLong(params?.id?.toString()))
            if (secondaryDemandOrder.demandOrderStatus == DemandOrderStatus.UNDER_REVIEW) {
                return this.getMessage(secondaryDemandOrder, Message.ERROR, "This Order is currently Under Review and uneditable. Please try again later.")
            }
            secondaryDemandOrder.properties = params
//            secondaryDemandOrder.userOrderPlaced = applicationUser
//            secondaryDemandOrder.dateOrder = DateUtil.getSimpleDate(params?.orderDate?.toString())
//            secondaryDemandOrder.dateDeliver = DateUtil.getSimpleDate(params?.deliveryDate?.toString())
            secondaryDemandOrder.demandOrderStatus = DemandOrderStatus.UNDER_PROCESS
            secondaryDemandOrder.lastUpdated = new Date()
            secondaryDemandOrder.userUpdated = applicationUser

            if (!secondaryDemandOrder.validate()) {
                return this.getValidationErrorMessage(secondaryDemandOrder)
            } else {
                if(secondaryDemandOrder.dateDeliver < secondaryDemandOrder.dateOrder){
                    return this.getMessage(secondaryDemandOrder, Message.ERROR, "Delivery Date can not less than Order Date.")
                }
                return secondaryDemandOrder
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
            Object result = this.preCondition(params, applicationUser)
            if (result instanceof SecondaryDemandOrder) {
                if (result.dateOrder <= result.dateDeliver) {
                    int noOfRows = (int) secondaryDemandOrderService.update(result)
                    if (noOfRows > 0) {
                        message = this.getMessage(result, Message.SUCCESS, "Secondary Demand Order Updated Successfully")
                    } else {
                        message = this.getMessage(result, Message.ERROR, this.FAIL_UPDATE)
                    }
                } else {
                    message = this.getMessage(result, Message.ERROR, "Delivery Date Can not be less than order date")
                }
            }
            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Secondary DemandOrder Details", Message.ERROR, "{ex.message}")
        }
    }
}
