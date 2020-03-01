package com.bits.bdfp.inventory.setup.poscustomer

import com.bits.bdfp.inventory.setup.PosCustomer
import com.bits.bdfp.inventory.setup.PosCustomerService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createPosCustomerAction")
class CreatePosCustomerAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  PosCustomerService posCustomerService

  public Object preCondition(Object user, Object object) {
    try {
      ApplicationUser applicationUser = (ApplicationUser) user
      PosCustomer posCustomer = (PosCustomer) object
      posCustomer.userCreated = applicationUser
      posCustomer.dateCreated = new Date()
      if (!posCustomer.validate()) {
        return null
      }
      return posCustomer
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return posCustomerService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}