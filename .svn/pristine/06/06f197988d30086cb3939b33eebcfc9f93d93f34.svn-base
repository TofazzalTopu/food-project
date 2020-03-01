package com.bits.bdfp.inventory.product.productpricingtype

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.product.ProductPricingType
import com.bits.bdfp.inventory.product.ProductPricingTypeService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteProductPricingTypeAction")
class DeleteProductPricingTypeAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  ProductPricingTypeService productPricingTypeService
    @Autowired
    ValidationCheckService validationCheckService
    Message message
   public Object preCondition(Object params, Object object) {
       Boolean isError = false
       try{
           Map map = [:]
           ProductPricingType productPricingType = productPricingTypeService.read(Long.parseLong(params.id.toString()))

           map.put('domain', 'product_pricing_type')
           map.put('id', productPricingType.id)
           map.put('field', 'product_pricing_type')
           isError = validationCheckService.validationCheck(map)

           if (!productPricingType.validate()) {
               message = this.getValidationErrorMessage(productPricingType)
               return message
           }
           else if (isError){
               message = this.getMessage('ProductPricingType', Message.ERROR, 'This Product Pricing Type has already been used')
               return message
           }
           else{
               return productPricingType
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

   public Message execute(Object params, Object object) {
       try {
           Object result = this.preCondition(params, null)
           if (result instanceof ProductPricingType) {
               int noOfRows = (int) productPricingTypeService.delete(result)
               if (noOfRows > 0) {
                   message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_DELETE)
               } else {
                   message = this.getMessage(result, Message.ERROR, this.FAIL_DELETE)
               }
           }

           return message;
       } catch (Exception ex) {
           log.error(ex.message)
           message= this.getMessage("ProductPricingType", Message.ERROR, "Exception-${ex.message}")
           return message;
       }
  }

}