package com.bits.bdfp.inventory.retailorder.consolidateretailorder

import com.bits.bdfp.inventory.retailorder.RetailOrderService
import com.bits.bdfp.inventory.retailorder.retailorder.ReadRetailOrderAction
import com.docu.common.Action
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 1/19/16.
 */
@Component("submitSecondaryOrdersAction")
class SubmitSecondaryOrdersAction extends Action {
    public static final Log log = LogFactory.getLog(ReadRetailOrderAction.class)
    private final String MESSAGE_HEADER = 'Message'
    private final String MESSAGE_SUCCESS = 'Secondary Order(s) Submitted Successfully'

    @Autowired
    RetailOrderService retailOrderService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            boolean success = retailOrderService.submitSecondaryOrders(params)
            if(success){
                return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
            }else{
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Secondary Order(s) Submission Failed")
            }
        }catch (Exception ex){
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}