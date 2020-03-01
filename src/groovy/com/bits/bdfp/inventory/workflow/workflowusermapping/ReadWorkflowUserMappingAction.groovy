package com.bits.bdfp.inventory.workflow.workflowusermapping

import com.bits.bdfp.inventory.workflow.WorkflowUserMappingService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readWorkflowUserMappingAction")
class ReadWorkflowUserMappingAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  WorkflowUserMappingService workflowUserMappingService

  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
       return workflowUserMappingService.read(Long.parseLong(params.id))
     } catch (Exception ex) {
     log.error(ex.message)
        return null
    }
  }

  public Object postCondition(Object params, Object object) {
    //Not implement
    return null
  }
}