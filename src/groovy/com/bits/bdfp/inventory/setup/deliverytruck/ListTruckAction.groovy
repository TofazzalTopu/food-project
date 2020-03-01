package com.bits.bdfp.inventory.setup.deliverytruck

import com.bits.bdfp.inventory.setup.DeliveryTruckService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 9/30/2015.
 */
@Component("listTruckAction")
class ListTruckAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    DeliveryTruckService deliveryTruckService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try{
            init(params)
            Map result = [:]
            List objectList = null

            result = deliveryTruckService.listTruck(this, params)
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

    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        int counter = start + 1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["",
                        "${instance.id ? instance.id : ''}",

                        "${instance.vehicle_number? instance.vehicle_number : ''}",
                        "${instance.name? instance.name : ''}",
                        "${instance.loading_capacity? instance.loading_capacity : ''}",
                        "${instance.dpName? instance.dpName : ''}",
                        "${instance.truckHeight? instance.truckHeight : ''}",
                        "${instance.truckWidth? instance.truckWidth : ''}",
                        "${instance.truckLength? instance.truckLength : ''}"
            ]
            instants << obj
            counter++
        };
        return instants
    }

    public Object postCondition(Object params, Object object) {
        return getResultForUI(object)
    }
}
