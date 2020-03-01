package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.inventory.demandorder.writeoff.CreateWriteOffAction
import com.bits.bdfp.inventory.demandorder.writeoff.DeleteWriteOffAction
import com.bits.bdfp.inventory.demandorder.writeoff.ListCustomerForWriteOffAction
import com.bits.bdfp.inventory.demandorder.writeoff.ListDistributionPointForWriteOffAction
import com.bits.bdfp.inventory.demandorder.writeoff.ListGeoLocationForWriteOffAction
import com.bits.bdfp.inventory.demandorder.writeoff.ListInvoiceForWriteOffAction
import com.bits.bdfp.inventory.demandorder.writeoff.ListTerritoryForWriteOffAction
import com.bits.bdfp.inventory.demandorder.writeoff.ListWriteOffAction
import com.bits.bdfp.inventory.demandorder.writeoff.UpdateWriteOffAction
import com.bits.bdfp.inventory.demandorder.writeoff.ReadWriteOffAction
import com.bits.bdfp.inventory.demandorder.writeoff.SearchWriteOffAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class WriteOffController {

    @Autowired
    private CreateWriteOffAction createWriteOffAction
    @Autowired
    private UpdateWriteOffAction updateWriteOffAction
    @Autowired
    private ListWriteOffAction listWriteOffAction
    @Autowired
    private DeleteWriteOffAction deleteWriteOffAction
    @Autowired
    private ReadWriteOffAction readWriteOffAction
    @Autowired
    private SearchWriteOffAction searchWriteOffAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListTerritoryForWriteOffAction listTerritoryForWriteOffAction
    @Autowired
    ListGeoLocationForWriteOffAction listGeoLocationForWriteOffAction
    @Autowired
    ListDistributionPointForWriteOffAction listDistributionPointForWriteOffAction
    @Autowired
    ListCustomerForWriteOffAction listCustomerForWriteOffAction
    @Autowired
    ListInvoiceForWriteOffAction listInvoiceForWriteOffAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        Object objList = listWriteOffAction.execute(params, null)
        render listWriteOffAction.postCondition(objList, null) as JSON
    }

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        Map result = ["results": list, "total": list.size()]
        render(view: "show", model: [result: result as JSON, list: list])
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createWriteOffAction.execute(params, applicationUser)
        render message as JSON
    }

    def edit = {
        Map data = (Map) readWriteOffAction.execute(params, null)
        render data as JSON
    }

    def update = {
        Message message = updateWriteOffAction.execute(params, null)
        render message as JSON
    }

    def delete = {
        Message message = deleteWriteOffAction.execute(params, null)
        render message as JSON
    }

    def search = {
        WriteOff writeOff = (WriteOff) searchWriteOffAction.execute(params, null)
        if (writeOff) {
            render writeOff as JSON
        } else {
            render ''
        }
    }

    def listTerritory = {
        render listTerritoryForWriteOffAction.execute(params, null) as JSON
    }

    def listGeo = {
        render listGeoLocationForWriteOffAction.execute(params, null) as JSON
    }

    def listDp = {
        render listDistributionPointForWriteOffAction.execute(params, null) as JSON
    }

    def fetchCustomer = {
        render listCustomerForWriteOffAction.execute(params, null) as JSON
    }

    def popupCustomerListPanel = {
        List customerList = (List) listCustomerForWriteOffAction.execute(params, null)
        render(view: '/invoice/popUpCustomerListByCategoryAndGeo', model: [aaData: customerList as JSON])
    }

    def listInvoice = {
        Object objList = listInvoiceForWriteOffAction.execute(params, null)
        render listInvoiceForWriteOffAction.postCondition(objList, null) as JSON
    }
}
