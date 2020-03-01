package com.bits.bdfp.inventory.sales

import com.bits.bdfp.customer.customermaster.ListSubAreaForCustomerAction
import com.bits.bdfp.inventory.sales.marketreturn.ChangeStatusAction
import com.bits.bdfp.inventory.sales.marketreturn.CreateMarketReturnAction
import com.bits.bdfp.inventory.sales.marketreturn.DeleteMarketReturnAction
import com.bits.bdfp.inventory.sales.marketreturn.DistributionPointStockChangeAction
import com.bits.bdfp.inventory.sales.marketreturn.FetchCustomerByGeoLocationAction
import com.bits.bdfp.inventory.sales.marketreturn.ListInvoiceFromStockAction
import com.bits.bdfp.inventory.sales.marketreturn.ListMarketReturnAction
import com.bits.bdfp.inventory.sales.marketreturn.ListReturnDetailsAction
import com.bits.bdfp.inventory.sales.marketreturn.ReadCustomerForMarketReturnAction
import com.bits.bdfp.inventory.sales.marketreturn.UpdateMarketReturnAction
import com.bits.bdfp.inventory.sales.marketreturn.ReadMarketReturnAction
import com.bits.bdfp.inventory.sales.marketreturn.SearchMarketReturnAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class MarketReturnController {

    @Autowired
    private CreateMarketReturnAction createMarketReturnAction
    @Autowired
    private UpdateMarketReturnAction updateMarketReturnAction
    @Autowired
    private ListMarketReturnAction listMarketReturnAction
    @Autowired
    private DeleteMarketReturnAction deleteMarketReturnAction
    @Autowired
    private ReadMarketReturnAction readMarketReturnAction
    @Autowired
    private SearchMarketReturnAction searchMarketReturnAction
    @Autowired
    ReadCustomerForMarketReturnAction readCustomerForMarketReturnAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListSubAreaForCustomerAction listSubAreaForCustomerAction
    @Autowired
    FetchCustomerByGeoLocationAction fetchCustomerByGeoLocationAction
    @Autowired
    ListInvoiceFromStockAction listInvoiceFromStockAction
    @Autowired
    DistributionPointStockChangeAction distributionPointStockChangeAction
    @Autowired
    ListReturnDetailsAction listReturnDetailsAction
    @Autowired
    ChangeStatusAction changeStatusAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        render listMarketReturnAction.execute(params, null) as JSON
    }

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        Map result = [:]
        if(list.size() == 1){
            params.put('entId', list[0].id)
            result = readCustomerForMarketReturnAction.execute(applicationUser, params)
        }
        if(result) {
            render(view: "show", model: [customer: result.customer,
                                         dpList: result.dpList as JSON,
                                         factoryList: result.factoryList as JSON,
                                         wareHouseList: result.wareHouseList as JSON,
                                         subWareHouseList: result.subWareHouseList as JSON,
                                         dpSize: result.dpList? result.dpList.size(): 0,
                                         factorySize: result.factoryList? result.factoryList.size(): 0,
                                         wareHouseSize: result.wareHouseList? result.wareHouseList.size(): 0,
                                         subWareHouseSize: result.subWareHouseList? result.subWareHouseList.size(): 0])
        }else{
            render(view: "show", model: [customer: '',
                                         dpList: '',
                                         factoryList: '',
                                         wareHouseList: '',
                                         subWareHouseList: '',
                                         dpSize: 0,
                                         factorySize: 0,
                                         wareHouseSize: 0,
                                         subWareHouseSize: 0])
        }

    }

    def save = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createMarketReturnAction.execute(params, applicationUser)
        render message as JSON
    }

    def edit = {
        Map map = readMarketReturnAction.execute(params, null)
        MarketReturn marketReturn = map.get('marketReturn')
        Message message = map.get('message')
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        Map result = [:]
        if(list.size() == 1){
            params.put('entId', list[0].id)
            result = readCustomerForMarketReturnAction.execute(applicationUser, params)
        }
        if(message){
            render message as JSON
        }else {
            render(template: 'showEdit', model: [marketReturn    : marketReturn,
                                                 message         : message,
                                                 customer        : result.customer,
                                                 dpList          : result.dpList as JSON,
                                                 factoryList     : result.factoryList as JSON,
                                                 wareHouseList   : result.wareHouseList as JSON,
                                                 subWareHouseList: result.subWareHouseList as JSON,
                                                 dpSize          : result.dpList ? result.dpList.size() : 0,
                                                 factorySize     : result.factoryList ? result.factoryList.size() : 0,
                                                 wareHouseSize   : result.wareHouseList ? result.wareHouseList.size() : 0,
                                                 subWareHouseSize: result.subWareHouseList ? result.subWareHouseList.size() : 0])
        }
//        Map data = (Map) readMarketReturnAction.execute(params, null)
//        render data as JSON
    }

    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateMarketReturnAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Object object = deleteMarketReturnAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = deleteMarketReturnAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def search = {
        MarketReturn marketReturn = (MarketReturn) searchMarketReturnAction.execute(params, null)
        if (marketReturn) {
            render marketReturn as JSON
        } else {
            render ''
        }
    }

    def fetchGeoLocation = {
        List list = (List) listSubAreaForCustomerAction.execute(params, 1)
        render list as JSON
    }

    def fetchCustomer = {
        ApplicationUser applicationUser = session?.applicationUser
        render fetchCustomerByGeoLocationAction.execute(params, applicationUser) as JSON
    }

    def popupCustomerListPanel = {
        ApplicationUser applicationUser = session?.applicationUser
        List customerList = (List) fetchCustomerByGeoLocationAction.execute(params, applicationUser)
        if(params.cat == '3') {
            render(view: '/marketReturn/popupCustomerlistByGeo', model: [aaData: customerList as JSON, sale: 1])
        }else{
            render(view: '/marketReturn/popupCustomerlistByGeo', model: [aaData: customerList as JSON, sale: ''])
        }
    }

    def listInvoice = {
        render listInvoiceFromStockAction.execute(null, params) as JSON
    }

    def popupInvoiceListPanel = {
        List list = (List) listInvoiceFromStockAction.execute(null,params)
        render(view: '/receiveProductsFromMarket/popUpInvoiceList', model: [aaData: list as JSON])
    }

    def changeQuantity = {
        render distributionPointStockChangeAction.execute(null, params) as JSON
    }

    def searchMarketReturn = {
        render(template: 'searchMarketReturn')
    }

    def listMr = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = listMarketReturnAction.execute(params, applicationUser)
        render listMarketReturnAction.postCondition(null, list) as JSON
    }

    def listDetails = {
        List list = listReturnDetailsAction.execute(params, null)
        render listReturnDetailsAction.postCondition(null, list) as JSON
    }

    def listMrForAutoComplete = {
        ApplicationUser applicationUser = session?.applicationUser
        render listMarketReturnAction.preCondition(params,applicationUser) as JSON
    }

    def statusChange = {
        ApplicationUser applicationUser = session?.applicationUser
        render changeStatusAction.execute(params, applicationUser) as JSON
    }
}
