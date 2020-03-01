package com.bits.bdfp.inventory.retailorder.consolidateretailorder

import com.bits.bdfp.inventory.retailorder.RetailOrderService
import com.docu.common.Action
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 1/19/16.
 */
@Component("deleteMultipleRetailOrderAction")
class DeleteMultipleRetailOrderAction extends Action {
    public static final Log log = LogFactory.getLog(this)
    private final String MESSAGE_HEADER = 'Delete Retail Order'
    private final String MESSAGE_SUCCESS = 'Retail Order(s) Deleted Successfully'
    private final String MESSAGE_FAILURE = 'Retail Order(s) Delete Failed'

    @Autowired
    RetailOrderService retailOrderService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            Boolean isSuccess =  retailOrderService.deleteMultipleRetailOrder(params)
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