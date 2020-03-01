package com.bits.bdfp.inventory.product.productcategory

import com.bits.bdfp.inventory.product.ProductCategory
import com.bits.bdfp.inventory.product.ProductCategoryService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createProductCategoryAction")
class CreateProductCategoryAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  ProductCategoryService productCategoryService

  public Object preCondition(Object user, Object object) {
    try {
      ProductCategory productCategory = (ProductCategory) object
      ApplicationUser applicationUser=(ApplicationUser)user
      productCategory.userCreated=applicationUser
      productCategory.dateCreated=new Date()
      if (!productCategory.validate()) {
        return null
      }
      return productCategory
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return productCategoryService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}