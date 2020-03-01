package com.bits.bdfp.settings.bussinessday

import com.bits.bdfp.settings.businessday.CreateBusinessHolidayAction
import com.bits.bdfp.settings.businessday.DeleteBusinessHolidayAction
import com.bits.bdfp.settings.businessday.GetBusinessMonthForHolidayAction
import com.bits.bdfp.settings.businessday.ListBusinessHolidayAction
import com.bits.bdfp.settings.businessday.OpenedFinancialYearAction
import com.bits.bdfp.settings.businessday.ReadHolidayAction
import com.bits.bdfp.settings.businessday.UpdateBusinessHolidayAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class BusinessHolidayController {
    @Autowired
    OpenedFinancialYearAction openedFinancialYearAction
    @Autowired
    GetBusinessMonthForHolidayAction getBusinessMonthForHolidayAction
    @Autowired
    CreateBusinessHolidayAction createBusinessHolidayAction
    @Autowired
    ListBusinessHolidayAction listBusinessHolidayAction
    @Autowired
    ReadHolidayAction readHolidayAction
    @Autowired
    UpdateBusinessHolidayAction updateBusinessHolidayAction
    @Autowired
    DeleteBusinessHolidayAction deleteBusinessHolidayAction
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def list = {
        render listBusinessHolidayAction.execute(params, null) as JSON
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createBusinessHolidayAction.execute(params, applicationUser)
        render message as JSON
    }

    def show = {
        List financialYearList = openedFinancialYearAction.execute(params, null)
        render(view: "/businessHoliday/show", model: [financialYearList: financialYearList])
    }
    def getBusinessMonthForHoliday = {
        List businessMonthDetailsList = getBusinessMonthForHolidayAction.execute(params, null)
        render businessMonthDetailsList as JSON
    }
    def edit = {
        render readHolidayAction.execute(params, null) as JSON
    }

    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateBusinessHolidayAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteBusinessHolidayAction.execute(params, null)
        render message as JSON
    }
}
