package com.bits.bdfp.inventory.retailorder.processretailorder

import com.bits.bdfp.inventory.retailorder.RetailOrderService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 2/3/16.
 */
@Component("listRetailInvoiceAction")
class ListRetailInvoiceAction extends Action {
    public static final Log log = LogFactory.getLog(ListRetailInvoiceAction.class)
    Message message

    static final EMPTY_DATA= "No data found;";


    @Autowired
    RetailOrderService retailOrderService

    public Object preCondition(def params, Object object) {
        return null
    }

    public Object execute(def params, def object) {
        Map output = [:]
        try {
            init(params)
            Map result = [:]
            List objectList = null

            result = retailOrderService.listRetailInvoice(params, this)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            if(result.objList.size() == 0){
                message = this.getMessage("Retail invoice", Message.SUCCESS, this.EMPTY_DATA)
                return  message;
            }
            List objList = this.wrapListInGridEntityList(objectList, start)

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
            output = [page: pageNum, total: pageCount, records: total, rows: objList]
            return output
        }
        catch (Exception ex) {
            log.error(ex.message)
            return  null
        }
    }


    public Object postCondition(def params, def object) {
        return null
    }

    private List wrapListInGridEntityList(List retailOrderList, int start) {
        List instants = []
        int counter = start + 1;
        retailOrderList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = [
//                    "${counter}",
                    "${instance.id ? instance.id : ''}",
                    "${instance.invoiceNo ? instance.invoiceNo : ''}",
                    "${instance.invoiceAmount ? instance.invoiceAmount : ''}",
                    "${instance.invoiceDate ? instance.invoiceDate : ''}",
                    "${instance.legacyId ? instance.legacyId : ''}",
                    "${instance.customerName ? instance.customerName : ''}",
                    "${instance.customerCode ? instance.customerCode : ''}",
                    "${instance.orderNo ? instance.orderNo : ''}",
                    "${instance.orderDate ? instance.orderDate : ''}"
            ]
            instants << obj
            counter++
        }

        return instants
    }
}
