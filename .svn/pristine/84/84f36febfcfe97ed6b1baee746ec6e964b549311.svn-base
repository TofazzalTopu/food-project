package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.inventory.finishgood.CreateFinishGoodAction
import com.bits.bdfp.inventory.finishgood.FinishProductForReverseFinishGoodAction
import com.bits.bdfp.inventory.finishgood.ListFinishGoodInventoryInquiryAction
import com.bits.bdfp.inventory.finishgood.ListIssuedFinishGoodAction
import com.bits.bdfp.inventory.finishgood.OnHandQtyBatchListAction
import com.bits.bdfp.inventory.finishgood.OnHandQtyItemListAction
import com.bits.bdfp.inventory.finishgood.ProductAndWarehouseByEnterpriseAction
import com.bits.bdfp.inventory.finishgood.ProductListForFinishGoodAction
import com.bits.bdfp.inventory.finishgood.ReverseFinishGoodAction
import com.bits.bdfp.inventory.finishgood.SubWarehouseListByFactoryInventoryAction
import com.bits.bdfp.inventory.finishgood.SubwarehouseByWarehouseAction
import com.bits.bdfp.inventory.finishgood.TransProductRefFinishGoodAction
import com.bits.bdfp.inventory.sales.distributionpoint.ListDistributionPointByApplicationUser
import com.bits.bdfp.inventory.warehouse.warehouse.ListNonFactoryWarehouseByApplicationUserAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class FinishGoodWarehouseController {

    @Autowired
    ProductAndWarehouseByEnterpriseAction productAndWarehouseByEnterpriseAction
    @Autowired
    SubwarehouseByWarehouseAction subwarehouseByWarehouseAction
    @Autowired
    ProductListForFinishGoodAction productListForFinishGoodAction
    @Autowired
    CreateFinishGoodAction createFinishGoodAction
    Message message
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListFinishGoodInventoryInquiryAction listFinishGoodInventoryInquiryAction
    @Autowired
    OnHandQtyBatchListAction onHandQtyBatchListAction
    @Autowired
    OnHandQtyItemListAction onHandQtyItemListAction
    @Autowired
    TransProductRefFinishGoodAction transProductRefFinishGoodAction
    @Autowired
    FinishProductForReverseFinishGoodAction finishProductForReverseFinishGoodAction
    @Autowired
    ReverseFinishGoodAction reverseFinishGoodAction
    @Autowired
    ListDistributionPointByApplicationUser listDistributionPointByApplicationUser
    @Autowired
    ListIssuedFinishGoodAction listIssuedFinishGoodAction
    @Autowired
    ListNonFactoryWarehouseByApplicationUserAction listNonFactoryWarehouseByApplicationUserAction
    @Autowired
    SubWarehouseListByFactoryInventoryAction subWarehouseListByFactoryInventoryAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        List inventoryList = productAndWarehouseByEnterpriseAction.execute(null, applicationUser)
        Map inventoryMap = ["results": inventoryList, "total": inventoryList?.size()]
        render(template: "show", model: [inventoryResult: inventoryMap as JSON, inventoryList: inventoryList])
    }

    def selectSubwarehouse = {
        render subwarehouseByWarehouseAction.execute(params, null) as JSON
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        message = createFinishGoodAction.execute(params, applicationUser)
        render message as JSON
    }


    def productListForFinishGood = {
        ApplicationUser applicationUser = session?.applicationUser
        List list = productListForFinishGoodAction.execute(params, applicationUser)
        render list as JSON

    }

    def productListPopupForFinishGood = {
        ApplicationUser applicationUser = session?.applicationUser
        List productList = productListForFinishGoodAction.execute(params, applicationUser)
        render(view: 'popUpProductList', model: [aaData: productList as JSON])
    }

    def finishGoodInventoryInquiry = {
        Map result = [:]
        ApplicationUser applicationUser = session?.applicationUser
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        List subWarehouseList = subWarehouseListByFactoryInventoryAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size() > 0) {
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(view: 'finishGoodInventoryInquiry', model: [list: enterpriseList, result: result as JSON,
                                                           subWarehouseList:subWarehouseList
                                                           ])
    }

    def finishGoodInquiryList = {
        ApplicationUser applicationUser = session?.applicationUser
        render listFinishGoodInventoryInquiryAction.execute(params, applicationUser) as JSON

    }

    def onHandQtyBatchList = {
        ApplicationUser applicationUser = session?.applicationUser
        render onHandQtyBatchListAction.execute(params, applicationUser) as JSON

    }
    def onHandQtyItemList = {
        ApplicationUser applicationUser = session?.applicationUser
        render onHandQtyItemListAction.execute(params, applicationUser) as JSON

    }
    def issuedQuantityItemList = {
        ApplicationUser applicationUser = session?.applicationUser
        render listIssuedFinishGoodAction.execute(params, applicationUser) as JSON
    }

    def reverseFinishGoodShow = {
        render(template: "reverseShow")
    }

    def transProductReferenceAutoComplete = {
        List list = transProductRefFinishGoodAction.execute(params, null)
        render list as JSON
    }

    def transProductReferenceForPopup = {
        List list = transProductRefFinishGoodAction.execute(params, null)
        render(view: 'popUpProductListReverseGood', model: [aaData: list as JSON])

    }
    def productListForReverseFinishGood = {
        Map map = finishProductForReverseFinishGoodAction.execute(params, null)
        render map as JSON
    }

    def reverseFinishGood = {
        ApplicationUser applicationUser = session?.applicationUser
        message = reverseFinishGoodAction.execute(params, applicationUser)
        render message as JSON
    }
    def viewDistributionPointStock = {
        Map result = [:]
        Map dpList = [:]
        ApplicationUser applicationUser = session?.applicationUser
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size() > 0) {
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }

        List warehouseList =  (List) listNonFactoryWarehouseByApplicationUserAction.execute(params, null)
//        render(template: "secondaryOrderShow", model: [warehouseList: warehouseList as JSON, warehouseCount: warehouseList.size()])

        render(template: "finishGoodInventoryInquiryByDistributionPoint", model: [list: enterpriseList, size: enterpriseList.size(), warehouseList: warehouseList as JSON, warehouseCount: warehouseList.size(), result: result as JSON])
    }

    def issuedQuantityItemListByDp = {
        ApplicationUser applicationUser = session?.applicationUser
        render listFinishGoodInventoryInquiryAction.getIssuedItemsByDistributionPoint(params, applicationUser) as JSON
    }
}
