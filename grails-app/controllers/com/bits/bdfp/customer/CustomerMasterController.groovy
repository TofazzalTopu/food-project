package com.bits.bdfp.customer

import com.bits.bdfp.customer.customer.FlexListCustomerAction
import com.bits.bdfp.customer.customer.ListCustomerAction
import com.bits.bdfp.customer.customercategory.ListCustomerCategoryAction
import com.bits.bdfp.customer.customermaster.CreateCustomerMasterAction
import com.bits.bdfp.customer.customermaster.DeleteCustomerMasterAction
import com.bits.bdfp.customer.customermaster.ListCustomerMasterAction
import com.bits.bdfp.customer.customermaster.ListSubAreaForCustomerAction
import com.bits.bdfp.customer.customermaster.UpdateCustomerMasterAction
import com.bits.bdfp.customer.customermaster.ReadCustomerMasterAction
import com.bits.bdfp.customer.customermaster.SearchCustomerMasterAction
import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.geolocation.TerritorySubArea
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class CustomerMasterController {

    @Autowired
    private CreateCustomerMasterAction createCustomerMasterAction
    @Autowired
    private UpdateCustomerMasterAction updateCustomerMasterAction
    @Autowired
    private ListCustomerMasterAction listCustomerMasterAction
    @Autowired
    private DeleteCustomerMasterAction deleteCustomerMasterAction
    @Autowired
    private ReadCustomerMasterAction readCustomerMasterAction
    @Autowired
    private SearchCustomerMasterAction searchCustomerMasterAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListCustomerAction listCustomerAction
    @Autowired
    FlexListCustomerAction flexListCustomerAction
    @Autowired
    ListSubAreaForCustomerAction listSubAreaForCustomerAction
    @Autowired
    ListCustomerCategoryAction listCustomerCategoryAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listCustomerMasterAction.execute(params, null)
        render listCustomerMasterAction.postCondition(null, list) as JSON
    }

    def show = {
        CustomerMaster customerMaster = new CustomerMaster()
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        //List CustomerCategoryList = listCustomerCategoryAction.fetchCustomerCategory(params, null)
        render(template: "/customer/customerMaster/customer/show", model: [customerMaster: customerMaster, list:list])
    }

    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = createCustomerMasterAction.execute(params, applicationUser)
        render message as JSON
    }

    def edit = {
        String territoryId = ''
        ApplicationUser applicationUser = session?.applicationUser
        CustomerMaster customerMaster = readCustomerMasterAction.execute(params, null)
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        CustomerTerritorySubArea customerTerritorySubArea = CustomerTerritorySubArea.findByCustomerMaster(customerMaster)
        if(customerTerritorySubArea){
            long territory = customerTerritorySubArea.territorySubArea.territoryConfiguration.id
            territoryId =  territory.toString()
        }
        render(view: "/customer/customerMaster/customer/edit", model: [customerMaster: customerMaster, list: list,
                                                                       territoryId: territoryId])
    }

    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updateCustomerMasterAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteCustomerMasterAction.execute(params, null);
        render message as JSON
    }

    def search = {
        CustomerMaster customerMaster = searchCustomerMasterAction.execute(params.fieldName, params.fieldValue)
        if (customerMaster) {
            render customerMaster as JSON
        } else {
            render ''
        }
    }

    def showCustomerDetails = {
        CustomerMaster customerMaster = new CustomerMaster()
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
//        List list1 = flexListCustomerAction.execute(params, null)
        render(template: "/customer/customerMaster/customer/searchCustomer", model: [customerMaster: customerMaster, list:list])
    }

    def popupCustomerListPanel = {
        render(view: '/customer/customerMaster/customer/customerListPanel')
    }

    def jsonCustomerList = {
        String result = listCustomerAction.execute(params, null)
        render result
    }

    def listCustomer = {
        render flexListCustomerAction.execute(params, null) as JSON
    }

    def fetchSubArea = {
        List list = listSubAreaForCustomerAction.execute(params, null)
        render listSubAreaForCustomerAction.postCondition(null, list) as JSON
    }

    def fetchCustomerCodeInfo = {
        CustomerMaster customerMaster =CustomerMaster.findById(Long.parseLong(params.cusId))
        render customerMaster as JSON
    }

    def fetchCustomerInfoByCode = {
        CustomerMaster customerMaster =CustomerMaster.findByCode(Long.parseLong(params.cusId))
        render customerMaster as JSON
    }


    def fetchCustomerInfoByCategory = {
        List list = searchCustomerMasterAction.executeCustomerByCategory(params, null)
        render list as JSON
    }


}
