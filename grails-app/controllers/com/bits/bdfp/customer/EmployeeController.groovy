package com.bits.bdfp.customer

import com.bits.bdfp.customer.employee.CreateEmployeeAction
import com.bits.bdfp.customer.employee.FlexListEmployeeAction
import com.bits.bdfp.customer.employee.ListEmployeeAction
import com.bits.bdfp.customer.employee.ListTerritoryByEmployeeAction
import com.bits.bdfp.customer.employee.ReadEmployeeAction
import com.bits.bdfp.customer.employee.UpdateEmployeeAction
import com.bits.bdfp.geolocation.territoryconfiguration.SearchTerritoryByEnterpriseAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class EmployeeController {

    @Autowired
    CreateEmployeeAction createEmployeeAction
    @Autowired
    UpdateEmployeeAction updateEmployeeAction
    @Autowired
    FlexListEmployeeAction flexListEmployeeAction
    @Autowired
    ReadEmployeeAction  readEmployeeAction
    @Autowired
    ListEmployeeAction listEmployeeAction
    @Autowired
    EnterpriseAutocompleteListAction  enterpriseAutocompleteListAction
    @Autowired
    SearchTerritoryByEnterpriseAction searchTerritoryByEnterpriseAction
    @Autowired
    ListTerritoryByEmployeeAction listTerritoryByEmployeeAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        CustomerMaster customerMaster = new CustomerMaster()
        Map result = [:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        Map supervisor = [:]
        List supervisorList = flexListEmployeeAction.execute(params, null)
        if (supervisorList && supervisorList.size()>0){
            supervisor = ["results": supervisorList, "total": supervisorList.size()]
        }
        render(template: "/customer/customerMaster/employee/show", model: [customerMaster: customerMaster, list: enterpriseList, result:result as JSON, supervisorList: supervisor as JSON, territoryIds: '-1'])
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createEmployeeAction.execute(params, applicationUser)
        render message as JSON
    }

    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateEmployeeAction.execute(params, applicationUser)
        render message as JSON
    }

    def flexListEmployee = {
        Map result = (Map) flexListEmployeeAction.execute(params, null)
        render result as JSON
    }

    def edit = {
        String supervisor = ""
        String enterPriseName = ""
        String territoryIds = ""
        CustomerMaster customerMaster = (CustomerMaster) readEmployeeAction.execute(params, null)
        if(customerMaster){
            if(customerMaster.customerMaster){
                supervisor = "[" + customerMaster.customerMaster.code + "] " + customerMaster.customerMaster.name
            }
            enterPriseName = customerMaster.enterpriseConfiguration.name
            params.put("employeeId", customerMaster.id)
            Map territoryIdList = (Map)listTerritoryByEmployeeAction.execute(params, null)
            territoryIds = territoryIdList.territoryIds
        }
        Map result =[:]
        ApplicationUser applicationUser = session?.applicationUser
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }

        Map supervisors = [:]
        List supervisorList = flexListEmployeeAction.execute(params, null)
        if (supervisorList && supervisorList.size()>0){
            supervisors = ["results": supervisorList, "total": supervisorList.size()]
        }
        render(view: "/customer/customerMaster/employee/edit", model: [customerMaster: customerMaster, supervisor: supervisor, enterPriseName: enterPriseName, list: enterpriseList, result: result as JSON, supervisorList: supervisors as JSON, territoryIds: territoryIds])
    }

    def searchEmployee = {
        Map supervisors = [:]
        params.put("editEmployee","true")
        List supervisorList = flexListEmployeeAction.execute(params, null)
        if (supervisorList && supervisorList.size()>0){
            supervisors = ["results": supervisorList, "total": supervisorList.size()]
        }
        render(template: "/customer/customerMaster/employee/search", model: [supervisorList: supervisors as JSON])
    }

    def popupEmployeeListPanel = {
        render(view: '/customer/customerMaster/employee/employeeListPanel')
    }

    def jsonEmployeeList = {
        String result = listEmployeeAction.execute(params, null)
        render result
    }
}
