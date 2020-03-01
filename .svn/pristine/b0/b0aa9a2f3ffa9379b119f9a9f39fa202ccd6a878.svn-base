package com.bits.bdfp.rest

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.customermaster.ListCustomerByGeoLocationAction
import com.bits.bdfp.finance.customerpayment.GetCustomerBalanceAction
import com.bits.bdfp.inventory.product.finishproduct.ListProductPriceByCustomerAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.bits.bdfp.util.ApplicationConstants
import com.docu.security.ApplicationUser
import com.docu.security.UserType
import grails.converters.JSON
import grails.plugins.springsecurity.SpringSecurityService
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired

class ServiceController {
    SpringSecurityService springSecurityService
    @Autowired
    ListGeoLocationByApplicationUserAction listGeoLocationByApplicationUserAction
    @Autowired
    ListCustomerByGeoLocationAction listCustomerByGeoLocationAction
    @Autowired
    ListProductPriceByCustomerAction listProductPriceByCustomerAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    CreateRetailOrderByMobileAction createRetailOrderByMobileAction
    @Autowired
    CreateSecondaryOrderFromRetailOrderByMobileAction createSecondaryOrderFromRetailOrderByMobileAction
    @Autowired
    ListProductForCheckAllQuantityAction listProductForCheckAllQuantityAction
    @Autowired
    AutoProcessRetailOrderByMobileAction autoProcessRetailOrderByMobileAction
    @Autowired
    ManualProcessRetailOrderByMobileAction manualProcessRetailOrderByMobileAction
    @Autowired
    ListNonPaidRetailInvoiceByMobileAction listNonPaidRetailInvoiceByMobileAction
    @Autowired
    GetCustomerBalanceAction getCustomerBalanceAction
    @Autowired
    ApplyCashCollectionForInvoiceByMobileAction applyCashCollectionForInvoiceByMobileAction
    @Autowired
    ListCustomerStockForMobileAction listCustomerStockForMobileAction

    def login = {
        String username = params.username
        String password = params.password

//        String hName = request.getHeader("hName")
//        String hPassword = request.getHeader("hPassword")

        ApplicationUser applicationUser = ApplicationUser.findByUsernameAndPassword(username,springSecurityService.encodePassword(password))
        UserType userType = applicationUser?applicationUser.userType:null

        Map map = [:]

        if(applicationUser){
            if(applicationUser.deviceId != params.deviceId){
                map.success = false
                map.data = "You are not authorized user for this device."
                map.count = 0
            }else if(userType && (userType.id == ApplicationConstants.USER_TYPE_OTHER || userType.id == ApplicationConstants.USER_TYPE_CUSTOMER) ){
                map = ["success":applicationUser?true:false,"data":applicationUser?[id:applicationUser.id, username:applicationUser.username, customerMasterId:applicationUser.customerMasterId?applicationUser.customerMasterId:'N/A', customerMasterCode:applicationUser.customerMasterId?CustomerMaster.read(applicationUser.customerMasterId).code:'N/A', fullName:applicationUser.fullName]:'User not found.',"count":applicationUser?1:0]
            }else{
                map.success = false
                map.data = "You are not authorized user."
                map.count = 0
            }
        }else{
            map.success = false
            map.data = "User not found."
            map.count = 0
        }

        render map as JSON
    }

    def sync = {
        try{
            List geoList
            Map map = [:]

            params.put("categoryId", ApplicationConstants.CUSTOMER_CATEGORY_RETAIL_ID)

            ApplicationUser applicationUser = ApplicationUser.findByUsername(params.username?params.username:"")

            if(applicationUser){
                UserType userType = applicationUser.userType
                if(applicationUser.deviceId != params.deviceId){
                    map.success = false
                    map.data = "You are not authorized user for this device."
                    map.count = 0
                }else if(userType && (userType.id == ApplicationConstants.USER_TYPE_OTHER || userType.id == ApplicationConstants.USER_TYPE_CUSTOMER) ){
                    Map dataMapped = [:]
                    geoList = (List) listGeoLocationByApplicationUserAction.execute(params, applicationUser)

                    if(geoList.size()>0){
                        geoList.each {
                            Map cMap = [:]
                            params.put("territorySubAreaId",it.id)
                            List cList = (List) listCustomerByGeoLocationAction.execute(params, null)

                            if(cList.size()>0){
                                cList.each {
                                    Map pMap = [:]
                                    params.put("customerId",it.id)
                                    params.put("territorySubAreaId",it.territorySubAreaId)
                                    List pList = (List)listProductPriceByCustomerAction.execute(params, null)
                                    if(pList.size()>0){
                                        pList.each {
                                            it.remove("code")
                                        }
                                        pMap.put("productList",pList)
                                        pMap.put("totalProduct",pList.size())
                                        it.putAll(pMap)
                                    }
                                }

                                cMap.put("customerList",cList)
                                cMap.put("totalCustomer",cList.size())
                                it.putAll(cMap)
                            }
                        }

                        dataMapped.put("geoList",geoList)
                        dataMapped.put("totalGeoLocation",geoList.size())
                    }

                    if(dataMapped.size()>0){
                        map.put("success",true)
                        map.put("data",dataMapped)
                        map.put("syncDate",new Date().format("dd-MM-yyyy hh:mm:ss a"))
                    }else{
                        map.put("success",false)
                        map.put("data","No data found.")
                    }

                }else{
                    map.success = false
                    map.data = "You are not authorized user."
                    map.count = 0
                }
            }else{
                map.put("success",false)
                map.put("data","Invalid user.")
            }

            render map as JSON

        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    def createRetailOrder = {
        JSONArray orderList = new JSONArray(params.orders)
//        JSONObject orderList = (JSONObject)params.orders
//        JSONArray jsonArray = orderList.getJSONArray(orderList.orders)
        List finalMap = []

        if (orderList.size()>0){
            orderList.each {
                ApplicationUser applicationUser = ApplicationUser.findByUsername(it.username?it.username:"")
                Map map = [:]
                if(applicationUser){
                    UserType userType = applicationUser.userType
                    if(applicationUser.deviceId != params.deviceId){
                        map.success = false
                        map.data = "You are not authorized user for this device."
                    }else if(userType && (userType.id == ApplicationConstants.USER_TYPE_OTHER || userType.id == ApplicationConstants.USER_TYPE_CUSTOMER) ){
                        List listEnterprise = enterpriseAutocompleteListAction.execute(applicationUser, null)
                        params.put("enterprise.id",listEnterprise.first().id)
                        params.put("isSubmitted",true)
                        params.put("orderPlacedFor.id",it.customerId)
                        params.put("territorySubArea.id",it.geoId)
                        params.put("orderDate",new Date())
                        params.put("deliveryDate",it.deliveryDate)
                        List list = []
                        it.items.each{
                            Map pMap = [:]
                            pMap.put("finishProduct.id",it.productId)
                            pMap.put("rate",it.rate)
                            pMap.put("quantity",it.quantity)
                            list.add(pMap)
                        }
                        params.put("items",list)
                        if(userType.id == ApplicationConstants.USER_TYPE_CUSTOMER){
                            CustomerMaster customerMaster = CustomerMaster.get(applicationUser.customerMasterId)
                            if(customerMaster){
                                params.put("deliveryMan.id",customerMaster.id)
                            }
                        }
                        Map result = createRetailOrderByMobileAction.execute(params, applicationUser)
                        if(result.message.type == 1){
                            map.put("success",true)
                            map.put("message",result.message.messageBody[0])
                            map.put("orderNo", result.orderNo)
                            map.put("orderId", it.orderId)
                        }else{
                            map.put("success",false)
                            map.put("message",result.message.messageBody[0])
                            map.put("orderId", it.orderId)
                        }
                    }else{
                        map.success = false
                        map.data = "You are not authorized user."
                    }
                }else{
                    map.success = false
                    map.data = "User not found."
                }
                finalMap.add(map)
            }
        }else{
            Map mapNo = [:]
            mapNo.success = false
            mapNo.data = "No data found."
            mapNo.count = 0
            finalMap.add(mapNo)
        }
        render finalMap as JSON
    }

    def createSecondaryOrder = {
        JSONObject dataObject = new JSONObject(params.data)
        ApplicationUser applicationUser = ApplicationUser.findByUsername(dataObject.username?dataObject.username:"")
        Map map = [:]
        if(applicationUser) {
            if (applicationUser.deviceId != params.deviceId) {
                map.success = false
                map.data = "You are not authorized user for this device."
                map.count = 0
            } else {
                params.put("isSubmitted", true)
                params.put("customerMaster.id", dataObject.customerMasterId)
                params.put("userTentativeDelivery.id", dataObject.userTentativeDeliveryId)
                params.put("territorySubArea.id", dataObject.geoId)
                params.put("dateOrder", new Date().format("dd-MM-yyyy"))
                params.put("dateDeliver", dataObject.dateDeliver)
                params.put("retailOrderNumbers", dataObject.retailOrderNumbers)
                List list = []
                dataObject.items.each {
                    Map pMap = [:]
                    pMap.put("finishProduct.id", it.productId)
                    pMap.put("rate", it.rate)
                    pMap.put("quantity", it.quantity)
                    list.add(pMap)
                }
                params.put("items", list)
                Map result = createSecondaryOrderFromRetailOrderByMobileAction.execute(params, applicationUser)
                if (result.message.type == 1) {
                    map.put("success", true)
                    map.put("message", result.message.messageBody[0])
                    map.put("orderNo", result.orderNo)
                } else {
                    map.put("success", false)
                    map.put("message", result.message.messageBody[0])
                }
            }
        }else{
            map.success = false
            map.data = "User not found."
            map.count = 0
        }

        render map as JSON
    }

    def checkAllQuantity = {
        ApplicationUser applicationUser = ApplicationUser.findByUsername(params.username?params.username:"")
        Map map = [:]
        if(applicationUser){
            if(applicationUser.deviceId != params.deviceId){
                map.success = false
                map.data = "You are not authorized user for this device."
                map.count = 0
            }else{
                List list = listProductForCheckAllQuantityAction.execute(params,applicationUser)
                if(list.size()>0){
                    list.each {
                        it.remove("productCode")
                    }
                    map.put("success",true)
                    map.put("data",list)
                    map.put("count",list.size())
                }else{
                    map.put("success",false)
                    map.put("data","No items found.")
                    map.put("count",0)
                }
            }
        }else{
            map.put("success",false)
            map.put("data","User not found.")
        }

        render map as JSON
    }

    def processAutomatically = {
        ApplicationUser applicationUser = ApplicationUser.findByUsername(params.username?params.username:"")
        Map map = [:]
        if(applicationUser){
            if(applicationUser.deviceId != params.deviceId){
                map.success = false
                map.data = "You are not authorized user for this device."
                map.count = 0
            }else{
                List list = autoProcessRetailOrderByMobileAction.execute(params,applicationUser)
                if(list.size()>0){
                    map.put("data",list)
                    map.put("count",list.size())
                }else{
                    map.put("success",false)
                    map.put("data","No items found.")
                    map.put("count",0)
                }
            }
        }else{
            map.put("success",false)
            map.put("data","User not found.")
        }

        render map as JSON
    }

    def getCustomerStock = {
        ApplicationUser applicationUser = ApplicationUser.findByUsername(params.username?params.username:"")
        Map map = [:]
        if(applicationUser){
            if(applicationUser.deviceId != params.deviceId){
                map.success = false
                map.data = "You are not authorized user for this device."
            }else{
                params.put("deliveryManId",applicationUser.customerMasterId)
                List stockList = listCustomerStockForMobileAction.execute(params,null)
                if(stockList.size()>0){
                    stockList.each {
                        it.remove("productCode")
                    }
                    map.put("success",true)
                    map.put("data",stockList)
                    map.put("count",stockList.size())
                }else{
                    map.put("success",false)
                    map.put("data","No items found.")
                }
            }
        }else{
            map.put("success",false)
            map.put("data","User not found.")
        }

        render map as JSON
    }

    def processManually = {
        JSONObject dataObject = new JSONObject(params.product)
        ApplicationUser applicationUser = ApplicationUser.findByUsername(params.username?params.username:"")
        Map map = [:]
        if(applicationUser){
            if(applicationUser.deviceId != params.deviceId){
                map.success = false
                map.data = "You are not authorized user for this device."
            }else{
                List list = []
                dataObject.items.each{
                    Map pMap = [:]
                    pMap.put("productId",it.productId)
                    pMap.put("rate",it.rate)
                    pMap.put("quantity",it.quantity)
                    list.add(pMap)
                }
                params.put("items",list)
                Map mapResult = manualProcessRetailOrderByMobileAction.execute(params,applicationUser)
                if(mapResult){
                    map = mapResult
                }else{
                    map.put("success",false)
                    map.put("data","No items found.")
                }
            }
        }else{
            map.put("success",false)
            map.put("data","User not found.")
        }

        render map as JSON
    }

    def getCustomerInvoiceListAndBalance = {
        ApplicationUser applicationUser = ApplicationUser.findByUsername(params.username?params.username:"")
        Map map = [:]
        if(applicationUser){
            if(applicationUser.deviceId != params.deviceId){
                map.success = false
                map.data = "You are not authorized user for this device."
            }else{
                List invoiceList = listNonPaidRetailInvoiceByMobileAction.execute(params, null)
                double  balance = (Double) getCustomerBalanceAction.execute(params, null)
                if(invoiceList.size()>0){
                    map.put("success",true)
                }else{
                    map.put("success",false)
                    map.put("message","No invoice found.")
                }
                map.put("customerBalance",balance)
                map.put("invoiceList",invoiceList)
                map.put("totalInvoice",invoiceList.size())
            }
        }else{
            map.put("success",false)
            map.put("message","User not found.")
        }

        render map as JSON
    }

    def collectCash = {
        ApplicationUser applicationUser = ApplicationUser.findByUsername(params.username?params.username:"")
        Map map = [:]
        if(applicationUser){
            if(applicationUser.deviceId != params.deviceId){
                map.success = false
                map.data = "You are not authorized user for this device."
            }else{
                List invoiceList = []
                JSONObject dataObject = new JSONObject(params.invoiceList)
                dataObject.invoiceList.each{
                    Double amountToBeReceived = new Double(it.amountToBeReceived)
                    if(amountToBeReceived > 0.00){
                        Map iMap =[:]
                        iMap.put("invoiceId", new Long(it.invoiceId))
                        iMap.put("amountToBeReceived", amountToBeReceived)
                        invoiceList.add(iMap)
                    }
                }
                params.put("invoiceList",invoiceList)
                Map result = applyCashCollectionForInvoiceByMobileAction.execute(params,applicationUser)
                map = result
            }
        }else{
            map.put("success",false)
            map.put("message","User not found.")
        }

        render map as JSON
    }
}
