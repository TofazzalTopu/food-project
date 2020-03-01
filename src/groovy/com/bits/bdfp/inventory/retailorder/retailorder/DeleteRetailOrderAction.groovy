package com.bits.bdfp.inventory.retailorder.retailorder

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.commons.UserMessageBuilder
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.inventory.retailorder.RetailOrder
import com.bits.bdfp.inventory.retailorder.RetailOrderService

@Component("deleteRetailOrderAction")
class DeleteRetailOrderAction extends Action {
    public static final Log log = LogFactory.getLog(DeleteRetailOrderAction.class)
    private final String MESSAGE_HEADER = 'Delete Retail Order'
    private final String MESSAGE_SUCCESS = 'Retail Order Deleted Successfully'
    private final String MESSAGE_FAILURE = 'Retail Order Delete Failed'

    @Autowired
    RetailOrderService retailOrderService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            RetailOrder retailOrder = retailOrderService.read(Long.parseLong(params.id))
            retailOrderService.delete(retailOrder)
            return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, MESSAGE_FAILURE)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}