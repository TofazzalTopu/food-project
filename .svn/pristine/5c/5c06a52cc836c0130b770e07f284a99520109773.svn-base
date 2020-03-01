package com.bits.bdfp.customer

import com.bits.bdfp.customer.customertype.CreateCustomerTypeAction
import com.bits.bdfp.customer.customertype.DeleteCustomerTypeAction
import com.bits.bdfp.customer.customertype.ListCustomerTypeAction
import com.bits.bdfp.customer.customertype.UpdateCustomerTypeAction
import com.bits.bdfp.customer.customertype.ReadCustomerTypeAction
import com.bits.bdfp.customer.customertype.SearchCustomerTypeAction

import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class CustomerTypeController {

    @Autowired
    private CreateCustomerTypeAction createCustomerTypeAction
    @Autowired
    private UpdateCustomerTypeAction updateCustomerTypeAction
    @Autowired
    private ListCustomerTypeAction listCustomerTypeAction
    @Autowired
    private DeleteCustomerTypeAction deleteCustomerTypeAction
    @Autowired
    private ReadCustomerTypeAction readCustomerTypeAction
    @Autowired
    private SearchCustomerTypeAction searchCustomerTypeAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listCustomerTypeAction.execute(params, null)
        render listCustomerTypeAction.postCondition(null, list) as JSON
    }

    def show = {
        CustomerType customerType = new CustomerType()
        render(template: "show", model: [customerType: customerType])
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        CustomerType customerType = new CustomerType(params)
        CustomerType customerTypeInstance = createCustomerTypeAction.preCondition(applicationUser, customerType)
        Message message = null
        if (customerTypeInstance == null) {
            message = createCustomerTypeAction.getValidationErrorMessage(customerType)
        } else {
            customerTypeInstance = createCustomerTypeAction.execute(null, customerTypeInstance)
            if (customerTypeInstance) {
                message = createCustomerTypeAction.getMessage('Customer Type',Message.SUCCESS, createCustomerTypeAction.SUCCESS_SAVE)
            } else {
                message = createCustomerTypeAction.getMessage('Customer Type',Message.ERROR, createCustomerTypeAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        render readCustomerTypeAction.execute(params, null) as JSON
    }
    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updateCustomerTypeAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteCustomerTypeAction.execute(params, null)
        render message as JSON

    }


    def search = {
        CustomerType customerType = searchCustomerTypeAction.execute(params.fieldName, params.fieldValue)
        if (customerType) {
            render customerType as JSON
        } else {
            render ''
        }

    }
}
