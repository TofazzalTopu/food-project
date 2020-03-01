package com.bits.bdfp.inventory.demandorder.processanorder

import com.bits.bdfp.inventory.demandorder.ProcessOrderService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by shazadur.rahman on 9/15/2015.
 */
@Component("updateSecondaryDemandOrderProcessAction")
class UpdateSecondaryDemandOrderProcessAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    ProcessOrderService processOrderService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try{
            init(params)
            Map result = [:]
            List objectList = null

            result = processOrderService.updateSecondaryDemandOrderDetails(this,params)
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
        int counter = start + 1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = [
                        "${counter}",
                        "${instance.id ? instance.id : ''}",
                        "${instance.pid ? instance.pid : ''}",
                        "${instance.name? instance.name : ''}",
                        "${instance.code? instance.code : ''}",
                        "${instance.order_qty? instance.order_qty : 0}",
                        "${instance.qty? instance.qty : 0}",
                        "${instance.rate? instance.rate : 0}",
                        "${instance.amount? instance.amount : 0}"
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
