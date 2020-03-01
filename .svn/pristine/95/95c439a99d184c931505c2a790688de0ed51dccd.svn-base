package com.bits.bdfp.inventory.warehouse.salesmanstock

import com.bits.bdfp.inventory.warehouse.SubWarehouseService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by liton.miah on 3/15/2017.
 */
@Component("listSalesmanStockAction")
class ListSalesmanStockAction extends Action{
        private static final Log log = LogFactory.getLog(this)
        @Autowired
        SubWarehouseService subWarehouseService

        public Object preCondition(Object params, Object object) {
            //not need to implement
            return null
        }

        public Object execute(Object params, Object object) {
            try{
                init(params)
                Map result = [:]
                List objectList = null

                result = subWarehouseService.getSalesmanStocList(params)
                if (result) { // in case: normal list
                    objectList = result.objList
                    total = result.count
                }
                List objList = this.wrapListInGridEntityList(objectList, start)
                Map output = [page: 1, total: 1, records: total, rows: objList]
                return output;
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
                obj.cell = ["${counter}",
                            "${instance.id ? instance.id : ''}",
                            "${instance.productId? instance.productId : ''}",
                            "${instance.productName? instance.productName : ''}",
                            "${instance.productCode? instance.productCode : ''}",
                            "${instance.availableQuantity? instance.availableQuantity : ''}"
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
