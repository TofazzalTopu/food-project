package com.bits.bdfp.inventory.demandorder.primarydemandorder

import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 9/21/15.
 */
@Component("listOrderDetailsByPrimaryOrderAction")
class ListOrderDetailsByPrimaryOrderAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try{
            Map result = [:]
            List objectList = null

            result = primaryDemandOrderService.ListPrimaryOrderDetailsGrid(params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityList(objectList, start)
            return [page: 1, total: objList.size(), records: objList.size(), rows: objList]
        }
        catch (Exception ex) {
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
            obj.cell = [
                    "${instance.id ? instance.id : ''}",
                    "${instance.primaryDemandOrderDetailsId? instance.primaryDemandOrderDetailsId : ''}",
                    "${instance.productCode? instance.productCode : ''}",
                    "${instance.productName? instance.productName : ''}",
                    "${instance.quantity? instance.quantity : ''}",
                    "${instance.rate? instance.rate : ''}",
                    "${instance.amount? instance.amount : ''}"
            ]
            instants << obj
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
