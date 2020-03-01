package com.bits.bdfp.inventory.demandorder.demandorderapprovalworkflow

import com.bits.bdfp.inventory.demandorder.DemandOrderApprovalWorkflow
import com.bits.bdfp.inventory.demandorder.DemandOrderApprovalWorkflowService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateDemandOrderApprovalWorkflowAction")
class UpdateDemandOrderApprovalWorkflowAction extends Action {
 private static final Log log = LogFactory.getLog(this)
  @Autowired
  DemandOrderApprovalWorkflowService demandOrderApprovalWorkflowService

   public Object preCondition(Object params, Object object) {
    DemandOrderApprovalWorkflow demandOrderApprovalWorkflow = DemandOrderApprovalWorkflow.read(Long.parseLong(params?.id?.toString()))
    demandOrderApprovalWorkflow.properties=params
    if (!demandOrderApprovalWorkflow.validate()) {
      return null
    }
    return demandOrderApprovalWorkflow
  }

   public Object postCondition(Object params, Object object) {
    //not implement
    return null
  }

   public Object execute(Object params, Object object) {
    try {
      return demandOrderApprovalWorkflowService.update(object)
    } catch (Exception ex) {
      log.error(ex.message)
      return null
    }
  }
}
