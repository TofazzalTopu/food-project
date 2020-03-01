package com.bits.bdfp.inventory.sales.processmarketreturn

import com.bits.bdfp.inventory.sales.MarketReturn
import com.bits.bdfp.inventory.sales.ProcessMarketReturnService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 1/25/2016.
 */
@Component("listMarketReturnForProcessAction")
class ListMarketReturnForProcessAction extends Action {

    @Autowired
    ProcessMarketReturnService processMarketReturnService

    @Override
    public Object preCondition(Object params, Object object) {
        return null
    }

    @Override
    public Object postCondition(Object params, Object object) {
        return getResultForUI(object)
    }

    @Override
    public Object execute(Object params, Object object) {
        Map output = [:]
        try {
            init(params)
            List list = processMarketReturnService.listMarketReturn(params)
            List objList = this.wrapListInGridEntityList(list, start)
            return objList
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return output
    }

    private List wrapListInGridEntityList(List<MarketReturn> marketReturnList, int start) {
        List instants = []
        int counter = start + 1;
        marketReturnList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${instance.id ? instance.id : ''}",
                        "${instance.mr_no? instance.mr_no : ''}",
                        "${instance.customer? instance.customer : ''}",
                        "${instance.date_created? instance.date_created : ''}",
                        '<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" ' +
                                'value="Reject" onclick="deleteItemFromGrid(' + instance.id + ');"/>'
            ]
            instants << obj
            counter++
        }

        return instants
    }

    Map getResultForUI(List objList) {
        int pageCount = 1
        if (resultPerPage < 0) {
            pageCount = 1
            resultPerPage=total
        }
        else {
            if (total > resultPerPage) {
                pageCount = Math.ceil(total / resultPerPage)
            }
        }
        Map output = [page: pageNum, total: pageCount, records: total, rows: objList]
        return output;
    }
}
