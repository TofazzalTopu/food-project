package com.bits.bdfp.inventory.demandorder.demandorderapprovalworkflow

import com.bits.bdfp.inventory.demandorder.DemandOrderApprovalWorkflow
import com.bits.bdfp.inventory.demandorder.DemandOrderApprovalWorkflowService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createDemandOrderApprovalWorkflowAction")
class CreateDemandOrderApprovalWorkflowAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  DemandOrderApprovalWorkflowService demandOrderApprovalWorkflowService

  public Object preCondition(Object params, Object object) {
    try {
      DemandOrderApprovalWorkflow demandOrderApprovalWorkflow = (DemandOrderApprovalWorkflow) object
      if (!demandOrderApprovalWorkflow.validate()) {
        return null
      }
      return demandOrderApprovalWorkflow
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return demandOrderApprovalWorkflowService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}