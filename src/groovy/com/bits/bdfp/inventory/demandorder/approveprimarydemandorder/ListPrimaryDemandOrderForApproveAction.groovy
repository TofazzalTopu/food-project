package com.bits.bdfp.inventory.demandorder.approveprimarydemandorder

import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by maimuna.akter on 9/13/2015.
 */
@Component("listPrimaryDemandOrderForApproveAction")
class ListPrimaryDemandOrderForApproveAction extends Action {

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

            result = primaryDemandOrderService.getPrimaryDemandOrderListForApprove(this,params)
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
                        "${counter}",
                        "${instance.id ? instance.id : ''}",

                        "${instance.customer_name? instance.customer_name : ''}",
                        "${instance.code? instance.legacy_id : ''}",
                        "${instance.address? instance.address : ''}",
                        "${instance.order_no? instance.order_no : ''}",
                        "${instance.order_date? instance.order_date : ''}",
                        "${instance.date_proposed_delivery? instance.date_proposed_delivery : ''}",
                        "${instance.delivery_order_value? instance.delivery_order_value : ''}",
                        "${instance.receivable? instance.receivable : ''}",
                        "${instance.inventory? instance.inventory : ''}"
            ]
            instants << obj
            counter++
        };
        return instants
    }

    public Object postCondition(Object objList, Object object) {
        int pageCount = 1
        if (resultPerPage < 0) {
            pageCount = 1
            resultPerPage = total
        } else {
            if (total > resultPerPage) {
                pageCount = Math.ceil(total / resultPerPage)
            }
        }
        Map output = [page: pageNum, total: pageCount, records: total, rows: objList]
        return output;
    }
}
