package com.bits.bdfp.inventory.demandorder.approveprimarydemandorder

import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by maimuna.akter on 9/14/2015.
 */
@Component("listFinishedProductForSelectedPrimaryDemandOrderAction")
class ListFinishedProductForSelectedPrimaryDemandOrderAction extends Action {

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

            result = primaryDemandOrderService.getFinishedProductListForSelectedPrimaryDemandOrderExecuteForEdit(params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityListForEdit(objectList, start)
            Map output = [page: 1, total: 1, records: total, rows: objList]
            return output
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
            obj.cell = ["${instance.product ? instance.product : ''}",
                        "${instance.totalQuantity? instance.totalQuantity : ''}",
                        "${instance.newQuantity? instance.totalQuantity : ''}",
                        "${instance.qtyInLtr? instance.qtyInLtr : ''}",
                        "${instance.rate? instance.rate : ''}",
                        "${instance.totalAmount? instance.totalAmount : ''}"
            ]
            instants << obj
            counter++
        };
        return instants
    }
    private List wrapListInGridEntityListForEdit(objList, start) {
        List instants = []
        int counter = start + 1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = [ "${instance.id ? instance.id : ''}",
                        "${instance.primaryDemandOrderDetailsId ? instance.primaryDemandOrderDetailsId : ''}",
                        "${instance.product ? instance.product : ''}",
                        "${instance.totalQuantity? instance.totalQuantity : ''}",
                        "${instance.newQuantity? instance.newQuantity : ''}",
                        "${instance.rate? instance.rate : ''}",
                        "${instance.totalAmount? instance.totalAmount : ''}"
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
        Map output = [page: 1, total: 1, records: total, rows: objList]
        return output;
    }
}
