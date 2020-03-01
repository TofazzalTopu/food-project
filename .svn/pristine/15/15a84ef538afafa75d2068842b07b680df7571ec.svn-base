package com.bits.bdfp.customer

import com.bits.bdfp.customer.customereligibilitymaster.CreateCustomerEligibilityMasterAction
import com.bits.bdfp.customer.customereligibilitymaster.DeleteCustomerEligibilityMasterAction
import com.bits.bdfp.customer.customereligibilitymaster.ListCustomerEligibilityMasterAction
import com.bits.bdfp.customer.customereligibilitymaster.UpdateCustomerEligibilityMasterAction
import com.bits.bdfp.customer.customereligibilitymaster.ReadCustomerEligibilityMasterAction
import com.bits.bdfp.customer.customereligibilitymaster.SearchCustomerEligibilityMasterAction

import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class CustomerEligibilityMasterController {

    @Autowired
    private CreateCustomerEligibilityMasterAction createCustomerEligibilityMasterAction
    @Autowired
    private UpdateCustomerEligibilityMasterAction updateCustomerEligibilityMasterAction
    @Autowired
    private ListCustomerEligibilityMasterAction listCustomerEligibilityMasterAction
    @Autowired
    private DeleteCustomerEligibilityMasterAction deleteCustomerEligibilityMasterAction
    @Autowired
    private ReadCustomerEligibilityMasterAction readCustomerEligibilityMasterAction
    @Autowired
    private SearchCustomerEligibilityMasterAction searchCustomerEligibilityMasterAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        Object objList = listCustomerEligibilityMasterAction.execute(params, null)
        render listCustomerEligibilityMasterAction.postCondition(objList, null) as JSON
    }

    def show = {
        render(view: "show")
    }

    def create = {
        Message message = createCustomerEligibilityMasterAction.execute(params, null)
        render message as JSON
    }

    def edit = {
        Map data = (Map) readCustomerEligibilityMasterAction.execute(params, null)
        render data as JSON
    }

    def update = {
        Message message = updateCustomerEligibilityMasterAction.execute(params, null)
        render message as JSON
    }

    def delete = {
        Message message = deleteCustomerEligibilityMasterAction.execute(params, null)
        render message as JSON
    }

    def search = {
        CustomerEligibilityMaster customerEligibilityMaster = (CustomerEligibilityMaster) searchCustomerEligibilityMasterAction.execute(params, null)
        if (customerEligibilityMaster) {
            render customerEligibilityMaster as JSON
        } else {
            render ''
        }
    }
    def loadEligibilityForCustomer = {

    }
}
