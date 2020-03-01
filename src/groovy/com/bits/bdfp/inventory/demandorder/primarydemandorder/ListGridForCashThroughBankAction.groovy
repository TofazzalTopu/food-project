package com.bits.bdfp.inventory.demandorder.primarydemandorder

import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by prianka.adhikary on 9/28/2015.
 */
@Component("listGridForCashThroughBankAction")
class ListGridForCashThroughBankAction extends Action{


    private static final Log log = LogFactory.getLog(this)
    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try{
            init(params)
            Map result = [:]
            List objectList = null

            result = primaryDemandOrderService.getListForCashThroughBank(this, params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityList(objectList, start)
            result = this.postCondition(objList, null)
            return result
        }
        catch (Exception ex) {
            log.error(ex.message)
            return  null
        }
    }


    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        int counter = -1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            if(instance.due_amount > 1) {
                obj.id = instance.id
                obj.cell = [
                        "${instance.id}",
                        "${instance.invoice_no ? instance.invoice_no : ''}",
                        "${instance.invoice_date ? instance.invoice_date : ''}",
                        "${instance.payment_due_date ? instance.payment_due_date : '0'}",
                        "${instance.amount ? instance.amount : '0'}",
                        "${instance.paid_amount ? instance.paid_amount : '0'}",
                        "${instance.due_amount ? instance.due_amount : '0'}",
                        '0'
                ]
                instants << obj
            }
        };
        return instants
    }

    public Object postCondition(Object objList, Object object) {
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
