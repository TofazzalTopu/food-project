package com.bits.bdfp.inventory.retailorder

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.customermaster.ListCustomerByGeoLocationAction
import com.bits.bdfp.customer.customermaster.ListDeliveryManByGeoLocationAction
import com.bits.bdfp.geolocation.territorysubarea.ListTerritorySubAreaByApplicationUserAction
import com.bits.bdfp.inventory.product.finishproduct.ListProductPriceByCustomerAction
import com.bits.bdfp.inventory.retailorder.retailorder.CreateRetailOrderAction
import com.bits.bdfp.inventory.retailorder.retailorder.DeleteRetailOrderAction
import com.bits.bdfp.inventory.retailorder.retailorder.DeleteRetailOrderDetailsAction
import com.bits.bdfp.inventory.retailorder.retailorder.ListRetailOrderAction
import com.bits.bdfp.inventory.retailorder.retailorder.ListRetailOrderDetailsAction
import com.bits.bdfp.inventory.retailorder.retailorder.ListRetailOrderNoAction
import com.bits.bdfp.inventory.retailorder.retailorder.SubmitRetailOrdersAction
import com.bits.bdfp.inventory.retailorder.retailorder.UpdateRetailOrderAction
import com.bits.bdfp.inventory.retailorder.retailorder.ReadRetailOrderAction
import com.bits.bdfp.inventory.retailorder.retailorder.SearchRetailOrderAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Message
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import com.docu.security.UserType
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class RetailOrderController {

    @Autowired
    private CreateRetailOrderAction createRetailOrderAction
    @Autowired
    private UpdateRetailOrderAction updateRetailOrderAction
    @Autowired
    private ListRetailOrderAction listRetailOrderAction
    @Autowired
    private DeleteRetailOrderAction deleteRetailOrderAction
    @Autowired
    private ReadRetailOrderAction readRetailOrderAction
    @Autowired
    private SearchRetailOrderAction searchRetailOrderAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListDeliveryManByGeoLocationAction listDeliveryManByGeoLocationAction
    @Autowired
    ListCustomerByGeoLocationAction listCustomerByGeoLocationAction
    @Autowired
    ListTerritorySubAreaByApplicationUserAction listTerritorySubAreaByApplicationUserAction
    @Autowired
    ListProductPriceByCustomerAction listProductPriceByCustomerAction
    @Autowired
    ListRetailOrderNoAction listRetailOrderNoAction
    @Autowired
    ListRetailOrderDetailsAction listRetailOrderDetailsAction
    @Autowired
    DeleteRetailOrderDetailsAction deleteRetailOrderDetailsAction
    @Autowired
    SubmitRetailOrdersAction submitRetailOrdersAction

    SpringSecurityService  springSecurityService

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        List list = (List) listRetailOrderAction.execute(params, null)
        render listRetailOrderAction.postCondition(null, list) as JSON
    }

    def show = {
        String deliveryManId = ""
        String deliveryMan = ""
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        UserType userType = applicationUser.userType
        if(userType && (userType.id == ApplicationConstants.USER_TYPE_OTHER || userType.id == ApplicationConstants.USER_TYPE_CUSTOMER) ){
            List listEnterprise = enterpriseAutocompleteListAction.execute(applicationUser, null)
            Map result = ["results": listEnterprise, "total": listEnterprise.size()]
            List subAreaList = (List) listTerritorySubAreaByApplicationUserAction.execute(params, null)
            if(userType.id == ApplicationConstants.USER_TYPE_CUSTOMER){
                CustomerMaster customerMaster = CustomerMaster.get(applicationUser.customerMasterId)
                if(customerMaster){
                    deliveryManId = customerMaster.id.toString()
                    deliveryMan = "[" + customerMaster.code + "] " + customerMaster.name
                }
            }
            render(view: 'show', model: [result:result as JSON, subAreaList: subAreaList as JSON, list: listEnterprise, userTypeId: userType.id, deliveryManId: deliveryManId, deliveryMan: deliveryMan])
        } else {
            render (view: "unAuthorized")
        }
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createRetailOrderAction.execute(params, applicationUser.id)
        render message as JSON
    }

    def edit = {
        RetailOrder retailOrder = (RetailOrder) readRetailOrderAction.execute(params, null)
        CustomerMaster deliveryMan = retailOrder.deliveryMan
        String orderDate = DateUtil.getDateFormatAsString(retailOrder.orderDate)
        String deliveryDate = DateUtil.getDateFormatAsString(retailOrder.deliveryDate)
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        UserType userType = applicationUser.userType
        if(userType && userType.id == ApplicationConstants.USER_TYPE_CUSTOMER ){
            render(view: 'update', model: [retailOrder: retailOrder, deliveryMan: deliveryMan, orderDate: orderDate, deliveryDate: deliveryDate])
        } else {
            render (template: "unAuthorized")
        }
    }

    def update = {
        Message message = updateRetailOrderAction.execute(params, null)
        render message as JSON
    }

    def delete = {
        Message message = deleteRetailOrderAction.execute(params, null)
        render message as JSON
    }

    def search = {
        render(view: 'search')
    }

    def listDeliveryMan = {
        List list = listDeliveryManByGeoLocationAction.execute(params, null)
        Map result = ["results": list, "total": list.size()]
        render result as JSON
    }

    def customerAutoComplete = {
        params.put("categoryId", ApplicationConstants.CUSTOMER_CATEGORY_RETAIL_ID)
        render listCustomerByGeoLocationAction.execute(params, null) as JSON
    }

    def popupCustomerListPanel = {
        List customerList = new ArrayList()
        params.put("categoryId", ApplicationConstants.CUSTOMER_CATEGORY_RETAIL_ID)
        customerList = (List) listCustomerByGeoLocationAction.execute(params, null)
        render(view: '/retailOrder/popupCustomerList', model: [aaData: customerList as JSON])
    }

    def popupProductListPanel = {
        render(view: '/retailOrder/popupProductList', model: ['customerId': params.customerId,'territorySubAreaId': params.territorySubAreaId])
    }

    def jsonProductListForRetailOrder = {
        List data = (List)listProductPriceByCustomerAction.listProductForRetailOrder(params, null)
        render data as JSON
    }
    def jsonProductList = {
        Map map = [:]
        List data = (List)listProductPriceByCustomerAction.execute(params, null)
        map.put("aaData", data)
        render map as JSON
    }
    def listProduct = {
        render listProductPriceByCustomerAction.listProductForRetailOrder(params, null) as JSON
    }

    def retailOrderAutoComplete = {
        render listRetailOrderNoAction.execute(params, null) as JSON
    }

    def listRetailOrderDetails = {
        List listUser = (List) listRetailOrderDetailsAction.execute(params, null)
        render listRetailOrderDetailsAction.postCondition(null, listUser) as JSON
    }

    def deleteRetailOrderDetails = {
        Message message = deleteRetailOrderDetailsAction.execute(params, null)
        render message as JSON
    }

    def submitOrders = {
        Message message= submitRetailOrdersAction.execute(params, null)
        render message as JSON
    }
}
