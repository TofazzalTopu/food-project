package com.bits.bdfp.inventory.demandorder.secondarydemandorderdetails

import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetails
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetailsService
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createSecondaryDemandOrderDetailsAction")
class CreateSecondaryDemandOrderDetailsAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  SecondaryDemandOrderDetailsService secondaryDemandOrderDetailsService
  @Autowired
  SecondaryDemandOrderService secondaryDemandOrderService
  Message message

  protected Message preCondition(Object object, Object params) {
    try {
      SecondaryDemandOrderDetails secondaryDemandOrderDetails  = (SecondaryDemandOrderDetails) object
      if (!secondaryDemandOrderDetails.validate()) {
        message = this.getValidationErrorMessage(secondaryDemandOrderDetails)
      } else {
        message = this.getMessage(secondaryDemandOrderDetails, Message.SUCCESS, this.SUCCESS_SAVE)
      }
      return message
    } catch (Exception ex) {
      log.error(ex.message)
      message = this.getMessage("SecondaryDemandOrderDetails", Message.ERROR, "Exception-${ex.message}")
      return message
    }
  }

//
  public Message execute(Object params, Object object) {
    try {
      ApplicationUser applicationUser = (ApplicationUser) object
      SecondaryDemandOrder secondaryDemandOrder= secondaryDemandOrderService.read(Long.parseLong(params?.id?.toString()))
      SecondaryDemandOrderDetails secondaryDemandOrderDetails = new SecondaryDemandOrderDetails(params)
      secondaryDemandOrderDetails.secondaryDemandOrder=secondaryDemandOrder
        secondaryDemandOrderDetails.rate=Float.parseFloat(params?.rate?:'0')
        secondaryDemandOrderDetails.quantity=Float.parseFloat(params?.qty?:'0')
        secondaryDemandOrderDetails.amount=secondaryDemandOrderDetails.quantity*secondaryDemandOrderDetails.rate

      secondaryDemandOrderDetails.dateCreated = new Date()
      secondaryDemandOrderDetails.userCreated = applicationUser

      message = this.preCondition(secondaryDemandOrderDetails, null)
      if (message.type == 1) {
        secondaryDemandOrderDetails = secondaryDemandOrderDetailsService.create(secondaryDemandOrderDetails)
        if (secondaryDemandOrderDetails) {
          message = this.getMessage(secondaryDemandOrderDetails, Message.SUCCESS, this.SUCCESS_SAVE)
        } else {
          message = this.getMessage(secondaryDemandOrderDetails, Message.ERROR, this.FAIL_SAVE)
        }
      }

      return message;
    } catch (Exception ex) {
      log.error(ex.message)
      throw ex
    }
  }
  public Object postCondition(Object params, Object object) {
    return null
  }
}