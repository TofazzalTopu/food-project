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
@Component("listSalesmanByBranchAction")
class ListSalesmanByBranchAction extends Action{
        private static final Log log = LogFactory.getLog(this)
        @Autowired
        SubWarehouseService subWarehouseService

        public Object preCondition(Object params, Object object) {
            //not need to implement
            return null
        }

        public Object execute(Object params, Object object) {
            try{
                List result = subWarehouseService.getSalesmanListByBranch(params)
                return result
            }
            catch (Exception ex) {
                log.error(ex.message)
                return  null
            }
        }

        public Object postCondition(Object params, Object object) {
            return null
        }
}
