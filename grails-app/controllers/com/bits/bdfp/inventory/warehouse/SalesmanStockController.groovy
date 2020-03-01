package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.warehouse.salesmanstock.ListSalesmanByBranchAction
import com.bits.bdfp.inventory.warehouse.salesmanstock.ListSalesmanStockAction
import com.bits.bdfp.settings.ApplicationUserDistributionPoint
import com.bits.bdfp.util.ApplicationConstants
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class SalesmanStockController {
    @Autowired
    ListSalesmanStockAction listSalesmanStockAction
    @Autowired
    ListSalesmanByBranchAction listSalesmanByBranchAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def show = {
        ApplicationUser applicationUser = session?.applicationUser
        ApplicationUserDistributionPoint applicationUserDistributionPoint = ApplicationUserDistributionPoint.findByApplicationUser(applicationUser)
        CustomerMaster branch = CustomerMaster.read(applicationUserDistributionPoint?.applicationUser?.customerMasterId)
        CustomerMaster salesman = CustomerMaster.read(applicationUser.customerMasterId)
//        UserType userType = UserType.read(ApplicationUser.read(applicationUser.id).userType.id)
        List customerList = []
        if(branch?.id == applicationUser.customerMasterId){
            customerList = listSalesmanByBranchAction.execute(applicationUser,null)
            render (view:"show", model: [customerList:customerList])
        }else if(salesman?.category?.id == ApplicationConstants.CUSTOMER_CATEGORY_SALES_MAN_ID){
            salesman.name = salesman.name+" (Code: "+salesman.code+")"
            customerList.add(salesman)
            render (view:"show", model: [customerList:customerList])
        }else{
            render (view: "/demandOrder/newPrimaryDemandOrder/unAuthorized")
        }
    }

    def getStockList = {
        Map stockList = (Map) listSalesmanStockAction.execute(params,null)
        render stockList as JSON
    }
}
