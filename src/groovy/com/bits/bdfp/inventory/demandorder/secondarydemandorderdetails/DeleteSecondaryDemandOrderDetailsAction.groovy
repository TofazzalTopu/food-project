package com.bits.bdfp.inventory.demandorder.secondarydemandorderdetails

import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetails
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetailsService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteSecondaryDemandOrderDetailsAction")
class DeleteSecondaryDemandOrderDetailsAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  SecondaryDemandOrderDetailsService secondaryDemandOrderDetailsService
  Message message

    public Object preCondition(Object params, Object object) {
        try {
            SecondaryDemandOrderDetails secondaryDemandOrderDetails = secondaryDemandOrderDetailsService.read(Long.parseLong(params?.detailsOrder?.toString()))
            if (!secondaryDemandOrderDetails.validate()) {
                message = this.getValidationErrorMessage(secondaryDemandOrderDetails)
                return message
            } else {
                return secondaryDemandOrderDetails;
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
            Object result = this.preCondition(params, object)
            if (result instanceof SecondaryDemandOrderDetails) {
                int noOfRows = (int) secondaryDemandOrderDetailsService.delete(result)
                if (noOfRows > 0) {
                    message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_DELETE)
                } else {
                    message = this.getMessage(result, Message.ERROR, this.FAIL_DELETE)
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