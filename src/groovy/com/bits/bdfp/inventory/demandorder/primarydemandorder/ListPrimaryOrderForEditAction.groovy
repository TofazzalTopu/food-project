package com.bits.bdfp.inventory.demandorder.primarydemandorder

import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 9/22/2015.
 */
@Component("listPrimaryOrderForEditAction")
class ListPrimaryOrderForEditAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            init(params)
            Map result = [:]
            List objectList = null

            result = primaryDemandOrderService.listForEdit(this, params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityList(objectList, start)
            return objList
        }
        catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    Map getResultForUI(List objList) {
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

    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        int counter = start + 1;
        objList.each { instance ->
//            if(instance.count == 0 || instance.demand_order_status == "SENT_FOR_PROCESSING") {
                GridEntity obj = new GridEntity()
                obj.id = instance.id
//            CustomerMaster customerMaster = CustomerMaster.read(instance.userOrderPlaced.id)
                obj.cell = ["${counter}",
                            "${instance.id ? instance.id : ''}",

                            "${instance.order_no ? instance.order_no : ''}",
                            "${instance.full_name ? instance.full_name : ''}",
                            "${instance.order_date ? instance.order_date : ''}",
                            "${instance.date_proposed_delivery ? instance.date_proposed_delivery : ''}",
                            "${instance.date_expected_deliver ? instance.date_expected_deliver : ''}",
                            "${instance.demand_order_status ? instance.demand_order_status : ''}"
                ]
                instants << obj
                counter++
//            }
        };
        return instants
    }

    public Object postCondition(Object params, Object object) {
        return getResultForUI(object)
    }
}
