package com.bits.bdfp.inventory.workflow

import com.bits.bdfp.inventory.workflow.workflowusermapping.CreateWorkflowUserMappingAction
import com.bits.bdfp.inventory.workflow.workflowusermapping.DeleteWorkflowUserMappingAction
import com.bits.bdfp.inventory.workflow.workflowusermapping.ListWorkflowUserMappingAction
import com.bits.bdfp.inventory.workflow.workflowusermapping.UpdateWorkflowUserMappingAction
import com.bits.bdfp.inventory.workflow.workflowusermapping.ReadWorkflowUserMappingAction
import com.bits.bdfp.inventory.workflow.workflowusermapping.SearchWorkflowUserMappingAction

import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class WorkflowUserMappingController {

    @Autowired
    private CreateWorkflowUserMappingAction createWorkflowUserMappingAction
    @Autowired
    private UpdateWorkflowUserMappingAction updateWorkflowUserMappingAction
    @Autowired
    private ListWorkflowUserMappingAction listWorkflowUserMappingAction
    @Autowired
    private DeleteWorkflowUserMappingAction deleteWorkflowUserMappingAction
    @Autowired
    private ReadWorkflowUserMappingAction readWorkflowUserMappingAction
    @Autowired
    private SearchWorkflowUserMappingAction searchWorkflowUserMappingAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listWorkflowUserMappingAction.execute(params, null)
        render listWorkflowUserMappingAction.postCondition(null, list) as JSON
    }

    def show = {
        WorkflowUserMapping workflowUserMapping = new WorkflowUserMapping()
        render(template: "show", model: [workflowUserMapping: workflowUserMapping])
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        WorkflowUserMapping workflowUserMapping = new WorkflowUserMapping(params)
        WorkflowUserMapping workflowUserMappingInstance = createWorkflowUserMappingAction.preCondition(applicationUser, workflowUserMapping)
        Message message = null
        if (workflowUserMappingInstance == null) {
            message = createWorkflowUserMappingAction.getValidationErrorMessage(workflowUserMapping)
        } else {
            workflowUserMappingInstance = createWorkflowUserMappingAction.execute(null, workflowUserMappingInstance)
            if (workflowUserMappingInstance) {
                message = createWorkflowUserMappingAction.getMessage(workflowUserMappingInstance,Message.SUCCESS,  createWorkflowUserMappingAction.SUCCESS_SAVE)
            } else {
                message = createWorkflowUserMappingAction.getMessage(workflowUserMapping, Message.ERROR, createWorkflowUserMappingAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        render readWorkflowUserMappingAction.execute(params, null) as JSON
    }

    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        WorkflowUserMapping workflowUserMapping = new WorkflowUserMapping(params)
        Object object = updateWorkflowUserMappingAction.preCondition(params, applicationUser)
        Message message = null
        if (object == false) {
            message = updateWorkflowUserMappingAction.getValidationErrorMessage(workflowUserMapping)
        } else {
            int noOfRows = (int) updateWorkflowUserMappingAction.execute(null, object)
            if (noOfRows > 0) {
                message = updateWorkflowUserMappingAction.getMessage(workflowUserMapping, Message.SUCCESS, updateWorkflowUserMappingAction.SUCCESS_UPDATE)
            } else {
                message = updateWorkflowUserMappingAction.getMessage(workflowUserMapping, Message.ERROR,  updateWorkflowUserMappingAction.FAIL_UPDATE)
            }
        }
        render message as JSON
    }

    def delete = {
        WorkflowUserMapping workflowUserMapping = deleteWorkflowUserMappingAction.preCondition(params, null);
        Message message = null
        if (workflowUserMapping) {
            int rowCount = (int) deleteWorkflowUserMappingAction.execute(null, workflowUserMapping);
            if (rowCount > 0) {
                message = deleteWorkflowUserMappingAction.getMessage(workflowUserMapping,  Message.SUCCESS, "Approval Person Deleted Successfully");
            } else {
                message = deleteWorkflowUserMappingAction.getMessage(workflowUserMapping,Message.ERROR,deleteWorkflowUserMappingAction.FAIL_DELETE);
            }
        } else {
            message = deleteWorkflowUserMappingAction.getMessage(workflowUserMapping,Message.ERROR,  deleteWorkflowUserMappingAction.ALREADY_DELETED);
        }
        render message as JSON;
    }

    def search = {
        WorkflowUserMapping workflowUserMapping = searchWorkflowUserMappingAction.execute(params.fieldName, params.fieldValue)
        if (workflowUserMapping) {
            render workflowUserMapping as JSON
        } else {
            render ''
        }

    }
}
