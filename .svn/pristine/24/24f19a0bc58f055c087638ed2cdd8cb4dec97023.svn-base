package com.bits.bdfp.inventory.warehouse.subwarehouse

import com.bits.bdfp.inventory.warehouse.SubWarehouseService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by prianka.adhikary on 9/15/2015.
 */
@Component("listSubWarehouseByWarehouseAction")
class ListSubWarehouseByWarehouseAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    SubWarehouseService subWarehouseService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return subWarehouseService.listSubWarehouseByWarehouse(params)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }
}
