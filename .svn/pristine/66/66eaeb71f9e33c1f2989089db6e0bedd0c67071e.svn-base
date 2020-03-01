package com.docu.commons.maritalstatus


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.Message
import com.docu.commons.UserMessageBuilder
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.GridEntity
import com.docu.commons.MaritalStatus
import com.docu.commons.MaritalStatusService

@Component("listMaritalStatusAction")
class ListMaritalStatusAction implements IAction {
    public static final Log log = LogFactory.getLog(CreateMaritalStatusAction.class)

    @Autowired
    MaritalStatusService maritalStatusService

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

            Map result = maritalStatusService.list(params)
            List<MaritalStatus> maritalStatusList = (List<MaritalStatus>) result.get('maritalStatusList')
            long maritalStatusCount = (long) result.get('maritalStatusCount')
            List rows = this.wrapListInGridEntityList(maritalStatusList, Integer.parseInt(start))

            int pageCount = 1
            if (maritalStatusCount > Long.parseLong(resultPerPage.toString())) {
                pageCount = Math.ceil(maritalStatusCount / Long.parseLong(resultPerPage.toString()))
            }

            output = [page: pageNum, total: pageCount, records: maritalStatusCount, rows: rows]
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return output
    }

    public Object postCondition(def object) {
        return null
    }

    private List wrapListInGridEntityList(List<MaritalStatus> maritalStatusList, int start) {
        List instants = []
        int counter = start + 1;
        maritalStatusList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                    "${instance.id ? instance.id : ''}",

                    "${instance.name ? instance.name : ''}",
                    "${instance.description ? instance.description : ''}"
            ]
            instants << obj
            counter++
        }

        return instants
    }
}
