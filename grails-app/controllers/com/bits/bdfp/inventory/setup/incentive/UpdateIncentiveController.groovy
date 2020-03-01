package com.bits.bdfp.inventory.setup.incentive

import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class UpdateIncentiveController {
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static  defaultAction = "show"

    @Autowired
    ListIncentiveProgramByTypeAction listIncentiveProgramByTypeAction
    @Autowired
    ListAllSearchIncentiveProgramByCriteriaAction listAllSearchIncentiveProgramByCriteriaAction

    def show = {
        render(view: "show")
    }

    def getIncentiveProgramList = {
        render listIncentiveProgramByTypeAction.execute(params, null) as JSON
    }

    def getAllSearchIncentiveProgramList = {
        render listAllSearchIncentiveProgramByCriteriaAction.execute(params, null) as JSON
    }
}
