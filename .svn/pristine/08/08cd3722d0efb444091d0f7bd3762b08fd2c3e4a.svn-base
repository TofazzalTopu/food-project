package com.bits.bdfp.currency.currencydemonstration

import com.bits.bdfp.util.ApplicationConstants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.commons.IAction
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.GridEntity
import com.bits.bdfp.currency.CurrencyDemonstration
import com.bits.bdfp.currency.CurrencyDemonstrationService

@Component("listCurrencyDemonstrationAction")
class ListCurrencyDemonstrationAction implements IAction {
    public static final Log log = LogFactory.getLog(CreateCurrencyDemonstrationAction.class)

    @Autowired
    CurrencyDemonstrationService currencyDemonstrationService

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

            Map result                              = currencyDemonstrationService.list(params)
            List<CurrencyDemonstration> currencyDemonstrationList  = (List<CurrencyDemonstration>) result.get('currencyDemonstrationList')
            long currencyDemonstrationCount               = (long) result.get('currencyDemonstrationCount')
            List rows                               = this.wrapListInGridEntityList(currencyDemonstrationList, Integer.parseInt(start))

            int pageCount = 1
            if (currencyDemonstrationCount > Long.parseLong(resultPerPage.toString())) {
                pageCount = Math.ceil(currencyDemonstrationCount / Long.parseLong(resultPerPage.toString()))
            }

            output = [page: pageNum, total: pageCount, records: currencyDemonstrationCount, rows: rows]
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return output
    }

    public Object postCondition(def object) {
        return null
    }

    private List wrapListInGridEntityList(List<CurrencyDemonstration> currencyDemonstrationList, int start) {
        List instants = []
        int counter = start + 1;
        currencyDemonstrationList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                        "${instance.id ? instance.id : ''}",
                            
                        "${instance.userCreated? instance.userCreated : ''}",
                        "${instance.userUpdated? instance.userUpdated : ''}",
                        "${instance.dateCreated? instance.dateCreated : ''}",
                        "${instance.dateUpdated? instance.dateUpdated : ''}",
                        "${instance.localCurrency? instance.localCurrency : ''}",
                        "${instance.noteName? instance.noteName : ''}",
                        "${instance.value? instance.value : ''}",
                    "${instance.isActive? ApplicationConstants.TICK_MARK_STRING : ApplicationConstants.CROSS_STRING}"
                        ]
                        instants << obj
                        counter++
        }

        return instants
    }
}
