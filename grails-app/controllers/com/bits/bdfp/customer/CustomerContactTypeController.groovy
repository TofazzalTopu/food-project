package com.bits.bdfp.customer

import com.bits.bdfp.customer.customercontacttype.CreateCustomerContactTypeAction
import com.bits.bdfp.customer.customercontacttype.DeleteCustomerContactTypeAction
import com.bits.bdfp.customer.customercontacttype.ListCustomerContactTypeAction
import com.bits.bdfp.customer.customercontacttype.UpdateCustomerContactTypeAction
import com.bits.bdfp.customer.customercontacttype.ReadCustomerContactTypeAction
import com.bits.bdfp.customer.customercontacttype.SearchCustomerContactTypeAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class CustomerContactTypeController {

    @Autowired
    private CreateCustomerContactTypeAction createCustomerContactTypeAction
    @Autowired
    private UpdateCustomerContactTypeAction updateCustomerContactTypeAction
    @Autowired
    private ListCustomerContactTypeAction listCustomerContactTypeAction
    @Autowired
    private DeleteCustomerContactTypeAction deleteCustomerContactTypeAction
    @Autowired
    private ReadCustomerContactTypeAction readCustomerContactTypeAction
    @Autowired
    private SearchCustomerContactTypeAction searchCustomerContactTypeAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listCustomerContactTypeAction.execute(params, null)
        render listCustomerContactTypeAction.postCondition(null, list) as JSON
    }

    def show = {
        CustomerContactType customerContactType = new CustomerContactType()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [customerContactType: customerContactType,list: enterpriseList,result:result as JSON])
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        CustomerContactType customerContactType = new CustomerContactType(params)
        CustomerContactType customerContactTypeInstance = createCustomerContactTypeAction.preCondition(applicationUser, customerContactType)
        Message message = null
        if (customerContactTypeInstance == null) {
            message = createCustomerContactTypeAction.getValidationErrorMessage(customerContactType)
        } else {
            customerContactTypeInstance = createCustomerContactTypeAction.execute(null, customerContactTypeInstance)
            if (customerContactTypeInstance) {
                message = createCustomerContactTypeAction.getMessage("Customer Contact Type",Message.SUCCESS, createCustomerContactTypeAction.SUCCESS_SAVE)
            } else {
                message = createCustomerContactTypeAction.getMessage("Customer Contact Type",Message.ERROR, createCustomerContactTypeAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result =readCustomerContactTypeAction.execute(params, null)
        render result as JSON
    }
    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updateCustomerContactTypeAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteCustomerContactTypeAction.execute(params, null)
        render message as JSON

    }



    def search = {
        CustomerContactType customerContactType = searchCustomerContactTypeAction.execute(params.fieldName, params.fieldValue)
        if (customerContactType) {
            render customerContactType as JSON
        } else {
            render ''
        }

    }
}
