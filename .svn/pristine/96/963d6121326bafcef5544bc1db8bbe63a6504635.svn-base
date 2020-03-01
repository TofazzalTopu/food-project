package com.bits.bdfp.inventory.sales.processmarketreturn


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.GridEntity
import com.bits.bdfp.inventory.sales.ProcessMarketReturn
import com.bits.bdfp.inventory.sales.ProcessMarketReturnService

@Component("listProcessMarketReturnAction")
class ListProcessMarketReturnAction implements IAction {
    public static final Log log = LogFactory.getLog(CreateProcessMarketReturnAction.class)

    @Autowired
    ProcessMarketReturnService processMarketReturnService

    public Object preCondition(def params) {
        return null
    }

    public Object execute(def params, def object) {
        Map output = [:]
        try {
            String pageNum                          = params.page ? params.page : "1"
            String resultPerPage                    = params.rows ? params.rows : "10"
            String start                            = ((Integer.parseInt(pageNum) - 1) * Integer.parseInt(resultPerPage)).toString()
            String sortCol                          = params.sidx ? params.sidx : "id"
            String sortOrder                        = params.sord ? params.sord : "desc"

            params.put("pageNum",                   pageNum)
            params.put("resultPerPage",             resultPerPage)
            params.put("start",                     start)
            params.put("sortCol",                   sortCol)
            params.put("sortOrder",                 sortOrder)

            Map result = processMarketReturnService.getListForGrid(params)
            List<ProcessMarketReturn> processMarketReturnList = (List<ProcessMarketReturn>) result.get('processMarketReturnList')
            long processMarketReturnCount = (long) result.get('processMarketReturnCount')
            List rows = this.wrapListInGridEntityList(processMarketReturnList, Integer.parseInt(start))

            int pageCount = 1
            if (processMarketReturnCount > Long.parseLong(resultPerPage.toString())) {
                pageCount = Math.ceil(processMarketReturnCount / Long.parseLong(resultPerPage.toString()))
            }

            output = [page: pageNum, total: pageCount, records: processMarketReturnCount, rows: rows]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }

        return output
    }

    public Object postCondition(def object) {
        return null
    }

    private List wrapListInGridEntityList(List<ProcessMarketReturn> processMarketReturnList, int start) {
        List instants = []
        int counter = start + 1;
        processMarketReturnList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                        "${instance.id ? instance.id : ''}",
                            
                        "${instance.mrNo? instance.mrNo : ''}",
                        "${instance.qcPersonName? instance.qcPersonName : ''}",
                        "${instance.mrProcessedBy? instance.mrProcessedBy : ''}",
                        "${instance.qcPersonPin? instance.qcPersonPin : ''}",
                        "${instance.qcPerformingTime? instance.qcPerformingTime : ''}",
                        "${instance.userCreated? instance.userCreated : ''}",
                        "${instance.userUpdated? instance.userUpdated : ''}",
                        "${instance.dateCreated? instance.dateCreated : ''}",
                        "${instance.dateUpdated? instance.dateUpdated : ''}"
                        ]
                        instants << obj
                        counter++
        }

        return instants
    }
}
