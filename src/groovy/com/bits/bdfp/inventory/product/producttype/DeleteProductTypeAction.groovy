package com.bits.bdfp.inventory.product.producttype

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.product.ProductType
import com.bits.bdfp.inventory.product.ProductTypeService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteProductTypeAction")
class DeleteProductTypeAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  ProductTypeService productTypeService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

   public Object preCondition(Object params, Object object) {
       Boolean isError = false
       try{
           Map map = [:]
           ProductType productType = productTypeService.read(Long.parseLong(params.id.toString()))
//           map.put('domain', 'product_type')
//           map.put('id', productType.id)
//           map.put('field', 'product_type')
           String domain = 'product_type'
           String id = productType.id

           isError = validationCheckService.validationCheck(domain,id)

           if (!productType.validate()) {
               message = this.getValidationErrorMessage(productType)
               return message
           }
           else if (isError){
               message = this.getMessage('Product Type', Message.ERROR, 'This Product Type has already been used')
               return message
           }
           else{
               return productType
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
           Object result = this.preCondition(params, null)
           if (result instanceof ProductType) {
               int noOfRows = (int) productTypeService.delete(result)
               if (noOfRows > 0) {
                   message = this.getMessage("Product Type", Message.SUCCESS, this.SUCCESS_DELETE)
               } else {
                   message = this.getMessage("Product Type", Message.ERROR, this.FAIL_DELETE)
               }
           }

           return message;
       } catch (Exception ex) {
           log.error(ex.message)
           message= this.getMessage("Product Type", Message.ERROR, "Exception-${ex.message}")
           return message;
       }

  }

}