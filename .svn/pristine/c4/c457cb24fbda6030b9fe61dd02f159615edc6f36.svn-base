package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.inventory.demandorder.adjustusingho.CreateAdjustUsingHoAction
import com.bits.bdfp.inventory.demandorder.adjustusingho.DeleteAdjustUsingHoAction
import com.bits.bdfp.inventory.demandorder.adjustusingho.FetchCustomerListByGeoAction
import com.bits.bdfp.inventory.demandorder.adjustusingho.ListAdjustUsingHoAction
import com.bits.bdfp.inventory.demandorder.adjustusingho.UpdateAdjustUsingHoAction
import com.bits.bdfp.inventory.demandorder.adjustusingho.ReadAdjustUsingHoAction
import com.bits.bdfp.inventory.demandorder.adjustusingho.SearchAdjustUsingHoAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class AdjustUsingHoController {

    @Autowired
    private CreateAdjustUsingHoAction createAdjustUsingHoAction
    @Autowired
    private UpdateAdjustUsingHoAction updateAdjustUsingHoAction
    @Autowired
    private ListAdjustUsingHoAction listAdjustUsingHoAction
    @Autowired
    private DeleteAdjustUsingHoAction deleteAdjustUsingHoAction
    @Autowired
    private ReadAdjustUsingHoAction readAdjustUsingHoAction
    @Autowired
    private SearchAdjustUsingHoAction searchAdjustUsingHoAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    FetchCustomerListByGeoAction fetchCustomerListByGeoAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        Object objList = listAdjustUsingHoAction.execute(params, null)
        render listAdjustUsingHoAction.postCondition(objList, null) as JSON
    }

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        Map result = ["results": list, "total": list.size()]
        render(view: "show", model: [result: result as JSON, list: list])
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createAdjustUsingHoAction.execute(params, applicationUser)
        render message as JSON
    }

    def edit = {
        Map data = (Map) readAdjustUsingHoAction.execute(params, null)
        render data as JSON
    }

    def update = {
        Message message = updateAdjustUsingHoAction.execute(params, null)
        render message as JSON
    }

    def delete = {
        Message message = deleteAdjustUsingHoAction.execute(params, null)
        render message as JSON
    }

    def search = {
        AdjustUsingHo adjustUsingHo = (AdjustUsingHo) searchAdjustUsingHoAction.execute(params, null)
        if (adjustUsingHo) {
            render adjustUsingHo as JSON
        } else {
            render ''
        }
    }

    def fetchCustomer = {
        render fetchCustomerListByGeoAction.execute(params, null) as JSON
    }

    def popupCustomerListPanel = {
        List customerList = (List) fetchCustomerListByGeoAction.execute(params, null)
        render(view: '/invoice/popUpCustomerListByCategoryAndGeo', model: [aaData: customerList as JSON])
    }
}
