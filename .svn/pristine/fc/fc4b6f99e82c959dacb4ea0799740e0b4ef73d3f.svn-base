package com.bits.bdfp.inventory.setup.chargetype

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.setup.ChargeType
import com.bits.bdfp.inventory.setup.ChargeTypeService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteChargeTypeAction")
class DeleteChargeTypeAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  ChargeTypeService chargeTypeService
    @Autowired
    ValidationCheckService validationCheckService
    Message message
   public Object preCondition(Object params, Object object) {
       Boolean isError = false
       try{

           ChargeType chargeType = chargeTypeService.read(Long.parseLong(params.id.toString()))
           String domain = 'charge_type'
           String id =  chargeType.id

           isError = validationCheckService.validationCheck(domain,id)

           if (!chargeType.validate()) {
               message = this.getValidationErrorMessage(chargeType)
               return message
           }
           else if (isError){
               message = this.getMessage('ChargeType', Message.ERROR, 'This Charge Type  has already been used')
               return message
           }
           else{
               return chargeType
           }


       }catch (Exception ex) {
           log.error(ex.message)
           throw ex
       }
  }
   public Object postCondition(Object params, Object object) {
    //not implement
    return null
  }

   public Object execute(Object params, Object object) {

       try {

           Object result = this.preCondition(params,null)
           if (result instanceof ChargeType) {
               int noOfRows = (int) chargeTypeService.delete(result)
               if (noOfRows > 0) {
                   message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_DELETE)
               } else {
                   message = this.getMessage(result, Message.ERROR, this.FAIL_DELETE)
               }
           }

           return message;
       } catch (Exception ex) {
           log.error(ex.message)
           message= this.getMessage("ChargeType", Message.ERROR, "Exception-${ex.message}")
           return message;
       }

  }

}