package com.bits.bdfp.currency.localcurrency

import com.bits.bdfp.util.ApplicationConstants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.GridEntity
import com.bits.bdfp.currency.LocalCurrency
import com.bits.bdfp.currency.LocalCurrencyService

@Component("listLocalCurrencyAction")
class ListLocalCurrencyAction implements IAction {
    public static final Log log = LogFactory.getLog(CreateLocalCurrencyAction.class)

    @Autowired
    LocalCurrencyService localCurrencyService

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

            Map result                              = localCurrencyService.list(params)
            List<LocalCurrency> localCurrencyList  = (List<LocalCurrency>) result.get('localCurrencyList')
            long localCurrencyCount               = (long) result.get('localCurrencyCount')
            List rows                               = this.wrapListInGridEntityList(localCurrencyList, Integer.parseInt(start))

            int pageCount = 1
            if (localCurrencyCount > Long.parseLong(resultPerPage.toString())) {
                pageCount = Math.ceil(localCurrencyCount / Long.parseLong(resultPerPage.toString()))
            }

            output = [page: pageNum, total: pageCount, records: localCurrencyCount, rows: rows]
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return output
    }

    public Object postCondition(def object) {
        return null
    }

    private List wrapListInGridEntityList(List<LocalCurrency> localCurrencyList, int start) {
        List instants = []
        int counter = start + 1;
        localCurrencyList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                        "${instance.id ? instance.id : ''}",
                            
                        "${instance.userCreated? instance.userCreated : ''}",
                        "${instance.userUpdated? instance.userUpdated : ''}",
                        "${instance.dateCreated? instance.dateCreated : ''}",
                        "${instance.dateUpdated? instance.dateUpdated : ''}",
                        "${instance.name? instance.name : ''}",
                    "${instance.symbol? instance.symbol : ''}",
                    "${instance.note? instance.note : ''}",
                    "${instance.isActive? ApplicationConstants.TICK_MARK_STRING : ApplicationConstants.CROSS_STRING}"
                        ]
                        instants << obj
                        counter++
        }

        return instants
    }
}
