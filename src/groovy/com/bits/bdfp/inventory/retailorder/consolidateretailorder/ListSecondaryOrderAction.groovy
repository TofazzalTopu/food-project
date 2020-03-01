package com.bits.bdfp.inventory.retailorder.consolidateretailorder

import com.bits.bdfp.inventory.retailorder.RetailOrderService
import com.docu.common.Action
import com.docu.commons.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 1/19/16.
 */
@Component("listSecondaryOrderAction")
class ListSecondaryOrderAction extends Action {
    public static final Log log = LogFactory.getLog(this)

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

            result = retailOrderService.listSecondaryOrder(params, this)
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

    public Object postCondition(def params, def object) {
        return getResultForUI(object)
    }

    private List wrapListInGridEntityList(List retailOrderList, int start) {
        List instants = []
        int counter = start + 1;
        retailOrderList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                    "${instance.id ? instance.id : ''}",
                    "${instance.orderNo ? instance.orderNo : ''}",
                    "${instance.orderDate ? instance.orderDate : ''}",
                    "${instance.customerName ? instance.customerName : ''}",
                    "${instance.legacyId ? instance.legacyId : ''}",
                    "${instance.customerCode ? instance.customerCode : ''}",
                    "${instance.enterprise ? instance.enterprise : ''}",
                    "${instance.deliveryDate ? instance.deliveryDate : ''}"
            ]
            instants << obj
            counter++
        }

        return instants
    }
}
