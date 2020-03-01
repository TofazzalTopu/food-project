package com.bits.bdfp.inventory.sales.invoice

import com.bits.bdfp.inventory.sales.InvoiceService
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.lang.reflect.AccessibleObject

/**
 * Created by NZ on 3/22/2016.
 */
@Component("listUnadjustedInvoiceAction")
class ListUnadjustedInvoiceAction extends Action {

    @Autowired
    InvoiceService invoiceService

    public Object preCondition(def params, def object) {
        //not need to implement
        return null
    }

    public Object execute(def params, def object) {
        try {
            init(params)
            params.put('appId', object.id)
            List objectList = null

            objectList = invoiceService.listUnadjustedInvoice(params)
            total = objectList.size()
            return this.wrapListInGridEntityList(objectList, start)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    public Object executeByCustomerId(def params, def object) {
        try {
            init(params)
            params.put('appId', object.id)
            List objectList = null

            objectList = invoiceService.listUnadjustedInvoiceByCustomerId(params)
            total = objectList.size()
            return this.wrapListInGridEntityListByCustomerId(objectList, start)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }


    Map getResultForUI(List objList) {
        int pageCount = 1
        if (total > resultPerPage) {
            pageCount = Math.ceil(total / resultPerPage)
        }
        Map output = [page: pageNum, total: pageCount, records: total, rows: objList]
        return output;
    }

    private Object wrapListInGridEntityList(objList, start) {
        try {
            List instants = []
            int counter = start + 1;
            objList.each { instance ->
                GridEntity obj = new GridEntity()
                obj.id = instance.id
                obj.cell = ["${instance.id ? instance.id : ''}",

                            "${instance.customer_id ? instance.customer_id : ''}",
                            "${instance.code ? instance.code : ''}",
                            "${instance.date_created ? instance.date_created : ''}",
                            "${instance.name ? instance.name : ''}",
                            "${instance.customer_code ? instance.customer_code : ''}",
                            "${instance.invoice_amount ? instance.invoice_amount : ''}",
                            "${instance.due_amount ? instance.due_amount : ''}"
                ]
                instants << obj
                counter++
            }
            return instants
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }
    private Object wrapListInGridEntityListByCustomerId(objList, start) {
        try {
            List instants = []
            int counter = start + 1;
            objList.each { instance ->
                GridEntity obj = new GridEntity()
                obj.id = instance.id
                obj.cell = ["${instance.id ? instance.id : ''}",

                            "${instance.code? instance.code : ''}",
                            "${instance.code ? instance.code : ''}",
                            "${instance.transaction_date ? instance.transaction_date : ''}",
                            "${instance.invoice_amount ? instance.invoice_amount : ''}",
                            "${instance.due_amount ? instance.due_amount : '0.0'}"
                ]
                instants << obj
                counter++
            }
            return instants
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return getResultForUI(params)
    }
}
