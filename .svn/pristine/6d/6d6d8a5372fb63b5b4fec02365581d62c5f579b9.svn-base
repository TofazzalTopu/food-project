package com.bits.bdfp.settings.businessday


import com.bits.bdfp.settings.FinancialYearService
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Action
import com.docu.commons.GridEntity
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("listFinancialYearAction")
class ListFinancialYearAction extends Action {
    public static final Log log = LogFactory.getLog(CreateFinancialYearAction.class)

    @Autowired
    FinancialYearService financialYearService

    public Object preCondition(Object params,Object object) {
        return null
    }

    public Object execute(Object params,Object object) {
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

            Map result = financialYearService.list(params)
            List financialYearList = (List) result.get('financialYearList')
            long financialYearCount = (long) result.get('financialYearCount')
            List rows = this.wrapListInGridEntityList(financialYearList, Integer.parseInt(start))

            int pageCount = 1
            if (financialYearCount > Long.parseLong(resultPerPage.toString())) {
                pageCount = Math.ceil(financialYearCount / Long.parseLong(resultPerPage.toString()))
            }

            output = [page: pageNum, total: pageCount, records: financialYearCount, rows: rows]
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return output
    }

    public Object postCondition(Object params,Object object) {
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
                        "${instance.date_start ? instance.date_start : ''}",
                        "${instance.date_end ? instance.date_end : ''}",
                        "${instance.is_opened ? ApplicationConstants.TICK_MARK_STRING : ApplicationConstants.CROSS_STRING}",
                        "${instance.username ? instance.username : ''}",
                        "${instance.date_created ? instance.date_created : ''}",
                        "${instance.user_updated ? instance.user_updated : ''}",
                        "${instance.date_last_updated ? instance.date_last_updated : ''}"
            ]
            instants << obj
            counter++
        }

        return instants
    }
}
