package com.bits.bdfp.customer

import com.bits.bdfp.customer.customershippingaddress.CreateCustomerShippingAddressAction
import com.bits.bdfp.customer.customershippingaddress.DeleteCustomerShippingAddressAction
import com.bits.bdfp.customer.customershippingaddress.ListCustomerShippingAddressAction
import com.bits.bdfp.customer.customershippingaddress.UpdateCustomerShippingAddressAction
import com.bits.bdfp.customer.customershippingaddress.ReadCustomerShippingAddressAction
import com.bits.bdfp.customer.customershippingaddress.SearchCustomerShippingAddressAction

import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class CustomerShippingAddressController {

    @Autowired
    private CreateCustomerShippingAddressAction createCustomerShippingAddressAction
    @Autowired
    private UpdateCustomerShippingAddressAction updateCustomerShippingAddressAction
    @Autowired
    private ListCustomerShippingAddressAction listCustomerShippingAddressAction
    @Autowired
    private DeleteCustomerShippingAddressAction deleteCustomerShippingAddressAction
    @Autowired
    private ReadCustomerShippingAddressAction readCustomerShippingAddressAction
    @Autowired
    private SearchCustomerShippingAddressAction searchCustomerShippingAddressAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listCustomerShippingAddressAction.execute(params, null)
        render listCustomerShippingAddressAction.postCondition(null, list) as JSON
    }

    def show = {
        CustomerShippingAddress customerShippingAddress = new CustomerShippingAddress()
        render(template: "show", model: [customerShippingAddress: customerShippingAddress])
    }

    def create = {
        CustomerShippingAddress customerShippingAddress = new CustomerShippingAddress(params)
        CustomerShippingAddress customerShippingAddressInstance = createCustomerShippingAddressAction.preCondition(null, customerShippingAddress)
        Message message = null
        if (customerShippingAddressInstance == null) {
            message = createCustomerShippingAddressAction.getValidationErrorMessageForUI(customerShippingAddress)
        } else {
            customerShippingAddressInstance = createCustomerShippingAddressAction.execute(null, customerShippingAddressInstance)
            if (customerShippingAddressInstance) {
                message = createCustomerShippingAddressAction.getSuccessMessageForUI(customerShippingAddressInstance, createCustomerShippingAddressAction.SUCCESS_SAVE)
            } else {
                message = createCustomerShippingAddressAction.getErrorMessageForUI(customerShippingAddress, createCustomerShippingAddressAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        render readCustomerShippingAddressAction.execute(params, null) as JSON
    }

    def update = {
        CustomerShippingAddress customerShippingAddress = new CustomerShippingAddress(params)
        Object object = updateCustomerShippingAddressAction.preCondition(params, null)
        Message message = null
        if (object == false) {
            message = updateCustomerShippingAddressAction.getValidationErrorMessageForUI(customerShippingAddress)
        } else {
            int noOfRows = (int) updateCustomerShippingAddressAction.execute(null, object)
            if (noOfRows > 0) {
                message = updateCustomerShippingAddressAction.getSuccessMessageForUI(customerShippingAddress, updateCustomerShippingAddressAction.SUCCESS_UPDATE)
            } else {
                message = updateCustomerShippingAddressAction.getErrorMessageForUI(customerShippingAddress, updateCustomerShippingAddressAction.FAIL_UPDATE)
            }
        }
        render message as JSON
    }

    def delete = {
        CustomerShippingAddress customerShippingAddress = deleteCustomerShippingAddressAction.execute(params, null);
        Message message = null
        if (customerShippingAddress) {
            int rowCount = (int) deleteCustomerShippingAddressAction.preCondition(null, customerShippingAddress);
            if (rowCount > 0) {
                message = deleteCustomerShippingAddressAction.getSuccessMessageForUI(customerShippingAddress, deleteCustomerShippingAddressAction.SUCCESS_DELETE);
            } else {
                message = deleteCustomerShippingAddressAction.getErrorMessageForUI(customerShippingAddress, deleteCustomerShippingAddressAction.FAIL_DELETE);
            }
        } else {
            message = deleteCustomerShippingAddressAction.getErrorMessageForUI(customerShippingAddress, deleteCustomerShippingAddressAction.ALREADY_DELETED);
        }
        render message as JSON;
    }

    def search = {
        CustomerShippingAddress customerShippingAddress = searchCustomerShippingAddressAction.execute(params.fieldName, params.fieldValue)
        if (customerShippingAddress) {
            render customerShippingAddress as JSON
        } else {
            render ''
        }

    }
}
