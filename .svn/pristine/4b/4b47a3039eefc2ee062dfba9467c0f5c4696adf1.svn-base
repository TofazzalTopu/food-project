package com.bits.bdfp.settings

import com.bits.bdfp.settings.businessunitconfiguration.CreateBusinessUnitConfigurationAction
import com.bits.bdfp.settings.businessunitconfiguration.DeleteBusinessUnitConfigurationAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.bits.bdfp.settings.businessunitconfiguration.ListBusinessUnitConfigurationAction
import com.bits.bdfp.settings.businessunitconfiguration.ListBusinessUnitEnterpriseAction
import com.bits.bdfp.settings.businessunitconfiguration.UpdateBusinessUnitConfigurationAction
import com.bits.bdfp.settings.businessunitconfiguration.ReadBusinessUnitConfigurationAction


import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.codehaus.groovy.grails.web.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class BusinessUnitConfigurationController {

    @Autowired
    private CreateBusinessUnitConfigurationAction createBusinessUnitConfigurationAction
    @Autowired
    private UpdateBusinessUnitConfigurationAction updateBusinessUnitConfigurationAction
    @Autowired
    private ListBusinessUnitConfigurationAction listBusinessUnitConfigurationAction
    @Autowired
    private DeleteBusinessUnitConfigurationAction deleteBusinessUnitConfigurationAction
    @Autowired
    private ReadBusinessUnitConfigurationAction readBusinessUnitConfigurationAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListBusinessUnitEnterpriseAction listBusinessUnitEnterpriseAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        ApplicationUser applicationUser = session?.applicationUser
        render listBusinessUnitConfigurationAction.execute(params, applicationUser) as JSON
    }

    def show = {
        BusinessUnitConfiguration businessUnitConfiguration = new BusinessUnitConfiguration()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [businessUnitConfiguration: businessUnitConfiguration, list: enterpriseList,result:result as JSON])
    }

    def create = {

        ApplicationUser applicationUser = session?.applicationUser
        Message message = createBusinessUnitConfigurationAction.execute(params, applicationUser)
        render message as JSON
    }

    def edit = {
        ApplicationUser applicationUser = session?.applicationUser
        render readBusinessUnitConfigurationAction.execute(params, applicationUser) as JSON
    }

    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateBusinessUnitConfigurationAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteBusinessUnitConfigurationAction.execute(params, null)
        render message as JSON
    }

    def listBusinessUnit = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = listBusinessUnitEnterpriseAction.execute(params, applicationUser)
        Map result = ["results": list, "total": list.size()]
        render result as JSON
    }

}
