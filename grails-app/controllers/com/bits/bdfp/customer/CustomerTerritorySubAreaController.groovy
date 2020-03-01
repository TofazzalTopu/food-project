package com.bits.bdfp.customer

import com.bits.bdfp.customer.customerterritorysubarea.CreateCustomerTerritorySubAreaAction
import com.bits.bdfp.customer.customerterritorysubarea.DeleteCustomerTerritorySubAreaAction
import com.bits.bdfp.customer.customerterritorysubarea.ListCustomerTerritorySubAreaAction
import com.bits.bdfp.customer.customerterritorysubarea.UpdateCustomerTerritorySubAreaAction
import com.bits.bdfp.customer.customerterritorysubarea.ReadCustomerTerritorySubAreaAction
import com.bits.bdfp.customer.customerterritorysubarea.SearchCustomerTerritorySubAreaAction

import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class CustomerTerritorySubAreaController {

    @Autowired
    private CreateCustomerTerritorySubAreaAction createCustomerTerritorySubAreaAction
    @Autowired
    private UpdateCustomerTerritorySubAreaAction updateCustomerTerritorySubAreaAction
    @Autowired
    private ListCustomerTerritorySubAreaAction listCustomerTerritorySubAreaAction
    @Autowired
    private DeleteCustomerTerritorySubAreaAction deleteCustomerTerritorySubAreaAction
    @Autowired
    private ReadCustomerTerritorySubAreaAction readCustomerTerritorySubAreaAction
    @Autowired
    private SearchCustomerTerritorySubAreaAction searchCustomerTerritorySubAreaAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listCustomerTerritorySubAreaAction.execute(params, null)
        render listCustomerTerritorySubAreaAction.postCondition(null, list) as JSON
    }

    def show = {
        CustomerTerritorySubArea customerTerritorySubArea = new CustomerTerritorySubArea()
        render(template: "show", model: [customerTerritorySubArea: customerTerritorySubArea])
    }

    def create = {
        CustomerTerritorySubArea customerTerritorySubArea = new CustomerTerritorySubArea(params)
        CustomerTerritorySubArea customerTerritorySubAreaInstance = createCustomerTerritorySubAreaAction.preCondition(null, customerTerritorySubArea)
        Message message = null
        if (customerTerritorySubAreaInstance == null) {
            message = createCustomerTerritorySubAreaAction.getValidationErrorMessageForUI(customerTerritorySubArea)
        } else {
            customerTerritorySubAreaInstance = createCustomerTerritorySubAreaAction.execute(null, customerTerritorySubAreaInstance)
            if (customerTerritorySubAreaInstance) {
                message = createCustomerTerritorySubAreaAction.getSuccessMessageForUI(customerTerritorySubAreaInstance, createCustomerTerritorySubAreaAction.SUCCESS_SAVE)
            } else {
                message = createCustomerTerritorySubAreaAction.getErrorMessageForUI(customerTerritorySubArea, createCustomerTerritorySubAreaAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        render readCustomerTerritorySubAreaAction.execute(params, null) as JSON
    }

    def update = {
        CustomerTerritorySubArea customerTerritorySubArea = new CustomerTerritorySubArea(params)
        Object object = updateCustomerTerritorySubAreaAction.preCondition(params, null)
        Message message = null
        if (object == false) {
            message = updateCustomerTerritorySubAreaAction.getValidationErrorMessageForUI(customerTerritorySubArea)
        } else {
            int noOfRows = (int) updateCustomerTerritorySubAreaAction.execute(null, object)
            if (noOfRows > 0) {
                message = updateCustomerTerritorySubAreaAction.getSuccessMessageForUI(customerTerritorySubArea, updateCustomerTerritorySubAreaAction.SUCCESS_UPDATE)
            } else {
                message = updateCustomerTerritorySubAreaAction.getErrorMessageForUI(customerTerritorySubArea, updateCustomerTerritorySubAreaAction.FAIL_UPDATE)
            }
        }
        render message as JSON
    }

    def delete = {
        CustomerTerritorySubArea customerTerritorySubArea = (CustomerTerritorySubArea) deleteCustomerTerritorySubAreaAction.preCondition(params, null);
//        Message message = null
//        if (customerTerritorySubArea) {
//            int rowCount = (int) deleteCustomerTerritorySubAreaAction.preCondition(null, customerTerritorySubArea);
//            if (rowCount > 0) {
//                message = deleteCustomerTerritorySubAreaAction.getSuccessMessageForUI(customerTerritorySubArea, deleteCustomerTerritorySubAreaAction.SUCCESS_DELETE);
//            } else {
//                message = deleteCustomerTerritorySubAreaAction.getErrorMessageForUI(customerTerritorySubArea, deleteCustomerTerritorySubAreaAction.FAIL_DELETE);
//            }
//        } else {
//            message = deleteCustomerTerritorySubAreaAction.getErrorMessageForUI(customerTerritorySubArea, deleteCustomerTerritorySubAreaAction.ALREADY_DELETED);
//        }
//        render message as JSON;
        Message message = deleteCustomerTerritorySubAreaAction.execute(null, customerTerritorySubArea);
        render message as JSON
    }

    def search = {
        CustomerTerritorySubArea customerTerritorySubArea = searchCustomerTerritorySubAreaAction.execute(params.fieldName, params.fieldValue)
        if (customerTerritorySubArea) {
            render customerTerritorySubArea as JSON
        } else {
            render ''
        }

    }
}
