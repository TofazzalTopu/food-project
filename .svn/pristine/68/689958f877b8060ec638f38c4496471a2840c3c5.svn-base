package com.bits.bdfp.inventory.warehouse.subwarehouse

import com.bits.bdfp.inventory.warehouse.SubWarehouse
import com.bits.bdfp.inventory.warehouse.SubWarehouseService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createSubWarehouseAction")
class CreateSubWarehouseAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    SubWarehouseService subWarehouseService

    public Object preCondition(Object user, Object object) {
        try {
            SubWarehouse subWarehouse = (SubWarehouse) object
            ApplicationUser applicationUser = (ApplicationUser) user
            subWarehouse.dateCreated = new Date()
            subWarehouse.userCreated = applicationUser
            if (!subWarehouse.validate()) {
                return null
            }
            return subWarehouse
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object execute(Object params, Object object) {
        try {
            return subWarehouseService.create(object)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}