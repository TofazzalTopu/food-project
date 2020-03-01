package com.bits.bdfp.inventory.retailorder.retailcashcollect

import com.bits.bdfp.inventory.retailorder.RetailOrderService
import com.docu.common.Action
import com.docu.commons.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 2/7/16.
 */
@Component("listNonPaidRetailInvoiceAction")
class ListNonPaidRetailInvoiceAction extends Action {
    public static final Log log = LogFactory.getLog(ListNonPaidRetailInvoiceAction.class)

    @Autowired
    RetailOrderService retailOrderService

    public Object preCondition(def params, Object object) {
        return null
    }

    public Object execute(def params, def object) {
        Map output = [:]
        try {
            List result = retailOrderService.listNonPaidRetailInvoice(params)
            List objList = this.wrapListInGridEntityList(result)
            output = [page: 1, total: 1, records: objList.size(), rows: objList]
            return output
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }


    public Object postCondition(def params, def object) {
        return null
    }

    private List wrapListInGridEntityList(List retailOrderList) {
        List instants = []
        retailOrderList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = [
                    "${instance.id ? instance.id : ''}",
                    "${instance.invoiceNo ? instance.invoiceNo : ''}",
                    "${instance.invoiceDate ? instance.invoiceDate : ''}",
                    "${instance.invoiceAmount ? instance.invoiceAmount : ''}"
            ]
            instants << obj
        }

        return instants
    }
}
