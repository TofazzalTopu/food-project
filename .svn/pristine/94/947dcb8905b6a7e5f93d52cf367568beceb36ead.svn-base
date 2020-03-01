package com.bits.bdfp.inventory.retailorder.processretailorder

import com.bits.bdfp.inventory.retailorder.RetailOrderService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 1/31/16.
 */
@Component("isAllowForProcessingAction")
class IsAllowForProcessingAction extends Action {
    public static final Log log = LogFactory.getLog(this)
    private final String MESSAGE_HEADER = 'Message'
    @Autowired
    RetailOrderService retailOrderService

    public Object preCondition(def params, Object object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            List result = retailOrderService.listRetailsOrderDetailsForProcessing(params)
            result.each{
                if(it.orderQuantity < it.availableQuantity){
                    return new Integer(1)
                }else{
                    return new Integer(0)
                }
            }
        }
        catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}
