package com.bits.bdfp.inventory.sales

import com.bits.bdfp.inventory.sales.marketreturn.ChangeStatusAction
import com.bits.bdfp.inventory.sales.processmarketreturn.CreateProcessMarketReturnAction
import com.bits.bdfp.inventory.sales.processmarketreturn.DeleteProcessMarketReturnAction
import com.bits.bdfp.inventory.sales.processmarketreturn.ListCustomerByDpAction
import com.bits.bdfp.inventory.sales.processmarketreturn.ListCustomerForProcessMarketReturnAction
import com.bits.bdfp.inventory.sales.processmarketreturn.ListDpForProcessMarketReturnAction
import com.bits.bdfp.inventory.sales.processmarketreturn.ListMarketReturnForProcessAction
import com.bits.bdfp.inventory.sales.processmarketreturn.ListProcessMarketReturnAction
import com.bits.bdfp.inventory.sales.processmarketreturn.ListProcessedMrDetailsAction
import com.bits.bdfp.inventory.sales.processmarketreturn.ListReturnDetailsForProcessAction
import com.bits.bdfp.inventory.sales.processmarketreturn.ListWarehouseByUserAction
import com.bits.bdfp.inventory.sales.processmarketreturn.RejectMarketReturnAction
import com.bits.bdfp.inventory.sales.processmarketreturn.SearchProcessedMarketReturnAction
import com.bits.bdfp.inventory.sales.processmarketreturn.UpdateProcessMarketReturnAction
import com.bits.bdfp.inventory.sales.processmarketreturn.ReadProcessMarketReturnAction
import com.bits.bdfp.inventory.sales.processmarketreturn.SearchProcessMarketReturnAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class ProcessMarketReturnController {

    @Autowired
    private CreateProcessMarketReturnAction createProcessMarketReturnAction
    @Autowired
    private UpdateProcessMarketReturnAction updateProcessMarketReturnAction
    @Autowired
    private ListProcessMarketReturnAction listProcessMarketReturnAction
    @Autowired
    private DeleteProcessMarketReturnAction deleteProcessMarketReturnAction
    @Autowired
    private ReadProcessMarketReturnAction readProcessMarketReturnAction
    @Autowired
    private SearchProcessMarketReturnAction searchProcessMarketReturnAction
    @Autowired
    ListMarketReturnForProcessAction listMarketReturnForProcessAction
    @Autowired
    RejectMarketReturnAction rejectMarketReturnAction
    @Autowired
    ListReturnDetailsForProcessAction listReturnDetailsForProcessAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListWarehouseByUserAction listWarehouseByUserAction
    @Autowired
    ListDpForProcessMarketReturnAction listDpForProcessMarketReturnAction
    @Autowired
    ListCustomerByDpAction listCustomerByDpAction
    @Autowired
    SearchProcessedMarketReturnAction searchProcessedMarketReturnAction
    @Autowired
    ListProcessedMrDetailsAction listProcessedMrDetailsAction
    @Autowired
    ChangeStatusAction changeStatusAction
    @Autowired
    ListCustomerForProcessMarketReturnAction listCustomerForProcessMarketReturnAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        render listProcessMarketReturnAction.execute(params, null) as JSON
    }

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        Map result = [:]
        if(list.size() == 1) {
            params.put('entId', list[0].id)
            result = listWarehouseByUserAction.execute(applicationUser, params)
        }
        if(result) {
            render(view: "show", model: [dpList: result.dpList? result.dpList as JSON : ''])
        }else{
            render(view: "show", model: [dpList: ''])
        }
    }

    def save = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createProcessMarketReturnAction.execute(params, applicationUser)
        render message as JSON
    }

    def edit = {
        Map data = (Map) readProcessMarketReturnAction.execute(params, null)
        render data as JSON
    }

    def update = {
        Object object = updateProcessMarketReturnAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = updateProcessMarketReturnAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def delete = {
        Object object = deleteProcessMarketReturnAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = deleteProcessMarketReturnAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def search = {
        ProcessMarketReturn processMarketReturn = (ProcessMarketReturn) searchProcessMarketReturnAction.execute(params, null)
        if (processMarketReturn) {
            render processMarketReturn as JSON
        } else {
            render ''
        }
    }

    def listMrNo = {
        List list = listMarketReturnForProcessAction.execute(params, null)
        render listMarketReturnForProcessAction.postCondition(null, list) as JSON
    }

    def rejectMarketReturn = {
        ApplicationUser applicationUser = session?.applicationUser
        render rejectMarketReturnAction.execute(params, applicationUser) as JSON
    }

    def listDetails = {
        List list = listReturnDetailsForProcessAction.execute(params, null)
        render listReturnDetailsForProcessAction.postCondition(null, list) as JSON
    }

    def showProcessed = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        Map result = ["results": list, "total": list.size()]
        params.put('id', applicationUser.id)
        List dpList = (List) listDpForProcessMarketReturnAction.execute(params, null)
        render(view: "showProcessed", model: [ result: result as JSON, list : list, dpList: dpList as JSON,
                                               dpSize: dpList? dpList.size():0])
    }

    def fetchCustomer = {
        render listCustomerByDpAction.execute(params, null) as JSON
    }

    def popupCustomerListPanel = {
        List customerList = (List) listCustomerByDpAction.execute(params, null)
        render(view: '/invoice/popUpCustomerListByCategoryAndGeo', model: [aaData: customerList as JSON])
    }

    def searchMarketReturn = {
        ApplicationUser applicationUser = session?.applicationUser
        Object objList = searchProcessedMarketReturnAction.execute(params, applicationUser)
        render searchProcessedMarketReturnAction.postCondition(params, objList) as JSON
    }

    def fetchDetails = {
        Object objList = listProcessedMrDetailsAction.execute(params, null)
        render listProcessedMrDetailsAction.postCondition(params, objList) as JSON
    }

    def fetchCustomerForView = {
        render listCustomerForProcessMarketReturnAction.execute(params, null) as JSON
    }

    def popupCustomerListPanelForView = {
        ApplicationUser applicationUser = session?.applicationUser
        List customerList = (List) listCustomerForProcessMarketReturnAction.execute(params, applicationUser)
        render(view: '/invoice/popUpCustomerListByCategoryAndGeo', model: [aaData: customerList as JSON])
    }

    def statusChange = {
        render changeStatusAction.execute(params, null) as JSON
    }


}
