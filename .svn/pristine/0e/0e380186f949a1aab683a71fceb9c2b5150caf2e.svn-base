package com.bits.bdfp.inventory.demandorder.writeoff

import com.bits.bdfp.inventory.demandorder.WriteOffService
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 3/28/2016.
 */
@Component("listInvoiceForWriteOffAction")
class ListInvoiceForWriteOffAction extends Action{

    @Autowired
    WriteOffService writeOffService

    public Object preCondition(def params, def object) {
        //not need to implement
        return null
    }

    public Object execute(def params, def object) {
        try{
            init(params)
            List objectList = null

            objectList = writeOffService.fetchInvoice(params)
            total = objectList.size()
            return this.wrapListInGridEntityList(objectList, start)
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
        try{
            List instants = []
            int counter = start + 1;
            objList.each { instance ->
                GridEntity obj = new GridEntity()
                obj.id = instance.id
                instants << obj
                counter++
                obj.cell = ["${instance.id ? instance.id : ''}",

                            "false",
                            "${instance.code? instance.code : ''}",
                            "${instance.date_created? instance.date_created : ''}",
                            "${instance.invoice_amount? instance.invoice_amount : ''}",
                            "${instance.due_amount? instance.due_amount : ''}",
                            "${instance.due_amount? instance.due_amount : ''}",
                            "false"

                ]
            }
            return instants
        } catch (Exception ex){
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return getResultForUI(params)
    }
}
