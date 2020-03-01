package com.bits.bdfp.inventory.setup.salescommission

import com.bits.bdfp.inventory.setup.SalesCommissionService
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Action
import com.docu.common.GridEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mddelower.hossain on 1/27/2016.
 */

@Component("fetchDataProductGridAction")
class FetchDataProductGridAction extends Action {

    @Autowired
    SalesCommissionService salesCommissionService

    Object preCondition(def Object object1, def Object object2) {
        return null
    }

    Object execute(Object params, Object object2) {
        try {
            Map result = [:]
            List objectList = null
            result = salesCommissionService.fetchDataGridProductAction()
            if (result) { // in case: normal list
                objectList = result.objList
            }
            List objList = this.wrapListInGridEntityList(objectList, start)
            return objList
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
    Map getResultForUI(List objList) {
//        int pageCount = 1
//        if (resultPerPage < 0) {
//            pageCount = 1
//            resultPerPage=total
//        }
//        else {
//            if (total > resultPerPage) {
//                pageCount = Math.ceil(total / resultPerPage)
//            }
//        }
//        Map output = [page: pageNum, total: pageCount, records: total, rows: objList]
        Map output = [rows: objList]
        return output;
    }
    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        int counter = start + 1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.productInfoId
            obj.cell = ["${instance.productInfoId ? instance.productInfoId : ''}",
                        "${instance.productInfo? instance.productInfo : ''}"
            ]
            instants << obj
            counter++
        };
        return instants
    }

//    Object postCondition(def Object object1, def Object object2) {
//        return null
//    }

    public Object postCondition(Object params, Object object) {
        return getResultForUI(object)
    }

}
