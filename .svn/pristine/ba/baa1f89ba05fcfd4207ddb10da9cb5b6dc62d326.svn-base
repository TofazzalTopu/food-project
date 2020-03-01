package com.bits.bdfp.customer

import com.bits.bdfp.customer.customerpaymenttype.CreateCustomerPaymentTypeAction
import com.bits.bdfp.customer.customerpaymenttype.DeleteCustomerPaymentTypeAction
import com.bits.bdfp.customer.customerpaymenttype.ListCustomerPaymentTypeAction
import com.bits.bdfp.customer.customerpaymenttype.UpdateCustomerPaymentTypeAction
import com.bits.bdfp.customer.customerpaymenttype.ReadCustomerPaymentTypeAction
import com.bits.bdfp.customer.customerpaymenttype.SearchCustomerPaymentTypeAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class CustomerPaymentTypeController {

    @Autowired
    private CreateCustomerPaymentTypeAction createCustomerPaymentTypeAction
    @Autowired
    private UpdateCustomerPaymentTypeAction updateCustomerPaymentTypeAction
    @Autowired
    private ListCustomerPaymentTypeAction listCustomerPaymentTypeAction
    @Autowired
    private DeleteCustomerPaymentTypeAction deleteCustomerPaymentTypeAction
    @Autowired
    private ReadCustomerPaymentTypeAction readCustomerPaymentTypeAction
    @Autowired
    private SearchCustomerPaymentTypeAction searchCustomerPaymentTypeAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listCustomerPaymentTypeAction.execute(params, null)
        render listCustomerPaymentTypeAction.postCondition(null, list) as JSON
    }

    def show = {
        CustomerPaymentType customerPaymentType = new CustomerPaymentType()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [customerPaymentType: customerPaymentType,list: enterpriseList,result:result as JSON])
    }

    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        CustomerPaymentType customerPaymentType = new CustomerPaymentType(params)
        CustomerPaymentType customerPaymentTypeInstance = createCustomerPaymentTypeAction.preCondition(applicationUser, customerPaymentType)
        Message message = null
        if (customerPaymentTypeInstance == null) {
            message = createCustomerPaymentTypeAction.getValidationErrorMessage(customerPaymentType)
        } else {
            customerPaymentTypeInstance = createCustomerPaymentTypeAction.execute(null, customerPaymentTypeInstance)
            if (customerPaymentTypeInstance) {
                message = createCustomerPaymentTypeAction.getMessage("Customer Payment Type",Message.SUCCESS, createCustomerPaymentTypeAction.SUCCESS_SAVE)
            } else {
                message = createCustomerPaymentTypeAction.getMessage("Customer Payment Type",Message.ERROR, createCustomerPaymentTypeAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readCustomerPaymentTypeAction.execute(params, null)
        render result as JSON
    }

    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updateCustomerPaymentTypeAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteCustomerPaymentTypeAction.execute(params, null)
        render message as JSON

    }


    def search = {
        CustomerPaymentType customerPaymentType = searchCustomerPaymentTypeAction.execute(params.fieldName, params.fieldValue)
        if (customerPaymentType) {
            render customerPaymentType as JSON
        } else {
            render ''
        }

    }
}
