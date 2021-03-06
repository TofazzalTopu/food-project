package com.bits.bdfp.inventory.product.externalproductstock

import com.bits.bdfp.inventory.product.ExternalProductStockService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdtofazzal.hossain on 1/16/2019.
 */
@Component('listExternalProductStockAction')
class ListExternalProductStockAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    ExternalProductStockService externalProductStockService

    protected Object preCondition(Object params, Object object) {
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            init(params)
            Map result = [:]
            List objectList = null;

            result = externalProductStockService.getListForGrid(this)
            if (result) {
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityList(objectList, start)
            return objList;
        } catch (Exception e) {
            log.error(e.message)
            return null
        }
    }

    public List wrapListInGridEntityList(objList, start) {
        List instants = []
        int counter = start + 1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = [
                    "${counter}",
                    "${instance.id ? instance.id : ''}",
                    "${instance.externalProduct ? instance.externalProduct : ''}",
                    "${instance.inQuantity ? instance.inQuantity : ''}",
                    "${instance.outQuantity ? instance.outQuantity : ''}"
            ]
            instants << obj
            counter++
        };

        return instants;
    }

    Map getResultForUI(List objList) {
        int pageCount = 1
        if (resultPerPage < 0) {
            pageCount = 0
            resultPerPage = total
        } else {
            if (total > resultPerPage) {
                pageCount = Math.ceil(total / resultPerPage)
            }
        }
        Map output = [page: pageNum, total: pageCount, records: total, rows: objList]
        return output;
    }

    public Object postCondition(Object params, Object object) {
        return getResultForUI(object)
    }
}
