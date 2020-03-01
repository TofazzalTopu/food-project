package com.bits.bdfp.inventory.finishgood

import com.bits.bdfp.inventory.warehouse.FinishGoodWarehouseService
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("finishProductForReverseFinishGoodAction")
class FinishProductForReverseFinishGoodAction extends Action {
  private static final Log log = LogFactory.getLog(this)
  @Autowired
  FinishGoodWarehouseService finishGoodWarehouseService

   public Object preCondition(Object params, Object object) {
    //not need to implement
    return null
  }

    public Object execute(Object params, Object object) {
        try {
            init(params)
            Map result = [:]
            List objectList = null

            result = finishGoodWarehouseService.productListForReverseFinishGood(params)
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
            return null
        }
    }


    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        int counter = start + 1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                        "${instance.id ? instance.id : ''}",
                        "${instance.id ? instance.id : ''}",
                        "${instance.name ? instance.name : ''}",
                        "${instance.quantity ? instance.quantity : 0}",
                        "${instance.uom ? instance.uom : ''}",
                        "${instance.batch_no ? instance.batch_no : ''}",
                        "${instance.cost ? instance.cost : ''}",
                        "${instance.ware_house_name ? instance.ware_house_name : ''}",
                        "${instance.sub_ware_house_name ? instance.sub_ware_house_name : ''}",
                        "${instance.product_ref_no ? instance.product_ref_no : ''}",
                        "${instance.good_id ? instance.good_id : ''}"

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
