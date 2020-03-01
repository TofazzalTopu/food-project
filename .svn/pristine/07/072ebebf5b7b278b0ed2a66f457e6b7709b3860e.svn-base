package com.bits.bdfp.inventory.retailorder.retailorder

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.inventory.retailorder.RetailOrderService

@Component("readRetailOrderAction")
class ReadRetailOrderAction extends Action {
    public static final Log log = LogFactory.getLog(ReadRetailOrderAction.class)

    @Autowired
    RetailOrderService retailOrderService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        return retailOrderService.read(Long.parseLong(params.id))
//        Map objectData = ObjectUtil.getPersistenceData(retailOrder)
//        return objectData
    }

    public Object postCondition(def params, def object) {
        return null
    }
}