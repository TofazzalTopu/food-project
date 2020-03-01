package com.bits.bdfp.inventory.product.mainproduct

import com.bits.bdfp.inventory.product.MainProduct
import com.bits.bdfp.inventory.product.MainProductService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createMainProductAction")
class CreateMainProductAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  MainProductService mainProductService

  public Object preCondition(Object user, Object object) {
    try {
      MainProduct mainProduct = (MainProduct) object
      ApplicationUser applicationUser=(ApplicationUser)user
      mainProduct.userCreated = applicationUser
      mainProduct.dateCreated = new Date()

      if (!mainProduct.validate()) {
        return null
      }
      return mainProduct
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return mainProductService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}