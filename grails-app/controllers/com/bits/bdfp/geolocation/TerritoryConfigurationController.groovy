package com.bits.bdfp.geolocation

import com.bits.bdfp.geolocation.territoryconfiguration.CreateTerritoryConfigurationAction
import com.bits.bdfp.geolocation.territoryconfiguration.DeleteTerritoryConfigurationAction
import com.bits.bdfp.geolocation.territoryconfiguration.FetchListForDropDownAction
import com.bits.bdfp.geolocation.territoryconfiguration.ListTerritoryConfigurationAction
import com.bits.bdfp.geolocation.territoryconfiguration.SearchTerritoryByEnterpriseAction
import com.bits.bdfp.geolocation.territoryconfiguration.UpdateTerritoryConfigurationAction
import com.bits.bdfp.geolocation.territoryconfiguration.ReadTerritoryConfigurationAction
import com.bits.bdfp.geolocation.territoryconfiguration.SearchTerritoryConfigurationAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class TerritoryConfigurationController {

    @Autowired
    private CreateTerritoryConfigurationAction createTerritoryConfigurationAction
    @Autowired
    private UpdateTerritoryConfigurationAction updateTerritoryConfigurationAction
    @Autowired
    private ListTerritoryConfigurationAction listTerritoryConfigurationAction
    @Autowired
    private DeleteTerritoryConfigurationAction deleteTerritoryConfigurationAction
    @Autowired
    private ReadTerritoryConfigurationAction readTerritoryConfigurationAction
    @Autowired
    private SearchTerritoryConfigurationAction searchTerritoryConfigurationAction
    @Autowired
    private SearchTerritoryByEnterpriseAction searchTerritoryByEnterpriseAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    FetchListForDropDownAction fetchListForDropDownAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listTerritoryConfigurationAction.execute(params, null)
        render listTerritoryConfigurationAction.postCondition(null, list) as JSON
    }

    def show = {
        TerritoryConfiguration territoryConfiguration = new TerritoryConfiguration()
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser,null)
        Map result = ["results": list, "total": list.size()]
        render(template: "show", model: [territoryConfiguration: territoryConfiguration, result:result as JSON, list: list])
    }

    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = createTerritoryConfigurationAction.execute(params, applicationUser)
        render message as JSON
    }

    def edit = {
        Map result = readTerritoryConfigurationAction.execute(params, null)
        render result as JSON
    }

    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updateTerritoryConfigurationAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteTerritoryConfigurationAction.execute(params, null)
        render message as JSON
    }

    def search = {
        TerritoryConfiguration territoryConfiguration = searchTerritoryConfigurationAction.execute(params.fieldName, params.fieldValue)
        if (territoryConfiguration) {
            render territoryConfiguration as JSON
        } else {
            render ''
        }
    }

    def searchTerritoryByEnterprise = {
        List result = searchTerritoryByEnterpriseAction.execute(params, null)
        render result as JSON
    }

    def fetchDropdownList = {
        List list = fetchListForDropDownAction.execute(params, null)
        render list as JSON
    }
}
