package com.bits.bdfp.inventory.retailorder.consolidateretailorder

import com.bits.bdfp.inventory.retailorder.RetailOrderService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 1/19/16.
 */
@Component("listSecondaryOrderNoAction")
class ListSecondaryOrderNoAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    RetailOrderService retailOrderService

    public Object preCondition(Object params, Object object) {
        return null
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return retailOrderService.secondaryOrderNoAutoComplete(params)
        }
        catch (Exception ex) {
            log.error(ex.message)
        }
    }
}
