package com.bits.bdfp.inventory.workflow.workflow

import com.bits.bdfp.inventory.workflow.Workflow
import com.bits.bdfp.inventory.workflow.WorkflowCustomerMapping
import com.bits.bdfp.inventory.workflow.WorkflowService
import com.bits.bdfp.inventory.workflow.WorkflowUserMapping
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.ObjectUtil
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

import javax.mail.Session

@Component("createWorkflowAction")
class CreateWorkflowAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    WorkflowService workflowService

    Message message

    public Object preCondition(Object user, Object object) {
        try {
            Workflow workflow = (Workflow) object.get('workflow')
            if (!workflow.validate()) {
                message = this.getValidationErrorMessage(workflow)
            } else {
                message = this.getMessage(workflow, Message.SUCCESS, this.SUCCESS_SAVE)
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Workflow", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object execute(Object params, Object object) {
        try {
            Workflow workflow = new Workflow(params)
            workflow.isActive = true
            workflow.userCreated = (ApplicationUser) object
            workflow.dateCreated = new Date()
            List<WorkflowUserMapping> workflowUserMappingList = ObjectUtil.instantiateObjects(params.users, WorkflowUserMapping.class)
            for(int i = 0; i < workflowUserMappingList.size(); i++){
                workflowUserMappingList[i].workflow = workflow
                workflowUserMappingList[i].isActive = true
                workflowUserMappingList[i].userCreated = (ApplicationUser) object
                workflowUserMappingList[i].dateCreated = new Date()
                if(!workflowUserMappingList[i].validate()) {
                    return this.getValidationErrorMessage(workflowUserMappingList[i])
                }
            }
            List<WorkflowCustomerMapping> workflowCustomerMappingList = ObjectUtil.instantiateObjects(params.customers, WorkflowCustomerMapping.class)
            for(int i = 0; i < workflowCustomerMappingList.size(); i++){
                workflowCustomerMappingList[i].workflow = workflow
                workflowCustomerMappingList[i].isActive = true
                workflowCustomerMappingList[i].userCreated = (ApplicationUser) object
                workflowCustomerMappingList[i].dateCreated = new Date()
                if(!workflowCustomerMappingList[i].validate()) {
                    return this.getValidationErrorMessage(workflowCustomerMappingList[i])
                }
            }
            Map map = new LinkedHashMap()
            map.put('workflow', workflow)
            map.put('workflowUserMapping', workflowUserMappingList)
            map.put('workflowCustomerMapping', workflowCustomerMappingList)

            message = this.preCondition(null, map)
            if (message.type == 1) {
                workflow = workflowService.createWorkflow(map)
                if (workflow) {
                    message = this.getMessage(workflow, Message.SUCCESS, this.SUCCESS_SAVE)
                } else {
                    message = this.getMessage(workflow, Message.ERROR, this.FAIL_SAVE)
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Workflow", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}