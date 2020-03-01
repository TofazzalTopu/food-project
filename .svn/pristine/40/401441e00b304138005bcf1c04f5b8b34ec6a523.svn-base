package com.bits.bdfp.inventory.sales.loadingslip

import com.bits.bdfp.inventory.sales.LoadingSlipService
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteLoadingSlipAction")
class DeleteLoadingSlipAction extends Action {

    private static final Log log = LogFactory.getLog(this)
    private final String MESSAGE_HEADER = 'Message'
    private final String MESSAGE_SUCCESS = 'In Transit Invoice Rollback Successful.'
    private final String MESSAGE_FAILED = 'In Transit Invoice Rollback Failed.'

    @Autowired
    LoadingSlipService loadingSlipService

    public Object preCondition(Object params, Object object) {
        try {
            return loadingSlipService.read(Long.parseLong(params.id.toString()))
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    public Object postCondition(Object params, Object object) {
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
        Map output = [page: pageNum, total: pageCount, records: total, rows: object]
        return output;
    }

    public Object execute(Object params, Object object) {
        try {
            return loadingSlipService.delete(object)
        }
        catch (Exception ex) {
            log.error(ex.message)
            return new Integer(0)
        }
    }

    public Object getLoadingSlipByInvoiceNumber(Object params) {
        try{
            init(params)
            Map result = [:]
            List objectList = null

            result = loadingSlipService.getLoadingSlipByInvoiceNumber(params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.getLoadingSlipListByInvoiceNumber(objectList, start)
            return objList
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    private List getLoadingSlipListByInvoiceNumber(objList, start) {
        List instants = []
        int counter = start + 1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = [ // "",
                        "${counter}",
                        "${instance.id ? instance.id : ''}",
                        "${instance.invoiceNo? instance.invoiceNo : ''}",
                        "${instance.invoiceAmount? instance.invoiceAmount : '0.00'}",
                        "${instance.customer? instance.customer : ''}",
                        "${instance.primaryOrderNo? instance.primaryOrderNo : ''}",
                        "${instance.orderStatus? instance.orderStatus : ''}",
                        "${instance.invoiceDate? instance.invoiceDate : ''}",
                        "${instance.loadingSlipNo? instance.loadingSlipNo : ''}",
                        "${instance.loadingSlipDate? instance.loadingSlipDate : ''}"
            ]
            instants << obj
            counter++
        };
        return instants
    }

    public Object deleteLoadingSlipDetails(Object params, Object object) {
        try {
            boolean success = loadingSlipService.deleteLoadingSlipDetails(params)
            if (success) {
                return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
            } else {
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, MESSAGE_FAILED)
            }
        }
        catch (Exception ex) {
            log.error(ex.message)
            return new Integer(0)
        }
    }

}