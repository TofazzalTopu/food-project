package com.bits.bdfp.inventory.product.pricingcategory

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.product.MasterProduct
import com.bits.bdfp.inventory.product.PricingCategory
import com.bits.bdfp.inventory.product.PricingCategoryService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updatePricingCategoryAction")
class UpdatePricingCategoryAction extends Action {
 private static final Log log = LogFactory.getLog(this)
  @Autowired
  PricingCategoryService pricingCategoryService
 @Autowired
 ValidationCheckService validationCheckService
 Message message
   public Object preCondition(Object object, Object params) {
    Boolean isError = false
    try{

    PricingCategory pricingCategory = PricingCategory.read(Long.parseLong(params?.id?.toString()))
    pricingCategory.properties=params
    ApplicationUser applicationUser=(ApplicationUser)object
    pricingCategory.userUpdated=applicationUser

     String domain = 'pricing_category'
     String id =  pricingCategory.id

     isError = validationCheckService.validationCheck(domain,id)


     if (!pricingCategory.validate()) {
      message = this.getValidationErrorMessage(pricingCategory)
      return message
     }
     else if (isError){
      message = this.getMessage('PricingCategory', Message.ERROR, 'This Pricing Category has already been used')
      return message
     }
     else{
      return pricingCategory
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
     ApplicationUser applicationUser = (ApplicationUser) object

     Object result = this.preCondition(applicationUser, params)
     if (result instanceof PricingCategory) {
      int noOfRows = (int) pricingCategoryService.update(result)
      if (noOfRows > 0) {
       message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_UPDATE)
      } else {
       message = this.getMessage(result, Message.ERROR, this.FAIL_UPDATE)
      }
     }

     return message;
    } catch (Exception ex) {
     log.error(ex.message)
     message= this.getMessage("PricingCategory", Message.ERROR, "Exception-${ex.message}")
     return message;
    }

  }
}
