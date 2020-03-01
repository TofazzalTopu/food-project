package com.bits.bdfp.inventory.sales.processmarketreturn

import com.bits.bdfp.inventory.sales.ProcessMarketReturnService
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 4/21/2016.
 */
@Component("searchProcessedMarketReturnAction")
class SearchProcessedMarketReturnAction extends Action{
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
            params.put('id', object.id)
            List list = processMarketReturnService.fetchDpByUser(params)
            if(list[0]?.is_factory) {
                list = processMarketReturnService.listProcessedReturn(params)
            }else{
                list = processMarketReturnService.listProcessedAndRejectedReturn(params)
            }
            List objList = this.wrapListInGridEntityList(list, start)
            return objList
        }
        catch (Exception ex) {
            log.error(ex.message)
        }

        return output
    }

    private List wrapListInGridEntityList(List list, int start) {
        List instants = []
        int counter = start + 1;
        list.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${instance.id ? instance.id : ''}",
                        "${instance.name? instance.name : ''}",
                        "${instance.code? instance.code : ''}",
                        "${instance.mr_no? instance.mr_no : ''}",
                        "${instance.process_date? instance.process_date : ''}",
                        "${instance.qc_performing_time? instance.qc_performing_time : ''}",
                        "${instance.qc_person_name? instance.qc_person_name : ''}",
                        "${instance.qc_person_pin? instance.qc_person_pin : ''}",
                        "${instance.mr_status? instance.mr_status : 'PROCESSED'}"
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
