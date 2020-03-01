package com.bits.bdfp.inventory.demandorder.secondarydemandorder

import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdtofazzal.hossain on 3/6/2017.
 */
@Component("listSecondaryInvoiceAction")
class ListSecondaryInvoiceAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService

    @Override
    protected Object preCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    protected Object postCondition(Object objList, Object object) {
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

    @Override
    protected Object execute(def Object object1,def Object object2) {
        return null
    }

    public Object secondaryInvoiceList(Object params, Object object){
        try{
            init(params)
            Map result = [:]
            List objectList = null

            result = primaryDemandOrderService.getListForCancelSecondaryInvoiceStatusGrid(this, params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityList(objectList, start)
            return this.postCondition(objList, null)
        }
        catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        int counter = start + 1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["",
                        "${counter}",
                        "${instance.id ? instance.id : ''}",
                        "${instance.order_no ? instance.order_no : ''}",
                        "${instance.invoice_no? instance.invoice_no : ''}",
                        "${instance.createdBy? instance.createdBy : ''}",
                        "${instance.customer? instance.customer : ''}",
                        "${instance.invoiceAmount? instance.invoiceAmount : ''}",
                        "${instance.date_created? instance.date_created : ''}",
                        "${instance.demand_order_status? instance.demand_order_status : ''}"
            ]
            instants << obj
            counter++
        };
        return instants
    }
}
