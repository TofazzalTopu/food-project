package com.bits.bdfp.inventory.sales.processmarketreturn

import com.bits.bdfp.inventory.sales.ProcessMarketReturnService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 4/24/2016.
 */
@Component("listProcessedMrDetailsAction")
class ListProcessedMrDetailsAction extends Action{
    private static final Log log = LogFactory.getLog(this)
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
        try {
            init(params)
            List list
            if(params.id) {
                list = processMarketReturnService.listProcessedReturnDetails(params)
            }else{
                list = processMarketReturnService.listRejectedReturnDetails(params)
            }
            List objList = this.wrapListInGridEntityList(list, start)
            return objList
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    private List wrapListInGridEntityList(List list, int start) {
        List instants = []
        int counter = start + 1;
        list.each { instance ->
            GridEntity obj = new GridEntity()
            String [] leak = instance.leak?.split(',')
            String [] mr = instance.mr?.split(',')
            String [] damage = instance.damage?.split(',')
            String [] sh = instance.short?.split(',')
            String [] challan = instance.short_challan?.split(',')
            obj.id = instance.pid
            obj.cell = ["${instance.pid ? instance.pid : ''}",
                        "${instance.name? instance.name : ''}",
                        "${instance.code? instance.code : ''}",
                        "${leak? leak[0] : '0'}",
                        "${leak? leak[1] : '0'}",
                        "${sh? sh[0] : '0'}",
                        "${sh? sh[1] : '0'}",
                        "${mr? mr[0] : '0'}",
                        "${mr? mr[1] : '0'}",
                        "${challan? challan[0] : '0'}",
                        "${challan? challan[1] : '0'}",
                        "${damage? damage[0] : '0'}",
                        "${damage? damage[1] : '0'}",
                        "${instance.total? instance.total : '0'}",
                        "${instance.rate? instance.total*instance.rate : '0'}"
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
