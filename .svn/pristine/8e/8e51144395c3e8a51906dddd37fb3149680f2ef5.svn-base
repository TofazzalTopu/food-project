package com.bits.bdfp.inventory.warehouse.registerfinishgood

import com.bits.bdfp.inventory.warehouse.RegisterFinishGoodService
import com.docu.common.Action
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 11/29/15.
 */
@Component("receiveFinishGoodAction")
class ReceiveFinishGoodAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    RegisterFinishGoodService registerFinishGoodService
    String title = "Receive Finish Good"

    public Object preCondition(Object params, Object object) {
        try {
            /******** Manual validation *******/
            if(!params.invoiceId){
                return this.getMessage(title, Message.ERROR, "Invoice is not selected")
            }
            if(!params.warehouseId){
                return this.getMessage(title, Message.ERROR, "Inventory is not selected")
            }

            if(!params.subWarehouseId){
                return this.getMessage(title, Message.ERROR, "Sub Inventory is not selected")
            }
            return this.getMessage(title, Message.SUCCESS, this.SUCCESS_SAVE)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(title, Message.ERROR, "Exception-${ex.message}")
        }
    }

    public Message execute(Object params, Object object) {
        try {
            Object result = registerFinishGoodService.receiveFinishGood(params)
            if (result) {
                return this.getMessage(title, Message.SUCCESS, "Finish Good Received Successfully")
            } else {
                return this.getMessage(title, Message.ERROR, this.FAIL_SAVE)
            }
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(title, Message.ERROR, ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}