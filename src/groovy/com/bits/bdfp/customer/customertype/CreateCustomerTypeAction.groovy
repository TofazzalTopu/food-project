package com.bits.bdfp.customer.customertype

import com.bits.bdfp.customer.CustomerType
import com.bits.bdfp.customer.CustomerTypeService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createCustomerTypeAction")
class CreateCustomerTypeAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  CustomerTypeService customerTypeService

  public Object preCondition(Object user, Object object) {
    try {
      CustomerType customerType = (CustomerType) object
      ApplicationUser applicationUser=(ApplicationUser)user
      customerType.userCreated=applicationUser
      customerType.dateCreated=new Date()
      if (!customerType.validate()) {
        return null
      }
      return customerType
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return customerTypeService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}