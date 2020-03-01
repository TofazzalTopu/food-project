package com.bits.bdfp.customer.customercategory

import com.bits.bdfp.customer.CustomerCategory
import com.bits.bdfp.customer.CustomerCategoryService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createCustomerCategoryAction")
class CreateCustomerCategoryAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  CustomerCategoryService customerCategoryService

  public Object preCondition(Object user, Object object) {
    try {
      ApplicationUser applicationUser=(ApplicationUser)user
      CustomerCategory customerCategory = (CustomerCategory) object
      customerCategory.userCreated=applicationUser
      customerCategory.dateCreated=new Date()
      if (!customerCategory.validate()) {
        return null
      }
      return customerCategory
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return customerCategoryService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}