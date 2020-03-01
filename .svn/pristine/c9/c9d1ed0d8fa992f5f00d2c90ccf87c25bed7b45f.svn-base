package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.warehouse.miscellaneoustransactions.CreateDamageMiscellaneousTransactionsAction
import com.bits.bdfp.inventory.warehouse.miscellaneoustransactions.CreateEntertainmentMiscellaneousTransactionsAction
import com.bits.bdfp.inventory.warehouse.miscellaneoustransactions.CreateReplacementMiscellaneousTransactionsAction
import com.bits.bdfp.inventory.warehouse.miscellaneoustransactions.CreateRtpMiscellaneousTransactionsAction
import com.bits.bdfp.inventory.warehouse.miscellaneoustransactions.CreateSampleMiscellaneousTransactionsAction
import com.bits.bdfp.inventory.warehouse.miscellaneoustransactions.UtilMiscellaneousTransactionsAction
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.apache.poi.util.LongField
import org.springframework.beans.factory.annotation.Autowired

class MiscellaneousTransactionsController {
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static  defaultAction = "show"
    @Autowired
    UtilMiscellaneousTransactionsAction utilMiscellaneousTransactionsAction
    @Autowired
    CreateReplacementMiscellaneousTransactionsAction createReplacementMiscellaneousTransactionsAction
    @Autowired
    CreateEntertainmentMiscellaneousTransactionsAction createEntertainmentMiscellaneousTransactionsAction
    @Autowired
    CreateSampleMiscellaneousTransactionsAction createSampleMiscellaneousTransactionsAction
    @Autowired
    CreateDamageMiscellaneousTransactionsAction createDamageMiscellaneousTransactionsAction
    @Autowired
    CreateRtpMiscellaneousTransactionsAction createRtpMiscellaneousTransactionsAction

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        List dpList = utilMiscellaneousTransactionsAction.getDpListByApplicationUser(applicationUser.id)
        List factoryDpList = utilMiscellaneousTransactionsAction.getFactoryDpListByApplicationUser(applicationUser.id)
        List<CustomerMaster> customerMasterList = CustomerMaster.list()
        Map mapCustomer = [:]
        mapCustomer.put("results",customerMasterList)
        mapCustomer.put("total",customerMasterList.size())
        render(view: "show", model: [dpList:dpList as JSON, dpSize: dpList? dpList.size(): 0, factoryDpList:factoryDpList as JSON , factoryDpSize: factoryDpList? factoryDpList.size(): 0, customers:mapCustomer as JSON])
    }

    def getAllCustomerListByKey = {
        List customerList =  utilMiscellaneousTransactionsAction.getAllCustomerListByKey(params.query)
        if(params.query){
            render customerList as JSON
        }else{
            render(view: 'popUpCustomerList', model: [aaData: customerList as JSON])
        }
    }

    def getCustomerListBySelectedDp = {
        render utilMiscellaneousTransactionsAction.getCustomerListBySelectedDp(Long.parseLong(params.dpId),params.query) as JSON
    }

    def getCustomerCodeById = {
        render utilMiscellaneousTransactionsAction.getCustomerCodeById(Long.parseLong(params.customerId)) as JSON
    }

    def getMrListByCustomerId = {
        render utilMiscellaneousTransactionsAction.getMrListByCustomerId(Long.parseLong(params.customerId)) as JSON
    }

    def getMarketReturnSummaryByMrId = {
        render utilMiscellaneousTransactionsAction.getMarketReturnSummaryByMrId(Long.parseLong(params.mrId)) as JSON
    }

    def getInventoryListByDp = {
        render utilMiscellaneousTransactionsAction.getInventoryListByDp(Long.parseLong(params.dpId)) as JSON
    }

    def getSubInventoryListByInventory = {
        render utilMiscellaneousTransactionsAction.getSubInventoryListByInventory(Long.parseLong(params.inventoryId)) as JSON
    }

    def getProductListBySubInventory = {
        render utilMiscellaneousTransactionsAction.getProductListBySubInventory(Long.parseLong(params.dpId),Long.parseLong(params.subInventoryId), params.query) as JSON
    }

    def createReplacement ={
        ApplicationUser applicationUser = session?.applicationUser
        render createReplacementMiscellaneousTransactionsAction.execute(params,applicationUser) as JSON
    }

    def createEntertainment ={
        ApplicationUser applicationUser = session?.applicationUser
        render createEntertainmentMiscellaneousTransactionsAction.execute(params,applicationUser) as JSON
    }

    def createSample ={
        ApplicationUser applicationUser = session?.applicationUser
        render createSampleMiscellaneousTransactionsAction.execute(params,applicationUser) as JSON
    }

    def createDamage ={
        ApplicationUser applicationUser = session?.applicationUser
        render createDamageMiscellaneousTransactionsAction.execute(params,applicationUser) as JSON
    }

    def createRtp ={
        ApplicationUser applicationUser = session?.applicationUser
        render createRtpMiscellaneousTransactionsAction.execute(params,applicationUser) as JSON
    }

    def distributionPointCustomerList = {
        List customerList = utilMiscellaneousTransactionsAction.getCustomerListBySelectedDp(Long.parseLong(params.dpId),'')
        render(view: 'popUpCustomerList', model: [aaData: customerList as JSON])
    }

    def getDistributionProductListBySubInventory = {
        List productList = utilMiscellaneousTransactionsAction.getProductListBySubInventory(Long.parseLong(params.dpId), Long.parseLong(params.subInventoryId), params.query)
        render(view: 'popUpProductList', model: [aaData: productList as JSON])
    }
}
