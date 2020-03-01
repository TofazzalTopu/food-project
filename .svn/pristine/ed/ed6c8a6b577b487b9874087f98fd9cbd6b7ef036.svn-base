package com.bits.bdfp.inventory.product.productpricingtype

import com.bits.bdfp.inventory.product.ProductPricingType
import com.bits.bdfp.inventory.product.ProductPricingTypeService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createProductPricingTypeAction")
class CreateProductPricingTypeAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  ProductPricingTypeService productPricingTypeService

  public Object preCondition(Object user, Object object) {
    try {
      ProductPricingType productPricingType = (ProductPricingType) object
      ApplicationUser applicationUser=(ApplicationUser)user
      productPricingType.userCreated=applicationUser
      productPricingType.dateCreated=new  Date()
      if (!productPricingType.validate()) {
        return null
      }
      return productPricingType
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return productPricingTypeService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}