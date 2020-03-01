package com.bits.bdfp.inventory.setup.discounttype

import com.bits.bdfp.inventory.setup.DiscountType
import com.bits.bdfp.inventory.setup.DiscountTypeService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createDiscountTypeAction")
class CreateDiscountTypeAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  DiscountTypeService discountTypeService

  public Object preCondition(Object user, Object object) {
    try {
      ApplicationUser applicationUser = (ApplicationUser) user

      DiscountType discountType = (DiscountType) object
      discountType.userCreated = applicationUser
      discountType.dateCreated = new Date()
      if (!discountType.validate()) {
        return null
      }
      return discountType
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return discountTypeService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}