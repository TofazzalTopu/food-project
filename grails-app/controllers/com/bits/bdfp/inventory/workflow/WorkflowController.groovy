package com.bits.bdfp.inventory.workflow

import com.bits.bdfp.customer.employee.ListEmployeeByEnterpriseForWorkflowAction
import com.bits.bdfp.inventory.workflow.workflow.CreateWorkflowAction
import com.bits.bdfp.inventory.workflow.workflow.DeleteWorkflowAction
import com.bits.bdfp.inventory.workflow.workflow.DeleteWorkflowCustomerMappingAction
import com.bits.bdfp.inventory.workflow.workflow.ListCustomerForWorkflowAction
import com.bits.bdfp.inventory.workflow.workflow.ListWorkflowAction
import com.bits.bdfp.inventory.workflow.workflow.ListWorkflowMappedCustomerAction
import com.bits.bdfp.inventory.workflow.workflow.ListWorkflowMappedUserAction
import com.bits.bdfp.inventory.workflow.workflow.PrioritySequenceListAction
import com.bits.bdfp.inventory.workflow.workflow.SavePrioritySequenceAction
import com.bits.bdfp.inventory.workflow.workflow.UpdateWorkflowAction
import com.bits.bdfp.inventory.workflow.workflow.ReadWorkflowAction
import com.bits.bdfp.inventory.workflow.workflow.SearchWorkflowAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.accesscontrol.FeatureAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class WorkflowController {

    @Autowired
    private CreateWorkflowAction createWorkflowAction
    @Autowired
    private UpdateWorkflowAction updateWorkflowAction
    @Autowired
    private ListWorkflowAction listWorkflowAction
    @Autowired
    private DeleteWorkflowAction deleteWorkflowAction
    @Autowired
    private ReadWorkflowAction readWorkflowAction
    @Autowired
    private SearchWorkflowAction searchWorkflowAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    PrioritySequenceListAction prioritySequenceListAction
    @Autowired
    SavePrioritySequenceAction savePrioritySequenceAction
    @Autowired
    ListEmployeeByEnterpriseForWorkflowAction listEmployeeByEnterpriseForWorkflowAction
    @Autowired
    ListWorkflowMappedUserAction listWorkflowMappedUserAction
    @Autowired
    ListCustomerForWorkflowAction listCustomerForWorkflowAction
    @Autowired
    DeleteWorkflowCustomerMappingAction deleteWorkflowCustomerMappingAction
    @Autowired
    ListWorkflowMappedCustomerAction listWorkflowMappedCustomerAction
    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listWorkflowAction.execute(params, null)
        render listWorkflowAction.postCondition(null, list) as JSON
    }

    def show = {
        Workflow workflow = new Workflow()
        ApplicationUser applicationUser = session?.applicationUser
        Map result = [:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size() > 0) {
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [workflow: workflow, list: enterpriseList, result: result as JSON])
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createWorkflowAction.execute(params, applicationUser)
        render message as JSON

//        Workflow workflow = new Workflow(params)
//        ApplicationUser applicationUser = session?.applicationUser
//        Workflow workflowInstance = createWorkflowAction.preCondition(applicationUser, workflow)
//        Message message = null
//        if (workflowInstance == null) {
//            message = createWorkflowAction.getValidationErrorMessage(workflow)
//        } else {
//            workflowInstance = createWorkflowAction.execute(null, workflowInstance)
//            if (workflowInstance) {
//                message = createWorkflowAction.getMessage(workflowInstance, Message.SUCCESS, createWorkflowAction.SUCCESS_SAVE)
//            } else {
//                message = createWorkflowAction.getMessage(workflow, Message.ERROR, createWorkflowAction.FAIL_SAVE)
//            }
//        }
//        render message as JSON
    }

    def edit = {
        Map result = readWorkflowAction.execute(params, null)
        render result as JSON
    }

    def update = {

        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateWorkflowAction.execute(params, applicationUser)
        render message as JSON

//        Workflow workflow = new Workflow(params)
//        ApplicationUser applicationUser = session?.applicationUser
//        Object object = updateWorkflowAction.preCondition(params, applicationUser)
//        Message message = null
//        if (object == false) {
//            message = updateWorkflowAction.getValidationErrorMessage(workflow)
//        } else {
//            int noOfRows = (int) updateWorkflowAction.execute(null, object)
//            if (noOfRows > 0) {
//                message = updateWorkflowAction.getMessage(workflow, Message.SUCCESS, updateWorkflowAction.SUCCESS_UPDATE)
//            } else {
//                message = updateWorkflowAction.getMessage(workflow, Message.ERROR, updateWorkflowAction.FAIL_UPDATE)
//            }
//        }
//        render message as JSON
    }

    def delete = {
        Workflow workflow = deleteWorkflowAction.preCondition(params, null);
        Message message = null
        if (workflow) {
            int rowCount = (int) deleteWorkflowAction.execute(null, workflow);
            if (rowCount > 0) {
                message = deleteWorkflowAction.getMessage(workflow, Message.SUCCESS, deleteWorkflowAction.SUCCESS_DELETE);
            } else {
                message = deleteWorkflowAction.getMessage(workflow, Message.ERROR, deleteWorkflowAction.FAIL_DELETE);
            }
        } else {
            message = deleteWorkflowAction.getMessage(workflow, Message.ERROR, deleteWorkflowAction.ALREADY_DELETED);
        }
        render message as JSON;
    }

    def showPrioritySequence = {
        ApplicationUser applicationUser = session?.applicationUser
        Map result = [:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size() > 0) {
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(view: 'showPrioritySequence', model: [list: enterpriseList, result: result as JSON])
    }
    def prioritySequenceList = {
        List list = prioritySequenceListAction.execute(params, null)
        render prioritySequenceListAction.postCondition(null, list) as JSON
    }

    def savePrioritySequence = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = savePrioritySequenceAction.execute(params, applicationUser)
        render message as JSON
    }

    def getMenuUrl = {
        String menuUrl = ""
        FeatureAction featureAction = FeatureAction.read(params?.featureActionId)
        if(featureAction){
            menuUrl = featureAction.menuUrl
        }
        render menuUrl
    }

    def listEmployee = {
        render listEmployeeByEnterpriseForWorkflowAction.execute(params, null) as JSON
    }

    def popupEmployeeListPanel={
        render(view: 'popUpEmployeeList')
    }

    def jsonEmployeeList = {
        Map map = [:]
        List data = (List)listEmployeeByEnterpriseForWorkflowAction.execute(params, null)
        map.put("aaData", data)
        render map as JSON
    }

    def listUserMapping = {
        List listUser = listWorkflowMappedUserAction.execute(params, null)
        render listWorkflowMappedUserAction.postCondition(null, listUser) as JSON
    }

    def listCustomerByEnterprise = {
        render listCustomerForWorkflowAction.execute(params, null) as JSON
    }

    def popupCustomerListPanel={
        render(view: 'popUpCustomerList')
    }

    def jsonCustomerListByEnterprise = {
        Map map = [:]
        List data = (List) listCustomerForWorkflowAction.execute(params, null)
        map.put("aaData", data)
        render map as JSON
    }

    def listCustomerMapping = {
        List listUser = (List) listWorkflowMappedCustomerAction.execute(params, null)
        render listWorkflowMappedCustomerAction.postCondition(null, listUser) as JSON
    }

    def deleteWorkflowCustomer = {
        WorkflowCustomerMapping workflowCustomerMapping = (WorkflowCustomerMapping) deleteWorkflowCustomerMappingAction.preCondition(params, null);
        Message message = null
        if (workflowCustomerMapping) {
            int rowCount = (int) deleteWorkflowCustomerMappingAction.execute(null, workflowCustomerMapping);
            if (rowCount > 0) {
                message = deleteWorkflowCustomerMappingAction.getMessage(workflowCustomerMapping,  Message.SUCCESS, "Workflow Customer Deleted Successfully");
            } else {
                message = deleteWorkflowCustomerMappingAction.getMessage(workflowCustomerMapping,Message.ERROR,deleteWorkflowCustomerMappingAction.FAIL_DELETE);
            }
        } else {
            message = deleteWorkflowCustomerMappingAction.getMessage(workflowCustomerMapping,Message.ERROR,  deleteWorkflowCustomerMappingAction.ALREADY_DELETED);
        }
        render message as JSON;
    }
}
