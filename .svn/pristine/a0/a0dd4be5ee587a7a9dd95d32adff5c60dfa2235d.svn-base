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
@Component("deleteSecondaryOrderDetailsAction")
class DeleteSecondaryOrderDetailsAction extends Action {
    public static final Log log = LogFactory.getLog(ReadRetailOrderAction.class)
    private final String MESSAGE_HEADER = 'Message'
    private final String MESSAGE_SUCCESS = 'Product Deleted Successfully from Retail Order'

    @Autowired
    RetailOrderService retailOrderService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        boolean success = retailOrderService.deleteSecondaryOrderDetails(params)
        if(success){
            return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
        }else{
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Product Delete Failed from Retail Order")
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}
