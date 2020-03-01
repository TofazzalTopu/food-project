package com.bits.bdfp.customer

import com.bits.bdfp.customer.customersaleschannel.CreateCustomerSalesChannelAction
import com.bits.bdfp.customer.customersaleschannel.DeleteCustomerSalesChannelAction
import com.bits.bdfp.customer.customersaleschannel.ListCustomerSalesChannelAction
import com.bits.bdfp.customer.customersaleschannel.UpdateCustomerSalesChannelAction
import com.bits.bdfp.customer.customersaleschannel.ReadCustomerSalesChannelAction
import com.bits.bdfp.customer.customersaleschannel.SearchCustomerSalesChannelAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class CustomerSalesChannelController {

    @Autowired
    private CreateCustomerSalesChannelAction createCustomerSalesChannelAction
    @Autowired
    private UpdateCustomerSalesChannelAction updateCustomerSalesChannelAction
    @Autowired
    private ListCustomerSalesChannelAction listCustomerSalesChannelAction
    @Autowired
    private DeleteCustomerSalesChannelAction deleteCustomerSalesChannelAction
    @Autowired
    private ReadCustomerSalesChannelAction readCustomerSalesChannelAction
    @Autowired
    private SearchCustomerSalesChannelAction searchCustomerSalesChannelAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listCustomerSalesChannelAction.execute(params, null)
        render listCustomerSalesChannelAction.postCondition(null, list) as JSON
    }

    def show = {
        CustomerSalesChannel customerSalesChannel = new CustomerSalesChannel()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [customerSalesChannel: customerSalesChannel, list: enterpriseList,result:result as JSON])
    }

    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        CustomerSalesChannel customerSalesChannel = new CustomerSalesChannel(params)
        CustomerSalesChannel customerSalesChannelInstance = createCustomerSalesChannelAction.preCondition(applicationUser, customerSalesChannel)
        Message message = null
        if (customerSalesChannelInstance == null) {
            message = createCustomerSalesChannelAction.getValidationErrorMessage(customerSalesChannel)
        } else {
            customerSalesChannelInstance = createCustomerSalesChannelAction.execute(null, customerSalesChannelInstance)
            if (customerSalesChannelInstance) {
                message = createCustomerSalesChannelAction.getMessage("Customer Sales Channel",Message.SUCCESS, createCustomerSalesChannelAction.SUCCESS_SAVE)
            } else {
                message = createCustomerSalesChannelAction.getMessage("Customer Sales Channel",Message.ERROR, createCustomerSalesChannelAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readCustomerSalesChannelAction.execute(params, null)
        render result as JSON
    }

    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updateCustomerSalesChannelAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteCustomerSalesChannelAction.execute(params, null)
        render message as JSON

    }

    def search = {
        CustomerSalesChannel customerSalesChannel = searchCustomerSalesChannelAction.execute(params.fieldName, params.fieldValue)
        if (customerSalesChannel) {
            render customerSalesChannel as JSON
        } else {
            render ''
        }

    }
}
