package com.bits.bdfp.inventory.workflow.workflowusermapping

import com.bits.bdfp.inventory.workflow.WorkflowUserMapping
import com.bits.bdfp.inventory.workflow.WorkflowUserMappingService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateWorkflowUserMappingAction")
class UpdateWorkflowUserMappingAction extends Action {
 private static final Log log = LogFactory.getLog(this)
  @Autowired
  WorkflowUserMappingService workflowUserMappingService

   public Object preCondition(Object params, Object object) {

    ApplicationUser applicationUser = (ApplicationUser) object
    WorkflowUserMapping workflowUserMapping = WorkflowUserMapping.read(Long.parseLong(params?.id?.toString()))
    workflowUserMapping.properties=params
    workflowUserMapping.userUpdated = applicationUser
    workflowUserMapping.dateUpdated = new Date()
    if (!workflowUserMapping.validate()) {
      return null
    }
    return workflowUserMapping
  }

   public Object postCondition(Object params, Object object) {
    //not implement
    return null
  }

   public Object execute(Object params, Object object) {
    try {
      return workflowUserMappingService.update(object)
    } catch (Exception ex) {
      log.error(ex.message)
      return null
    }
  }
}
