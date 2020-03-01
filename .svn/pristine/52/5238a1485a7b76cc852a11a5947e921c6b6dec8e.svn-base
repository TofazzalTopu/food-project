package com.bits.bdfp.inventory.product.productpackage

import com.bits.bdfp.inventory.product.ProductPackage
import com.bits.bdfp.inventory.product.ProductPackageService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createProductPackageAction")
class CreateProductPackageAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  ProductPackageService productPackageService

  public Object preCondition(Object user, Object object) {
    try {
      ApplicationUser applicationUser = (ApplicationUser) user
      ProductPackage productPackage = (ProductPackage) object
      productPackage.userCreated = applicationUser
      productPackage.dateCreated = new Date()
      if (!productPackage.validate()) {
        return null
      }
      return productPackage
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return productPackageService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}