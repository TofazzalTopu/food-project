package com.bits.bdfp.inventory.sales

import com.bits.bdfp.inventory.sales.marketreturn.ReadCustomerForMarketReturnAction
import com.bits.bdfp.inventory.sales.receiveproductsfrommarket.CreateReceiveProductsFromMarketAction
import com.bits.bdfp.inventory.sales.receiveproductsfrommarket.DeleteReceiveProductsFromMarketAction
import com.bits.bdfp.inventory.sales.receiveproductsfrommarket.ListEnterpriseFinishProductAction
import com.bits.bdfp.inventory.sales.receiveproductsfrommarket.ListReceiveProductsFromMarketAction
import com.bits.bdfp.inventory.sales.receiveproductsfrommarket.ListWarehouseByDpAction
import com.bits.bdfp.inventory.sales.receiveproductsfrommarket.ReadInvoiceByCustomerAction
import com.bits.bdfp.inventory.sales.receiveproductsfrommarket.UpdateReceiveProductsFromMarketAction
import com.bits.bdfp.inventory.sales.receiveproductsfrommarket.ReadReceiveProductsFromMarketAction
import com.bits.bdfp.inventory.sales.receiveproductsfrommarket.SearchReceiveProductsFromMarketAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class ReceiveProductsFromMarketController {

    @Autowired
    private CreateReceiveProductsFromMarketAction createReceiveProductsFromMarketAction
    @Autowired
    private UpdateReceiveProductsFromMarketAction updateReceiveProductsFromMarketAction
    @Autowired
    private ListReceiveProductsFromMarketAction listReceiveProductsFromMarketAction
    @Autowired
    private DeleteReceiveProductsFromMarketAction deleteReceiveProductsFromMarketAction
    @Autowired
    private ReadReceiveProductsFromMarketAction readReceiveProductsFromMarketAction
    @Autowired
    private SearchReceiveProductsFromMarketAction searchReceiveProductsFromMarketAction
    @Autowired
    ReadCustomerForMarketReturnAction readCustomerForMarketReturnAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListEnterpriseFinishProductAction listEnterpriseFinishProductAction
    @Autowired
    ReadInvoiceByCustomerAction readInvoiceByCustomerAction
    @Autowired
    ListWarehouseByDpAction listWarehouseByDpAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        render listReceiveProductsFromMarketAction.execute(params, null) as JSON
    }

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        Map result = [:]
        if(list.size() == 1){
            params.put('entId', list[0].id)
            params.put('receive', 1)
            result = readCustomerForMarketReturnAction.execute(applicationUser, params)
        }
        if(result) {
            render(view: "show", model: [dpList: result.dpList as JSON,
                                         wareHouseList: result.wareHouseList as JSON,
                                         subWareHouseList: result.subWareHouseList as JSON,
                                         dpSize: result.dpList? result.dpList.size(): 0,
                                         wareHouseSize: result.wareHouseList? result.wareHouseList.size(): 0,
                                         subWareHouseSize: result.subWareHouseList? result.subWareHouseList.size(): 0])
        }else{
            render(view: "show", model: [dpList: '',
                                         wareHouseList: '',
                                         subWareHouseList: '',
                                         dpSize: 0,
                                         wareHouseSize: 0,
                                         subWareHouseSize: 0])
        }
    }

    def save = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createReceiveProductsFromMarketAction.execute(params, applicationUser)
        render message as JSON
    }

    def edit = {
        Map data = (Map) readReceiveProductsFromMarketAction.execute(params, null)
        render data as JSON
    }

    def update = {
        Object object = updateReceiveProductsFromMarketAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = updateReceiveProductsFromMarketAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def delete = {
        Object object = deleteReceiveProductsFromMarketAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = deleteReceiveProductsFromMarketAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def search = {
        ReceiveProductsFromMarket receiveProductsFromMarket = (ReceiveProductsFromMarket) searchReceiveProductsFromMarketAction.execute(params, null)
        if (receiveProductsFromMarket) {
            render receiveProductsFromMarket as JSON
        } else {
            render ''
        }
    }

    def listProduct = {
        ApplicationUser applicationUser = session?.applicationUser
        render listEnterpriseFinishProductAction.execute(applicationUser, params) as JSON
    }

    def popupProductListPanel = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = (List) listEnterpriseFinishProductAction.execute(applicationUser, params)
        render(view: '/receiveProductsFromMarket/popUpProductList', model: [aaData: list as JSON])
    }

    def listBatch = {
        render readInvoiceByCustomerAction.execute(1, params) as JSON
    }

    def listInvoice = {
        render readInvoiceByCustomerAction.execute(null, params) as JSON
    }

    def popupInvoiceListPanel = {
        List list = (List) readInvoiceByCustomerAction.execute(null,params)
        render(view: '/receiveProductsFromMarket/popUpInvoiceList', model: [aaData: list as JSON])
    }

    def checkIntegrity = {
        render readInvoiceByCustomerAction.postCondition(null, params) as JSON
    }

    def listWarehouse = {
        render listWarehouseByDpAction.execute(null, params) as JSON
    }

    def listSubWarehouse = {
        render listWarehouseByDpAction.execute(null, params) as JSON
    }
}
