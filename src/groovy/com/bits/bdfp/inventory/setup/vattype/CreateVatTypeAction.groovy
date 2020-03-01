package com.bits.bdfp.inventory.setup.vattype

import com.bits.bdfp.inventory.setup.VatType
import com.bits.bdfp.inventory.setup.VatTypeService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createVatTypeAction")
class CreateVatTypeAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  VatTypeService vatTypeService

  public Object preCondition(Object user, Object object) {
    try {
      ApplicationUser applicationUser = (ApplicationUser) user
      VatType vatType = (VatType) object
      vatType.userCreated = applicationUser
      vatType.dateCreated = new Date()
      if (!vatType.validate()) {
        return null
      }
      return vatType
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return vatTypeService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}