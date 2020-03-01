package com.bits.bdfp.inventory.setup.incentive

import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class CalculateAndAdjustIncentiveController {
    @Autowired
    CreateCalculateAndAdjustIncentiveAction createCalculateAndAdjustIncentiveAction
    @Autowired
    ListIncentiveCustomerByProgramAction listIncentiveCustomerByProgramAction
    @Autowired
    ListAllSearchIncentiveProgramByCriteriaAction listAllSearchIncentiveProgramByCriteriaAction
    @Autowired
    ListNitSalesAmountByCustomerAction listNitSalesAmountByCustomerAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static  defaultAction = "show"

    def show = {
        render(view: "show")
    }

    def getAllSearchIncentiveCustomerList = {
        render listIncentiveCustomerByProgramAction.execute(params, null) as JSON
    }

    def getAllSearchIncentiveProgramList = {
        render listAllSearchIncentiveProgramByCriteriaAction.execute(params, null) as JSON
    }

    def getNetSalesAmountByCustomer = {
        Map netSales = (Map) listNitSalesAmountByCustomerAction.execute(params, null)
        render netSales as JSON
    }

    def adjustIncentive = {
        ApplicationUser applicationUser = session?.applicationUser
        render createCalculateAndAdjustIncentiveAction.execute(params,applicationUser) as JSON
    }

}
