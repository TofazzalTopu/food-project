package com.bits.bdfp.inventory.setup.discounttype

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.setup.DiscountType
import com.bits.bdfp.inventory.setup.DiscountTypeService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteDiscountTypeAction")
class DeleteDiscountTypeAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  DiscountTypeService discountTypeService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

   public Object preCondition(Object params, Object object) {
       Boolean isError = false
       try{

           DiscountType discountType = discountTypeService.read(Long.parseLong(params.id.toString()))
           String domain = 'discount_type'
           String id =  discountType.id

           isError = validationCheckService.validationCheck(domain,id)

           if (!discountType.validate()) {
               message = this.getValidationErrorMessage(discountType)
               return message
           }
           else if (isError){
               message = this.getMessage('Discount Type', Message.ERROR, 'This Discount Type  has already been used')
               return message
           }
           else{
               return discountType
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
           if (result instanceof DiscountType) {
               int noOfRows = (int) discountTypeService.delete(result)
               if (noOfRows > 0) {
                   message = this.getMessage("Discount Type", Message.SUCCESS, this.SUCCESS_DELETE)
               } else {
                   message = this.getMessage("Discount Type", Message.ERROR, this.FAIL_DELETE)
               }
           }

           return message;
       } catch (Exception ex) {
           log.error(ex.message)
           message= this.getMessage("Discount Type", Message.ERROR, "Exception-${ex.message}")
           return message;
       }
  }

}