package com.bits.bdfp.settings.businessday


import com.bits.bdfp.settings.FinancialYearService
import com.bits.bdfp.util.ApplicationConstants
import com.docu.commons.DateUtil
import com.docu.commons.GridEntity
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("listBusinessDayAction")
class ListBusinessDayAction implements IAction {
    public static final Log log = LogFactory.getLog(ListBusinessDayAction.class)

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

            Map result = financialYearService.getBusinessDayList(params)
            List businessDayList = (List) result.get('businessDayList')
            long businessDayCount = (long) result.get('businessDayCount')
            List rows = this.wrapListInGridEntityList(businessDayList, Integer.parseInt(start))

            int pageCount = 1
            if (businessDayCount > Long.parseLong(resultPerPage.toString())) {
                pageCount = Math.ceil(businessDayCount / Long.parseLong(resultPerPage.toString()))
            }

            output = [page: pageNum, total: pageCount, records: businessDayCount, rows: rows]
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
                        "${instance.business_date ? DateUtil.getDateFormatAsString(DateUtil.getSimpleDateYMD(instance.business_date.toString())) : ''}",
                        "${instance.opened_from ? instance.opened_from : ''}",
                        "${instance.is_open ? ApplicationConstants.TICK_MARK_STRING : ApplicationConstants.CROSS_STRING}",
                        "${instance.username ? instance.username : ''}",
                        "${instance.user_updated ? instance.user_updated : ''}",
                        "${instance.date_created ? DateUtil.getDateFormatAsString(DateUtil.getSimpleDateYMD(instance.date_created.toString())) : ''}",
                        "${instance.date_last_updated ? DateUtil.getDateFormatAsString(DateUtil.getSimpleDateYMD(instance.date_last_updated.toString())) : ''}"
            ]
            instants << obj
            counter++
        }

        return instants
    }
}
