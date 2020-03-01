package com.bits.bdfp.customer

import com.bits.bdfp.customer.customerpriority.CreateCustomerPriorityAction
import com.bits.bdfp.customer.customerpriority.DeleteCustomerPriorityAction
import com.bits.bdfp.customer.customerpriority.ListCustomerPriorityAction
import com.bits.bdfp.customer.customerpriority.UpdateCustomerPriorityAction
import com.bits.bdfp.customer.customerpriority.ReadCustomerPriorityAction
import com.bits.bdfp.customer.customerpriority.SearchCustomerPriorityAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class CustomerPriorityController {

    @Autowired
    private CreateCustomerPriorityAction createCustomerPriorityAction
    @Autowired
    private UpdateCustomerPriorityAction updateCustomerPriorityAction
    @Autowired
    private ListCustomerPriorityAction listCustomerPriorityAction
    @Autowired
    private DeleteCustomerPriorityAction deleteCustomerPriorityAction
    @Autowired
    private ReadCustomerPriorityAction readCustomerPriorityAction
    @Autowired
    private SearchCustomerPriorityAction searchCustomerPriorityAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listCustomerPriorityAction.execute(params, null)
        render listCustomerPriorityAction.postCondition(null, list) as JSON
    }

    def show = {
        CustomerPriority customerPriority = new CustomerPriority()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [customerPriority: customerPriority, list: enterpriseList,result:result as JSON])
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        CustomerPriority customerPriority = new CustomerPriority(params)
        CustomerPriority customerPriorityInstance = createCustomerPriorityAction.preCondition(applicationUser, customerPriority)
        Message message = null
        if (customerPriorityInstance == null) {
            message = createCustomerPriorityAction.getValidationErrorMessage(customerPriority)
        } else {
            customerPriorityInstance = createCustomerPriorityAction.execute(null, customerPriorityInstance)
            if (customerPriorityInstance) {
                message = createCustomerPriorityAction.getMessage("Customer Priority",Message.SUCCESS, createCustomerPriorityAction.SUCCESS_SAVE)
            } else {
                message = createCustomerPriorityAction.getMessage("Customer Priority",Message.ERROR, createCustomerPriorityAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readCustomerPriorityAction.execute(params, null)
        render result as JSON
    }
    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updateCustomerPriorityAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteCustomerPriorityAction.execute(params, null)
        render message as JSON

    }

    def search = {
        CustomerPriority customerPriority = searchCustomerPriorityAction.execute(params.fieldName, params.fieldValue)
        if (customerPriority) {
            render customerPriority as JSON
        } else {
            render ''
        }

    }
}
