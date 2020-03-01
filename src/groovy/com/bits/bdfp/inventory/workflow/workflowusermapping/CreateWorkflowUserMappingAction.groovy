package com.bits.bdfp.inventory.workflow.workflowusermapping

import com.bits.bdfp.inventory.workflow.WorkflowUserMapping
import com.bits.bdfp.inventory.workflow.WorkflowUserMappingService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createWorkflowUserMappingAction")
class CreateWorkflowUserMappingAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  WorkflowUserMappingService workflowUserMappingService

  public Object preCondition(Object user, Object object) {
    try {
      ApplicationUser applicationUser = (ApplicationUser) user
      WorkflowUserMapping workflowUserMapping = (WorkflowUserMapping) object
      workflowUserMapping.userCreated = applicationUser
      workflowUserMapping.dateCreated = new Date()
      if (!workflowUserMapping.validate()) {
        return null
      }
      return workflowUserMapping
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return workflowUserMappingService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}