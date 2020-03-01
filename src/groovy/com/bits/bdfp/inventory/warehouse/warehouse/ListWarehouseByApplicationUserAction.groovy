package com.bits.bdfp.inventory.warehouse.warehouse

import com.bits.bdfp.inventory.warehouse.WarehouseService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 11/29/15.
 */
@Component("listWarehouseByApplicationUserAction")
class ListWarehouseByApplicationUserAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    WarehouseService warehouseService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return warehouseService.listWarehouseByApplicationUser()
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }
}