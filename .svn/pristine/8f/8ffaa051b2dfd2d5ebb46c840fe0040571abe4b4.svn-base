package com.bits.bdfp.inventory.demandorder.primarydemandorder

import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createPrimaryDemandOrderAction")
class CreatePrimaryDemandOrderAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService

    public Object preCondition(Object params, Object object) {
        try {
            PrimaryDemandOrder primaryDemandOrder = (PrimaryDemandOrder) object
            if (!primaryDemandOrder.validate()) {
                return null
            }
            return primaryDemandOrder
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object execute(Object params, Object object) {
        try {
            return primaryDemandOrderService.create(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}