package com.bits.bdfp.settings.bussinessday

import com.bits.bdfp.settings.businessday.CreateBusinessDayTimeAction
import com.bits.bdfp.settings.businessday.CreateFinancialYearAction
import com.bits.bdfp.settings.businessday.EditBusinessDayAction
import com.bits.bdfp.settings.businessday.EditBusinessMonthAction
import com.bits.bdfp.settings.businessday.GetBusinessDayAction
import com.bits.bdfp.settings.businessday.GetBusinessMonthAction
import com.bits.bdfp.settings.businessday.ListBusinessDayAction
import com.bits.bdfp.settings.businessday.ListBusinessDayTimeAction
import com.bits.bdfp.settings.businessday.ListBusinessMonthAction
import com.bits.bdfp.settings.businessday.ListFinancialYearAction
import com.bits.bdfp.settings.businessday.OpenedBusinessMonthAction
import com.bits.bdfp.settings.businessday.OpenedFinancialYearAction
import com.bits.bdfp.settings.businessday.SaveBusinessDayAction
import com.bits.bdfp.settings.businessday.SaveBusinessMonthAction
import com.bits.bdfp.settings.businessday.UpdateBusinessDayAction
import com.bits.bdfp.settings.businessday.UpdateBusinessMonthAction
import com.docu.commons.Message
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class FinancialYearController {
    @Autowired
    CreateFinancialYearAction createFinancialYearAction
    @Autowired
    ListFinancialYearAction listFinancialYearAction
    @Autowired
    OpenedFinancialYearAction openedFinancialYearAction
    @Autowired
    CreateBusinessDayTimeAction createBusinessDayTimeAction
    @Autowired
    ListBusinessDayTimeAction listBusinessDayTimeAction
    @Autowired
    GetBusinessMonthAction getBusinessMonthAction
    @Autowired
    OpenedBusinessMonthAction openedBusinessMonthAction
    @Autowired
    GetBusinessDayAction getBusinessDayAction
    @Autowired
    ListBusinessDayAction listBusinessDayAction
    @Autowired
    SaveBusinessDayAction saveBusinessDayAction
    @Autowired
    EditBusinessDayAction editBusinessDayAction
    @Autowired
    UpdateBusinessDayAction updateBusinessDayAction
    @Autowired
    SaveBusinessMonthAction saveBusinessMonthAction
    @Autowired
    ListBusinessMonthAction listBusinessMonthAction
    @Autowired
    EditBusinessMonthAction editBusinessMonthAction
    @Autowired
    UpdateBusinessMonthAction updateBusinessMonthAction
    def show = {

    }
    def list = {
        render listFinancialYearAction.execute(params, null) as JSON
    }
    def save = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createFinancialYearAction.execute(params, applicationUser)
        render message as JSON
    }

//    def edit = {
//        Map data = (Map) readFinancialYearAction.execute(params, null)
//        render data as JSON
//    }
//
//    def update = {
//        ApplicationUser applicationUser = session.applicationUser
//        params.put("user", applicationUser)
//        Object object = updateFinancialYearAction.preCondition(params)
//        Message message = object.get(Message.MESSAGE)
//        if (message.type == Message.SUCCESS) {
//            object = updateFinancialYearAction.execute(params, object)
//            message = object.get(Message.MESSAGE)
//        }
//        render message as JSON
//    }
//
//    def delete = {
//        Object object = deleteFinancialYearAction.preCondition(params)
//        Message message = object.get(Message.MESSAGE)
//        if (message.type == Message.SUCCESS) {
//            object = deleteFinancialYearAction.execute(params, object)
//            message = object.get(Message.MESSAGE)
//        }
//            render message as JSON
//    }


    def showBusinessDayTime = {
        List financialYearList = openedFinancialYearAction.execute(params, null)
        render(view: "/financialYear/businessDayTime/show", model: [financialYearList: financialYearList])
    }

    def setBusinessDayTime = {
        params.put("id", params.financialYearId)
        Object object = createBusinessDayTimeAction.preCondition(params)

        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = createBusinessDayTimeAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }
    def getBusinessDayTimeList = {
        render listBusinessDayTimeAction.execute(params, null) as JSON
    }


    def showBusinessDay = {
        List financialYearList = openedFinancialYearAction.execute(params, null)
        render(view: "/financialYear/businessDay/show", model: [financialYearList: financialYearList])
    }

    def getBusinessMonth = {
        List businessMonthDetailsList = getBusinessMonthAction.execute(params, null)
        render businessMonthDetailsList as JSON
    }

    def getOpenedBusinessMonth = {
        List businessMonthDetailsList = openedBusinessMonthAction.execute(params, null)
        render businessMonthDetailsList as JSON
    }

    def getBusinessDay = {
        List businessDayDetailsList = getBusinessDayAction.execute(params, null)
        render businessDayDetailsList as JSON
    }

    def getBusinessDayList = {
        render listBusinessDayAction.execute(params, null) as JSON
    }

    def saveBusinessDay = {
        ApplicationUser applicationUser = session.applicationUser
        String openedFrom = session?.ipDetails
        params.put("openedFrom", openedFrom)
        params.put("userCreated", applicationUser)
        Object object = saveBusinessDayAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = saveBusinessDayAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def editBusinessDay = {
        List editBusinessDayList = editBusinessDayAction.execute(params, null)
        render editBusinessDayList as JSON
    }


    def updateBusinessDay = {
        ApplicationUser applicationUser = session.applicationUser
        params.put("userCreated", applicationUser)
        Object object = updateBusinessDayAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = updateBusinessDayAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def showBusinessMonth = {
        List financialYearList = openedFinancialYearAction.execute(params, null)
        render(view: "/financialYear/businessMonth/show", model: [financialYearList: financialYearList])
    }

    /* set business month*/
    def saveBusinessMonth = {
        ApplicationUser applicationUser = session.applicationUser
        params.put("userCreated", applicationUser)
        Object object = saveBusinessMonthAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = saveBusinessMonthAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }

    def getBusinessMonthList = {
        render listBusinessMonthAction.execute(params, null) as JSON
    }
    def editBusinessMonth = {
        List editBusinessMonthList = editBusinessMonthAction.execute(params, null)
        render editBusinessMonthList as JSON
    }


    def updateBusinessMonth = {
        ApplicationUser applicationUser = session.applicationUser
        params.put("userCreated", applicationUser)
        Object object = updateBusinessMonthAction.preCondition(params)
        Message message = object.get(Message.MESSAGE)
        if (message.type == Message.SUCCESS) {
            object = updateBusinessMonthAction.execute(params, object)
            message = object.get(Message.MESSAGE)
        }
        render message as JSON
    }
    /* end set business month */
}
