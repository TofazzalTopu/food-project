package com.bits.bdfp.inventory.workflow.workflow

import com.bits.bdfp.inventory.product.FinishProduct
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

@Component("updateWorkflowAction")
class UpdateWorkflowAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    WorkflowService workflowService

    Message message

    public Object preCondition(Object params, Object object) {
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

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            Workflow workflow = Workflow.read(Long.parseLong(params.id))
            workflow.properties = params
            workflow.userUpdated = (ApplicationUser) object
            workflow.dateUpdated = new Date()
            List<WorkflowUserMapping> workflowUserMappingList = ObjectUtil.instantiateObjects(params.users, WorkflowUserMapping.class)
            for (int i = 0; i < workflowUserMappingList.size(); i++) {
                workflowUserMappingList[i].workflow = workflow
                if (!workflowUserMappingList[i].id) {
                    workflowUserMappingList[i].isActive = true
                    workflowUserMappingList[i].isActive = true
                    workflowUserMappingList[i].userCreated = (ApplicationUser) object
                    workflowUserMappingList[i].dateCreated = new Date()
                } else {
                    workflowUserMappingList[i].userUpdated = (ApplicationUser) object
                    workflowUserMappingList[i].dateUpdated = new Date()
                }
                if(!workflowUserMappingList[i].validate()) {
                    return this.getValidationErrorMessage(workflowUserMappingList[i])
                }
            }

            List<WorkflowCustomerMapping> workflowCustomerMappingList = ObjectUtil.instantiateObjects(params.customers, WorkflowCustomerMapping.class)
            for(int i = 0; i < workflowCustomerMappingList.size(); i++){
                workflowCustomerMappingList[i].workflow = workflow
                if(!workflowCustomerMappingList[i].id){
                    workflowCustomerMappingList[i].isActive = true
                    workflowCustomerMappingList[i].userCreated = (ApplicationUser) object
                    workflowCustomerMappingList[i].dateCreated = new Date()
                } else {
                    workflowCustomerMappingList[i].userUpdated = (ApplicationUser) object
                    workflowCustomerMappingList[i].dateUpdated = new Date()
                }

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
                Integer integer = workflowService.updateWorkflow(map)
                if (integer == 1) {
                    message = this.getMessage(workflow, Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage(workflow, Message.ERROR, this.FAIL_UPDATE)
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("Workflow", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }
}
