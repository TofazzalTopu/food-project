package com.bits.bdfp.inventory.workflow.workflow

import com.bits.bdfp.customer.CustomerMasterService
import com.bits.bdfp.inventory.workflow.WorkflowService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 12/27/15.
 */
@Component("listCustomerForWorkflowAction")
class ListCustomerForWorkflowAction extends Action{
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    WorkflowService workflowService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return workflowService.listCustomerByEnterprise(params)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }
}
