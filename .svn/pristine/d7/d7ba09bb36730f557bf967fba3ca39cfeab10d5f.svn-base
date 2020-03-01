package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.inventory.demandorder.demandorderapprovalworkflow.CreateDemandOrderApprovalWorkflowAction
import com.bits.bdfp.inventory.demandorder.demandorderapprovalworkflow.DeleteDemandOrderApprovalWorkflowAction
import com.bits.bdfp.inventory.demandorder.demandorderapprovalworkflow.ListDemandOrderApprovalWorkflowAction
import com.bits.bdfp.inventory.demandorder.demandorderapprovalworkflow.UpdateDemandOrderApprovalWorkflowAction
import com.bits.bdfp.inventory.demandorder.demandorderapprovalworkflow.ReadDemandOrderApprovalWorkflowAction
import com.bits.bdfp.inventory.demandorder.demandorderapprovalworkflow.SearchDemandOrderApprovalWorkflowAction

import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class DemandOrderApprovalWorkflowController {

    @Autowired
    private CreateDemandOrderApprovalWorkflowAction createDemandOrderApprovalWorkflowAction
    @Autowired
    private UpdateDemandOrderApprovalWorkflowAction updateDemandOrderApprovalWorkflowAction
    @Autowired
    private ListDemandOrderApprovalWorkflowAction listDemandOrderApprovalWorkflowAction
    @Autowired
    private DeleteDemandOrderApprovalWorkflowAction deleteDemandOrderApprovalWorkflowAction
    @Autowired
    private ReadDemandOrderApprovalWorkflowAction readDemandOrderApprovalWorkflowAction
    @Autowired
    private SearchDemandOrderApprovalWorkflowAction searchDemandOrderApprovalWorkflowAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listDemandOrderApprovalWorkflowAction.execute(params, null)
        render listDemandOrderApprovalWorkflowAction.postCondition(null, list) as JSON
    }

    def show = {
        DemandOrderApprovalWorkflow demandOrderApprovalWorkflow = new DemandOrderApprovalWorkflow()
        render(template: "show", model: [demandOrderApprovalWorkflow: demandOrderApprovalWorkflow])
    }

    def create = {
        DemandOrderApprovalWorkflow demandOrderApprovalWorkflow = new DemandOrderApprovalWorkflow(params)
        DemandOrderApprovalWorkflow demandOrderApprovalWorkflowInstance = createDemandOrderApprovalWorkflowAction.preCondition(null, demandOrderApprovalWorkflow)
        Message message = null
        if (demandOrderApprovalWorkflowInstance == null) {
            message = createDemandOrderApprovalWorkflowAction.getValidationErrorMessageForUI(demandOrderApprovalWorkflow)
        } else {
            demandOrderApprovalWorkflowInstance = createDemandOrderApprovalWorkflowAction.execute(null, demandOrderApprovalWorkflowInstance)
            if (demandOrderApprovalWorkflowInstance) {
                message = createDemandOrderApprovalWorkflowAction.getSuccessMessageForUI(demandOrderApprovalWorkflowInstance, createDemandOrderApprovalWorkflowAction.SUCCESS_SAVE)
            } else {
                message = createDemandOrderApprovalWorkflowAction.getErrorMessageForUI(demandOrderApprovalWorkflow, createDemandOrderApprovalWorkflowAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        render readDemandOrderApprovalWorkflowAction.execute(params, null) as JSON
    }

    def update = {
        DemandOrderApprovalWorkflow demandOrderApprovalWorkflow = new DemandOrderApprovalWorkflow(params)
        Object object = updateDemandOrderApprovalWorkflowAction.preCondition(params, null)
        Message message = null
        if (object == false) {
            message = updateDemandOrderApprovalWorkflowAction.getValidationErrorMessageForUI(demandOrderApprovalWorkflow)
        } else {
            int noOfRows = (int) updateDemandOrderApprovalWorkflowAction.execute(null, object)
            if (noOfRows > 0) {
                message = updateDemandOrderApprovalWorkflowAction.getSuccessMessageForUI(demandOrderApprovalWorkflow, updateDemandOrderApprovalWorkflowAction.SUCCESS_UPDATE)
            } else {
                message = updateDemandOrderApprovalWorkflowAction.getErrorMessageForUI(demandOrderApprovalWorkflow, updateDemandOrderApprovalWorkflowAction.FAIL_UPDATE)
            }
        }
        render message as JSON
    }

    def delete = {
        DemandOrderApprovalWorkflow demandOrderApprovalWorkflow = deleteDemandOrderApprovalWorkflowAction.execute(params, null);
        Message message = null
        if (demandOrderApprovalWorkflow) {
            int rowCount = (int) deleteDemandOrderApprovalWorkflowAction.preCondition(null, demandOrderApprovalWorkflow);
            if (rowCount > 0) {
                message = deleteDemandOrderApprovalWorkflowAction.getSuccessMessageForUI(demandOrderApprovalWorkflow, deleteDemandOrderApprovalWorkflowAction.SUCCESS_DELETE);
            } else {
                message = deleteDemandOrderApprovalWorkflowAction.getErrorMessageForUI(demandOrderApprovalWorkflow, deleteDemandOrderApprovalWorkflowAction.FAIL_DELETE);
            }
        } else {
            message = deleteDemandOrderApprovalWorkflowAction.getErrorMessageForUI(demandOrderApprovalWorkflow, deleteDemandOrderApprovalWorkflowAction.ALREADY_DELETED);
        }
        render message as JSON;
    }

    def search = {
        DemandOrderApprovalWorkflow demandOrderApprovalWorkflow = searchDemandOrderApprovalWorkflowAction.execute(params.fieldName, params.fieldValue)
        if (demandOrderApprovalWorkflow) {
            render demandOrderApprovalWorkflow as JSON
        } else {
            render ''
        }

    }
}
