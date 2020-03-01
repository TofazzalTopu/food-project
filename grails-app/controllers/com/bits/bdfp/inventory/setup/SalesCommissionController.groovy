package com.bits.bdfp.inventory.setup

import com.bits.bdfp.inventory.sales.processmarketreturn.ListCustomerByDpAction
import com.bits.bdfp.inventory.setup.salescommission.AdjustSalesCommissionAction
import com.bits.bdfp.inventory.setup.salescommission.CreateSalesCommissionAction
import com.bits.bdfp.inventory.setup.salescommission.CreateProductSalesCommissionAction
import com.bits.bdfp.inventory.setup.salescommission.FetchCommissionForCustomerAction
import com.bits.bdfp.inventory.setup.salescommission.FetchDataForDistributionPointDropDownAction
import com.bits.bdfp.inventory.setup.salescommission.FetchDataProductGridAction
import com.bits.bdfp.inventory.setup.salescommission.ListBranchCommissionAmountAction
import com.bits.bdfp.inventory.setup.salescommission.ListCustomerSalesCommissionAction
import com.bits.bdfp.inventory.setup.salescommission.ListCustomersCommissionAmountAction
import com.bits.bdfp.inventory.setup.salescommission.ListSalesCommissionAction
import com.bits.bdfp.inventory.setup.salescommission.ListCustomerSalesCommissionForUpdateAction
import com.bits.bdfp.inventory.setup.salescommission.ListSalesCommissionByDpAction
import com.bits.bdfp.inventory.setup.salescommission.ListProductByCustomerSalesCommissionForUpdateAction
import com.bits.bdfp.inventory.setup.salescommission.UpdateCustomerSalesCommissionEffectiveDate
import com.bits.bdfp.inventory.setup.salescommission.ListAllProductByAction
import com.bits.bdfp.inventory.setup.salescommission.DeleteCheckSalesCommissionAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser

import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by mddelower.hossain on 19-Jan-2016.
 */
class SalesCommissionController {
    @Autowired
    private CreateSalesCommissionAction createSalesCommissionAction
    @Autowired
    FetchDataForDistributionPointDropDownAction fetchDataForDistributionPointDropDownAction
    @Autowired
    ListSalesCommissionAction listSalesCommissionAction
    @Autowired
    ListCustomerSalesCommissionAction listCustomerSalesCommissionAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListCustomerByDpAction listCustomerByDpAction
    @Autowired
    FetchCommissionForCustomerAction fetchCommissionForCustomerAction
    @Autowired
    AdjustSalesCommissionAction adjustSalesCommissionAction
    @Autowired
    ListCustomersCommissionAmountAction listCustomersCommissionAmountAction
    @Autowired
    ListBranchCommissionAmountAction listBranchCommissionAmountAction
    @Autowired
    ListCustomerSalesCommissionForUpdateAction listCustomerSalesCommissionForUpdateAction
    @Autowired
    ListProductByCustomerSalesCommissionForUpdateAction listProductByCustomerSalesCommissionForUpdateAction
    ///new 2/6/17
    @Autowired
    ListSalesCommissionByDpAction listSalesCommissionByDpAction
    @Autowired
    CreateProductSalesCommissionAction createProductSalesCommissionAction
    @Autowired
    UpdateCustomerSalesCommissionEffectiveDate updateCustomerSalesCommissionEffectiveDate
    @Autowired
    DeleteCheckSalesCommissionAction deleteCheckSalesCommissionAction
    @Autowired
    FetchDataProductGridAction fetchDataProductGridAction
    @Autowired
    ListAllProductByAction listAllProductByAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
//        List list = listSalesCommissionAction.execute(params, null)
//        render listSalesCommissionAction.postCondition(null, list) as JSON
    }

    def show = {
        SalesCommission salesCommission = new SalesCommission()
        render(template: "show", model: [salesCommission: salesCommission])
    }

    def loadProductGrid = {
        List productList = fetchDataProductGridAction.execute(params, null)
        render fetchDataProductGridAction.postCondition(null, productList) as JSON
    }


    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = createSalesCommissionAction.execute(applicationUser, params)
        render message as JSON
    }

    def fetchDPDropdownList = {
        List list = fetchDataForDistributionPointDropDownAction.execute(params, null)
        render list as JSON
    }
    def fetchUserTerritoryList = {
        List territoryList = fetchDataForDistributionPointDropDownAction.fetchUserTerritoryList(params, null)
        render territoryList as JSON
    }


    def listDefaultCustomerByDP = {
        List list = listSalesCommissionAction.execute(params, null)
        render list as JSON
    }

    def listCustomerByDP = {
        Map list = (Map)listCustomerSalesCommissionAction.execute(params, null)
        render list as JSON
    }

    def listCustomersCommissionByDP = {
        List list = listCustomersCommissionAmountAction.execute(params, null)
        render list as JSON
    }

    def listBranchCommissionByDP = {
        List list = listBranchCommissionAmountAction.execute(params, null)
        render list as JSON
    }

    def showAdjustCommission = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = enterpriseAutocompleteListAction.execute(applicationUser, null)
        String currentDate = DateUtil.getCurrentDateFormatAsString()
        Map result = ["results": list, "total": list.size()]
        render(view: "showAdjust", model: [result: result as JSON, list: list, currentDate: currentDate])
    }

    def fetchCustomer = {
        render listCustomerByDpAction.execute(params, null) as JSON
    }

    def popupCustomerListPanel = {
        List customerList = (List) listCustomerByDpAction.execute(params, null)
        render(view: '/invoice/popUpCustomerListByCategoryAndGeo', model: [aaData: customerList as JSON])
    }

    def fetchCommission = {
        render fetchCommissionForCustomerAction.execute(params, null)
    }

    def adjustSalesCommission = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = adjustSalesCommissionAction.execute(applicationUser, params)
        render message as JSON
    }

    def viewCommission = {
        List territoryList = fetchDataForDistributionPointDropDownAction.fetchUserTerritoryList(params, null)
        List dpList = fetchDataForDistributionPointDropDownAction.fetchDistributionPointList(params, null)
        render(template: "showCommission", model: [ territoryList:territoryList as JSON, territoryListSize:territoryList? territoryList.size():0, dpList:dpList as JSON, dpSize:dpList? dpList.size():0])
    }

    def showEditCommission ={
        render(template: "editSalesCommission/show")
    }
    ///new 2/6/17
    def listSalesCommissionByDP = {
        Map list = (Map)listSalesCommissionByDpAction.execute(params, null)
        render list as JSON
    }
    def listCustomerBySalesCommissionDP = {
        Map list = (Map)listCustomerSalesCommissionForUpdateAction.execute(params, null)
        render list as JSON
    }
    def updateProductSalesCommissionPopup = {
        List productList = listAllProductByAction.execute(params,null)
        render(template: "editSalesCommission/updateProductSalesCommission",model: [list: productList, params: params])
    }
    def updateProductSalesCommissionDetails = {
        Map result = listProductByCustomerSalesCommissionForUpdateAction.execute(params, null)
        render result as JSON
//        render listProductByCustomerSalesCommissionForUpdateAction.execute(params, null) as JSON
    }

    def createProductSalesCommission = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = createProductSalesCommissionAction.execute(applicationUser, params)
        render message as JSON
    }
    def deleteProduct = {
       render  createProductSalesCommissionAction.executeDelete(params, null) as JSON
    }
    def deleteCustomerSalesCommission = {
        render  createProductSalesCommissionAction.executeDeleteCustomerSalesCommission(params, null) as JSON
    }

    def updateDateForParticularCustomer = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message =  updateCustomerSalesCommissionEffectiveDate.execute(applicationUser, params)
        render message as JSON
    }
    def deleteCheckSalesCommission = {
        render  deleteCheckSalesCommissionAction.checkSalesCommissionCustomerAssosiation(params, null) as JSON
    }
}

