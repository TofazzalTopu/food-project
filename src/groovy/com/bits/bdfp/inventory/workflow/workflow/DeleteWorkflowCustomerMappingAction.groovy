package com.bits.bdfp.inventory.workflow.workflow

import com.bits.bdfp.inventory.workflow.WorkflowCustomerMappingService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 12/27/15.
 */
@Component("deleteWorkflowCustomerMappingAction")
class DeleteWorkflowCustomerMappingAction extends Action {

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    WorkflowCustomerMappingService workflowCustomerMappingService

    public Object preCondition(Object params, Object object) {
        try{
            return workflowCustomerMappingService.read(Long.parseLong(params.id.toString()))
        } catch(Exception ex){
            log.error(ex.getMessage());
        }
    }
    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return workflowCustomerMappingService.delete(object)
        }
        catch (Exception ex) {
            log.error(ex.message)
            return new Integer(0)
        }
    }

}