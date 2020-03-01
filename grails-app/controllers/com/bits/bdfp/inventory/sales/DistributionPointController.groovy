package com.bits.bdfp.inventory.sales

import com.bits.bdfp.inventory.sales.distributionpoint.CreateDistributionPointAction
import com.bits.bdfp.inventory.sales.distributionpoint.DeleteDistributionPointAction
import com.bits.bdfp.inventory.sales.distributionpoint.DistributionPointDefaultCustomerAction
import com.bits.bdfp.inventory.sales.distributionpoint.ListDPByEnterpriseAndAppUserAction
import com.bits.bdfp.inventory.sales.distributionpoint.ListDistributionPointAction
import com.bits.bdfp.inventory.sales.distributionpoint.ListDistributionPointBySubAreaAction
import com.bits.bdfp.inventory.sales.distributionpoint.ListGeolocationInfoAction
import com.bits.bdfp.inventory.sales.distributionpoint.ListGeolocationInfobyCustomerAction
import com.bits.bdfp.inventory.sales.distributionpoint.SelectTerritoryListAction
import com.bits.bdfp.inventory.sales.distributionpoint.UpdateDistributionPointAction
import com.bits.bdfp.inventory.sales.distributionpoint.ReadDistributionPointAction
import com.bits.bdfp.inventory.sales.distributionpoint.SearchDistributionPointAction
import com.bits.bdfp.inventory.sales.distributionpoint.LoadDistributionPointByEnterpriseAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class DistributionPointController {

    @Autowired
    private CreateDistributionPointAction createDistributionPointAction
    @Autowired
    private UpdateDistributionPointAction updateDistributionPointAction
    @Autowired
    private ListDistributionPointAction listDistributionPointAction
    @Autowired
    private DeleteDistributionPointAction deleteDistributionPointAction
    @Autowired
    private ReadDistributionPointAction readDistributionPointAction
    @Autowired
    private SearchDistributionPointAction searchDistributionPointAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    SelectTerritoryListAction selectTerritoryListAction
    @Autowired
    ListGeolocationInfoAction listGeolocationInfoAction
    @Autowired
    ListGeolocationInfobyCustomerAction listGeolocationInfobyCustomerAction
    @Autowired
    DistributionPointDefaultCustomerAction distributionPointDefaultCustomerAction
    @Autowired
    LoadDistributionPointByEnterpriseAction loadDistributionPointByEnterpriseAction
    @Autowired
    ListDPByEnterpriseAndAppUserAction listDPByEnterpriseAndAppUserAction
    @Autowired
    ListDistributionPointBySubAreaAction listDistributionPointBySubAreaAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]
    Message message = null

    def list = {
        List list = listDistributionPointAction.execute(params, null)
        render listDistributionPointAction.postCondition(null, list) as JSON
    }

    def show = {
        DistributionPoint distributionPoint = new DistributionPoint()
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        Map result = ["results": list, "total": list?.size()]
        render(template: "show", model: [distributionPoint: distributionPoint, result: result as JSON, list: list])
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        message = createDistributionPointAction.execute(params, applicationUser)
        render message as JSON
    }

    def edit = {
        render readDistributionPointAction.execute(params, null) as JSON
    }

    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateDistributionPointAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteDistributionPointAction.execute(params, null)
        render message as JSON
    }

    def search = {
        DistributionPoint distributionPoint = searchDistributionPointAction.execute(params.fieldName, params.fieldValue)
        if (distributionPoint) {
            render distributionPoint as JSON
        } else {
            render ''
        }

    }
    def selectTerritoryAndInventory = {
        render selectTerritoryListAction.execute(params, null) as JSON
    }

    def selectGeoLocationGridData = {
        Map geoLocationInfoList = listGeolocationInfoAction.execute(params, null)
        render geoLocationInfoList as JSON
        // render(template: "geoLocationInfoTemplate",model: [geoLocationInfoList:geoLocationInfoList])
    }

    def selectGeoLocationGridDatabyCustomer = {
        Map geoLocationInfoList = listGeolocationInfobyCustomerAction.execute(params, null)
        render geoLocationInfoList as JSON
        // render(template: "geoLocationInfoTemplate",model: [geoLocationInfoList:geoLocationInfoList])
    }
    def distributionPointCustomerList = {
        List customerList = distributionPointDefaultCustomerAction.execute(params, null)
        render(view: 'popUpCustomerList', model: [aaData: customerList as JSON])
    }

    def autoCompleteCustomerListForDistribution = {
        List list = distributionPointDefaultCustomerAction.execute(params, null)
        render list as JSON

    }
    def loadDistributionPoint = {
        List list = loadDistributionPointByEnterpriseAction.execute(params, null)
        Map result = ["results": list, "total": list?.size()]
        render result as JSON
    }

    def listDPByEnterpriseAndAppUser = {
        List list = (List) listDPByEnterpriseAndAppUserAction.execute(params, null)
        Map result = ["results": list, "total": list?.size()]
        render result as JSON
    }

    def listDistributionPointBySubArea = {
        List result = (List) listDistributionPointBySubAreaAction.execute(params, null)
        render result as JSON
    }

}
