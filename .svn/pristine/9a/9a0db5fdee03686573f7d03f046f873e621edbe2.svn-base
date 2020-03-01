package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.customer.employee.ListSalesEmployeeByApplicationUserAction
import com.bits.bdfp.setup.salestarget.saleshead.CreateSalesHeadAction
import com.bits.bdfp.setup.salestarget.saleshead.DeleteSalesHeadAction
import com.bits.bdfp.setup.salestarget.saleshead.ListSalesHeadAction
import com.bits.bdfp.setup.salestarget.saleshead.UpdateSalesHeadAction
import com.bits.bdfp.setup.salestarget.saleshead.ReadSalesHeadAction
import com.bits.bdfp.setup.salestarget.saleshead.SearchSalesHeadAction

import com.docu.common.Message
import com.docu.commons.DateUtil
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class SalesHeadController {

    @Autowired
    private CreateSalesHeadAction createSalesHeadAction
    @Autowired
    private UpdateSalesHeadAction updateSalesHeadAction
    @Autowired
    private ListSalesHeadAction listSalesHeadAction
    @Autowired
    private DeleteSalesHeadAction deleteSalesHeadAction
    @Autowired
    private ReadSalesHeadAction readSalesHeadAction
    @Autowired
    private SearchSalesHeadAction searchSalesHeadAction
    @Autowired
    ListSalesEmployeeByApplicationUserAction listSalesEmployeeByApplicationUserAction

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = {
        Object objList = listSalesHeadAction.execute(params, null)
        render listSalesHeadAction.postCondition(objList, null) as JSON
    }

    def show = {
        int currentYear = DateUtil.getCurrentSystemYear()
        render(view: "/salestarget/salesHead/show", model: [currentYear: currentYear])
    }

    def create = {
        Message message = createSalesHeadAction.execute(params, null)
        render message as JSON
    }

    def edit = {
        int currentYear = DateUtil.getCurrentSystemYear()
        List<SalesHead> salesHeadList = SalesHead.findAllByTargetYearGreaterThanEqualsAndIsActive(currentYear, true)
        render(view: '/salestarget/salesHead/update', model: [salesHeadList: salesHeadList])
    }

    def update = {
        Message message = updateSalesHeadAction.execute(params, null)
        render message as JSON
    }

    def delete = {
        Message message = deleteSalesHeadAction.execute(params, null)
        render message as JSON
    }

    def search = {
        SalesHead salesHead = (SalesHead) searchSalesHeadAction.execute(params, null)
        if (salesHead) {
            render salesHead as JSON
        } else {
            render ''
        }
    }
    def listEmployee = {
        render listSalesEmployeeByApplicationUserAction.execute(params, null) as JSON
    }

    def popupEmployeeListPanel={
        render(view: '/salestarget/salesHead/popUpEmployeeList')
    }

    def jsonEmployeeList = {
        Map map = [:]
        List data = (List)listSalesEmployeeByApplicationUserAction.execute(params, null)
        map.put("aaData", data)
        render map as JSON
    }

    def saleHeadDetails = {
        SalesHead salesHead = (SalesHead) readSalesHeadAction.execute(params, null)
        render(template: "/salestarget/salesHead/salesHeadInfo", model: [salesHead: salesHead])
    }
}
