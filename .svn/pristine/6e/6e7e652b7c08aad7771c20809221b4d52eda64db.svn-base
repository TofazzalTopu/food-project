package com.bits.bdfp.settings.bussinessday

import com.bits.bdfp.settings.businessday.*
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class LocalHolidayController {
    @Autowired
    OpenedFinancialYearAction openedFinancialYearAction
    @Autowired
    GetBusinessMonthForHolidayAction getBusinessMonthForHolidayAction
    @Autowired
    CreateLocalHolidayAction createLocalHolidayAction
    @Autowired
    ListLocalHolidayAction listLocalHolidayAction
    @Autowired
    ReadLocalHolidayAction readLocalHolidayAction
    @Autowired
    UpdateLocalHolidayAction updateLocalHolidayAction
    @Autowired
    DeleteLocalHolidayAction deleteLocalHolidayAction
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def list = {
        render listLocalHolidayAction.execute(params, null) as JSON
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createLocalHolidayAction.execute(params, applicationUser)
        render message as JSON
    }

    def show = {
        List financialYearList = openedFinancialYearAction.execute(params, null)
        render(view: "/localHoliday/show", model: [financialYearList: financialYearList])
    }
    def getBusinessMonthForHoliday = {
        List businessMonthDetailsList = getBusinessMonthForHolidayAction.execute(params, null)
        render businessMonthDetailsList as JSON
    }
    def edit = {
        render readLocalHolidayAction.execute(params, null) as JSON
    }

    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateLocalHolidayAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteLocalHolidayAction.execute(params, null)
        render message as JSON
    }
}
