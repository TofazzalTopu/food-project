package com.bits.bdfp.common.designation

import com.bits.bdfp.common.Designation
import com.bits.bdfp.common.DesignationService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createDesignationAction")
class CreateDesignationAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  DesignationService designationService

  public Object preCondition(Object user, Object object) {
    try {
      ApplicationUser applicationUser=(ApplicationUser)user
      Designation designation = (Designation) object
      designation.userCreated=applicationUser
      designation.dateCreated=new Date()
      if (!designation.validate()) {
        return null
      }
      return designation
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return designationService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}