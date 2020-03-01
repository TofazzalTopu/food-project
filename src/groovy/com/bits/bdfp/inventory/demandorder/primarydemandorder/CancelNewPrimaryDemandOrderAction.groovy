package com.bits.bdfp.inventory.demandorder.primarydemandorder

import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.docu.common.Action
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 9/28/15.
 */
@Component("cancelNewPrimaryDemandOrderAction")
class CancelNewPrimaryDemandOrderAction extends Action{
    Message message
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            boolean success = primaryDemandOrderService.cancelNewPrimaryOrder(params)
            return this.getMessage('Cancel New Primary Demand Order', Message.SUCCESS, "Primary Demand Order Canceled Successfully")
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage('Cancel New Primary Demand Order', Message.ERROR, ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }
}
