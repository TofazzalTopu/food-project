package com.bits.bdfp.inventory.finishgood

import com.bits.bdfp.inventory.warehouse.FinishGoodWarehouseService
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by prianka.adhikary on 9/15/2015.
 */
@Component("onHandQtyBatchListAction")
class OnHandQtyBatchListAction extends Action{
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    FinishGoodWarehouseService finishGoodWarehouseService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try{
            ApplicationUser applicationUser = (ApplicationUser)object
            init(params)
            Map result = [:]
            List objectList = null

            result = finishGoodWarehouseService.getListOnHandQtyBatch(this, params, applicationUser)
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
                    "${instance.id? instance.id : ''}",
                    "${instance.product_code? instance.product_code : ''}",
                    "${instance.product_name? instance.product_name : ''}",
                    "${instance.batch_no? instance.batch_no : ''}",
                    "${instance.quantity? instance.quantity : '0'}"
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
