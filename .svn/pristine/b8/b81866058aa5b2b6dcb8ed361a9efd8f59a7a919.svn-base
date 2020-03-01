package com.bits.bdfp.common.designation

import com.bits.bdfp.common.Designation
import com.bits.bdfp.common.DesignationService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateDesignationAction")
class UpdateDesignationAction extends Action {
 private static final Log log = LogFactory.getLog(this)
  @Autowired
  DesignationService designationService

   public Object preCondition(Object params, Object object) {
    Designation designation = Designation.read(Long.parseLong(params?.id?.toString()))
    designation.properties=params
    if (!designation.validate()) {
      return null
    }
    return designation
  }

   public Object postCondition(Object params, Object object) {
    //not implement
    return null
  }

   public Object execute(Object params, Object object) {
    try {
      return designationService.update(object)
    } catch (Exception ex) {
      log.error(ex.message)
      return null
    }
  }
}
