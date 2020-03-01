package com.bits.bdfp.inventory.demandorder.secondarydemandorder

import com.bits.bdfp.finance.CustomerPaymentService
import com.bits.bdfp.inventory.demandorder.Invoice
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 11/24/2015.
 */
@Component("listMRNoAction")
class ListMRNoAction extends Action{
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerPaymentService customerPaymentService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try{
            init(params)
            Map result = [:]
            List objectList = null

            result = customerPaymentService.listMRNo(this, params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityList(objectList, start)
            return objList
        }
        catch (Exception ex) {
            log.error(ex.message)
            return  null
        }
    }
    public Object listMRNoForAutoComplete(Object params, Object object) {
        try{
            init(params)
            List result = []
            result = customerPaymentService.listMRNoForAutoComplete(this, params)
            return result
        }
        catch (Exception ex) {
            log.error(ex.message)
            return  null
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

                        "${instance.mr_no? instance.mr_no : ''}",
                        "${instance.name? instance.name : ''}",
                        "${instance.trans_no? instance.trans_no : ''}",
                        "${instance.invoices? instance.invoices : ''}",
                        "${instance.date_transaction? instance.date_transaction : ''}",
                        "${instance.amount? instance.amount : ''}",
                        ""
            ]
            instants << obj
            counter++
        };
        return instants
    }

    public Object postCondition(Object params, Object object) {
        return getResultForUI(object)
    }

    private String getInvoiceNO(String invoices){
        String invoiceCode = ""
        String[] invoiceIds = invoices.split(",")
        if(invoiceIds.size()>0){
            invoiceIds.each {
                Invoice invoice = Invoice.findByCode(it)
                if(!invoice){
                    invoice = Invoice.read(Long.parseLong(it))
                }
                if(invoiceCode){
                    invoiceCode += ", "+invoice?invoice.code:''
                }else{
                    invoiceCode = invoice?invoice.code:''
                }
            }
        }
        return invoiceCode
    }
}
