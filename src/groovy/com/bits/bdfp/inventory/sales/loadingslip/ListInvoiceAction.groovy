package com.bits.bdfp.inventory.sales.loadingslip

import com.bits.bdfp.inventory.sales.LoadingSlipService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 9/30/2015.
 */
@Component("listInvoiceAction")
class ListInvoiceAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    LoadingSlipService loadingSlipService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try{
            init(params)
            Map result = [:]
            List objectList = null

            result = loadingSlipService.listInvoice(this, params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityList(objectList, start)
            return objList
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
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

    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        int counter = start + 1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["",
                        "${instance.id ? instance.id : ''}",

                        "${instance.name? instance.name : ''}",
                        "${instance.legacy_id? instance.legacy_id : ''}",
                        "${instance.invoice_no? instance.invoice_no : ''}",
                        "${instance.date_created? instance.date_created : ''}",
                        "${instance.order_no? instance.order_no : ''}",
                        "${instance.address? instance.address : ''}",
                        "${instance.quantity? instance.quantity : ''}"
            ]
            instants << obj
            counter++
        };
        return instants
    }

    public Object postCondition(Object params, Object object) {
        return getResultForUI(object)
    }
}
