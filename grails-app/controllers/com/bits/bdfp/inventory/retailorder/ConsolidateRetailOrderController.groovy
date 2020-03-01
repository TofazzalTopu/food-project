package com.bits.bdfp.inventory.retailorder

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.customermaster.ReadCustomerMasterAction
import com.bits.bdfp.geolocation.TerritorySubArea
import com.bits.bdfp.geolocation.territorysubarea.ListTerritorySubAreaByApplicationUserAction
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder
import com.bits.bdfp.inventory.demandorder.secondarydemandorder.ReadSecondaryDemandOrderAction
import com.bits.bdfp.inventory.retailorder.consolidateretailorder.CreateSecondaryOrderFromRetailOrderAction
import com.bits.bdfp.inventory.retailorder.consolidateretailorder.DeleteMultipleRetailOrderAction
import com.bits.bdfp.inventory.retailorder.consolidateretailorder.DeleteSecondaryOrderAction
import com.bits.bdfp.inventory.retailorder.consolidateretailorder.DeleteSecondaryOrderDetailsAction
import com.bits.bdfp.inventory.retailorder.consolidateretailorder.ListRetailOrderDetailsForConsolidateAction
import com.bits.bdfp.inventory.retailorder.consolidateretailorder.ListRetailOrderForConsolidateAction
import com.bits.bdfp.inventory.retailorder.consolidateretailorder.ListSecondaryOrderAction
import com.bits.bdfp.inventory.retailorder.consolidateretailorder.ListSecondaryOrderDetailsAction
import com.bits.bdfp.inventory.retailorder.consolidateretailorder.ListSecondaryOrderDetailsFromRetailOrderAction
import com.bits.bdfp.inventory.retailorder.consolidateretailorder.ListSecondaryOrderNoAction
import com.bits.bdfp.inventory.retailorder.consolidateretailorder.SubmitSecondaryOrdersAction
import com.bits.bdfp.inventory.retailorder.consolidateretailorder.UpdateSecondaryOrderCreatedFromRetailOrderAction
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Message
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import com.docu.security.UserType
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class ConsolidateRetailOrderController {

    SpringSecurityService  springSecurityService
    @Autowired
    ListRetailOrderForConsolidateAction listRetailOrderForConsolidateAction
    @Autowired
    ListRetailOrderDetailsForConsolidateAction listRetailOrderDetailsForConsolidateAction
    @Autowired
    ReadCustomerMasterAction readCustomerMasterAction
    @Autowired
    ListTerritorySubAreaByApplicationUserAction listTerritorySubAreaByApplicationUserAction
    @Autowired
    ListSecondaryOrderDetailsFromRetailOrderAction listSecondaryOrderDetailsFromRetailOrderAction
    @Autowired
    CreateSecondaryOrderFromRetailOrderAction createSecondaryOrderFromRetailOrderAction
    @Autowired
    DeleteMultipleRetailOrderAction deleteMultipleRetailOrderAction
    @Autowired
    ListSecondaryOrderAction listSecondaryOrderAction
    @Autowired
    ListSecondaryOrderNoAction listSecondaryOrderNoAction
    @Autowired
    SubmitSecondaryOrdersAction submitSecondaryOrdersAction
    @Autowired
    ReadSecondaryDemandOrderAction readSecondaryDemandOrderAction
    @Autowired
    ListSecondaryOrderDetailsAction listSecondaryOrderDetailsAction
    @Autowired
    DeleteSecondaryOrderDetailsAction deleteSecondaryOrderDetailsAction
    @Autowired
    UpdateSecondaryOrderCreatedFromRetailOrderAction updateSecondaryOrderCreatedFromRetailOrderAction
    @Autowired
    DeleteSecondaryOrderAction deleteSecondaryOrderAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        List list = (List) listSecondaryOrderAction.execute(params, null)
        render listSecondaryOrderAction.postCondition(null, list) as JSON
    }

    def listOrderForConsolidate = {
        render listRetailOrderForConsolidateAction.execute(params, null) as JSON
    }

    def listOrderDetailsForConsolidate = {
        render listRetailOrderDetailsForConsolidateAction.execute(params, null) as JSON
    }

    def show = {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        UserType userType = applicationUser.userType
        if(userType && userType.id == ApplicationConstants.USER_TYPE_CUSTOMER ){
            params.put('id', applicationUser.customerMasterId.toString())
            CustomerMaster customerMaster = (CustomerMaster)readCustomerMasterAction.execute(params, null)
            List subAreaList = (List) listTerritorySubAreaByApplicationUserAction.execute(params, null)
            int suAreaCount = subAreaList.size()
            render(view: '/retailOrder/consolidateRetailOrder/show', model: [customerMaster: customerMaster, suAreaCount: suAreaCount, subAreaList: subAreaList])
            return
        }else{
            render(view: '/retailOrder/consolidateRetailOrder/unAuthorized')
        }
    }

    def deleteMultipleRetailOrder = {
        Message message = deleteMultipleRetailOrderAction.execute(params, null)
        render message as JSON
    }

    def generateSecondaryDetailsFromRetailOrders = {
        render listSecondaryOrderDetailsFromRetailOrderAction.execute(params, null) as JSON
    }

    def createSecondaryOrder = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createSecondaryOrderFromRetailOrderAction.execute(params, applicationUser)
        render message as JSON
    }

    def updateSecondaryOrder = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateSecondaryOrderCreatedFromRetailOrderAction.execute(params, applicationUser)
        render message as JSON
    }

    def searchSecondaryOrder = {
        render(view: '/retailOrder/consolidateRetailOrder/search')
    }

    def secondaryOrderAutoComplete = {
        render listSecondaryOrderNoAction.execute(params, null) as JSON
    }

    def edit = {
        SecondaryDemandOrder secondaryDemandOrder = (SecondaryDemandOrder) readSecondaryDemandOrderAction.execute(params, null)
        String orderDate = DateUtil.getDateFormatAsString(secondaryDemandOrder.dateOrder)
        String deliveryDate = DateUtil.getDateFormatAsString(secondaryDemandOrder.dateDeliver)
        CustomerMaster customerMaster = secondaryDemandOrder.customerMaster
        CustomerMaster userTentativeDelivery = secondaryDemandOrder.userTentativeDelivery
        TerritorySubArea territorySubArea = secondaryDemandOrder.territorySubArea
        ApplicationUser userOrderPlaced = secondaryDemandOrder.userOrderPlaced
        render(view: '/retailOrder/consolidateRetailOrder/update', model: [secondaryDemandOrder: secondaryDemandOrder, customerMaster: customerMaster, userTentativeDelivery: userTentativeDelivery, territorySubArea: territorySubArea, orderDate: orderDate, deliveryDate: deliveryDate, userOrderPlaced: userOrderPlaced])
    }

    def listSecondaryOrderDetails = {
        List listSecondaryOrderDetails = (List) listSecondaryOrderDetailsAction.execute(params, null)
        render listSecondaryOrderDetailsAction.postCondition(null, listSecondaryOrderDetails) as JSON
    }

    def deleteSecondaryOrderDetails = {
        Message message = deleteSecondaryOrderDetailsAction.execute(params, null)
        render message as JSON
    }

    def deleteSecondaryOrder = {
        Message message = deleteSecondaryOrderAction.execute(params, null)
        render message as JSON
    }

    def submitSecondaryOrders = {
        Message message= submitSecondaryOrdersAction.execute(params, null)
        render message as JSON
    }
}
