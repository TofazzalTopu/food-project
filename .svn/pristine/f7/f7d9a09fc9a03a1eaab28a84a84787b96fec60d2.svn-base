package com.bits.bdfp.settings.businessday


import com.bits.bdfp.settings.FinancialYearService
import com.docu.commons.GridEntity
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("listBusinessDayTimeAction")
class ListBusinessDayTimeAction implements IAction {
    public static final Log log = LogFactory.getLog(ListBusinessDayTimeAction.class)

    @Autowired
    FinancialYearService financialYearService

    public Object preCondition(def params) {
        return null
    }

    public Object execute(def params, def object) {
        Map output = [:]
        try {
            String pageNum = params.page ? params.page : "1"
            String resultPerPage = params.rows ? params.rows : "10"
            String start = ((Integer.parseInt(pageNum) - 1) * Integer.parseInt(resultPerPage)).toString()
            String sortCol = params.sidx ? params.sidx : "id"
            String sortOrder = params.sord ? params.sord : "desc"

            params.put("pageNum", pageNum)
            params.put("resultPerPage", resultPerPage)
            params.put("start", start)
            params.put("sortCol", sortCol)
            params.put("sortOrder", sortOrder)

            Map result = financialYearService.getBusinessDayTimeList(params)
            List businessDayTimeList = (List) result.get('businessDayTimeList')
            long businessDayTimeCount = (long) result.get('businessDayTimeCount')
            List rows = this.wrapListInGridEntityList(businessDayTimeList, Integer.parseInt(start))

            int pageCount = 1
            if (businessDayTimeCount > Long.parseLong(resultPerPage.toString())) {
                pageCount = Math.ceil(businessDayTimeCount / Long.parseLong(resultPerPage.toString()))
            }

            output = [page: pageNum, total: pageCount, records: businessDayTimeCount, rows: rows]
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return output
    }

    public Object postCondition(def object) {
        return null
    }

    private List wrapListInGridEntityList(List financialYearList, int start) {
        List instants = []
        int counter = start + 1;
        financialYearList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                        "${instance.id ? instance.id : ''}",
                        "${instance.name ? instance.name : ''}",
                        "${instance.month_string ? instance.month_string : ''}",
                        "${instance.business_date ? instance.business_date: ''}",
                        "${instance.start_time ? instance.start_time : ''}",
                        "${instance.end_time ? instance.end_time : ''}"
            ]
            instants << obj
            counter++
        }

        return instants
    }
}
