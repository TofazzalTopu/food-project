package com.bits.bdfp.inventory.product.mainproduct

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.product.MainProduct
import com.bits.bdfp.inventory.product.MainProductService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteMainProductAction")
class DeleteMainProductAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  MainProductService mainProductService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

   public Object preCondition(Object params, Object object) {
       Boolean isError = false
       try{
           MainProduct mainProduct = mainProductService.read(Long.parseLong(params.id.toString()))
           String domain = 'main_product'
           String id =  mainProduct.id

           isError = validationCheckService.validationCheck(domain,id)
           if (!mainProduct.validate()) {
               message = this.getValidationErrorMessage(mainProduct)
               return message
           }
           else if (isError){
               message = this.getMessage('MainProduct', Message.ERROR, 'This Main Product has already been used')
               return message
           }
           else{
               return mainProduct
           }
       }
       catch (Exception ex) {
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

           Object result = this.preCondition( params,null)
           if (result instanceof MainProduct) {
               int noOfRows = (int) mainProductService.delete(result)
               if (noOfRows > 0) {
                   message = this.getMessage(result, Message.SUCCESS, "Main Product deleted successfully")
               } else {
                   message = this.getMessage(result, Message.ERROR, "This Main Product is already used")
               }
           }

           return message;
       } catch (Exception ex) {
           log.error(ex.message)
           message= this.getMessage("MainProduct", Message.ERROR, "Exception-${ex.message}")
           return message;
       }
  }

}