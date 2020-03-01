package com.bits.bdfp.inventory.demandorder.secondarydemandorder

import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * Created by NZ on 9/15/2015.
 */
@Component("listCustomerOrdersAction")
class ListCustomerOrdersAction extends Action {

    @Autowired
    SecondaryDemandOrderService secondaryDemandOrderService

    public Object preCondition(Object params, Object object) {
        return null
    }

    public Object postCondition(Object params, Object object) {
        return getResultForUI(object)
    }

    public Object execute(Object params, Object object) {
        try {
            init(params)
            Map result = [:]
            List objectList = null

            result = secondaryDemandOrderService.listSecondaryOrderForPrimary(this, params)
            if (result) { // in case: normal list
                objectList = result.objList
            }

            List objList = this.wrapListInGridEntityList(objectList, start)
            total = objList.size()
            result = this.postCondition(null, objList)
            return result
        }
        catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        int counter = start + 1;
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy")
        objList.each { instance->
            GridEntity obj = new GridEntity()
                obj.id = instance.id
                obj.cell = ["${instance.id ? instance.id : ''}",
                            "${instance.customer_id ? instance.customer_id : ''}",
                            'false',
                            "${instance.name ? instance.name : ''}",
                            "${instance.order_no ? instance.order_no : ''}",
                            "${instance.date_order ? format.format(instance.date_order) : ''}",
                            "${instance.date_deliver ? format.format(instance.date_deliver) : ''}",
                            "${instance.amount ? instance.amount : ''}"
                ]
                instants << obj
                counter++
        };
        return instants
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
}