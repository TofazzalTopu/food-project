package com.bits.bdfp.bill.createbill

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.bill.CreateBill
import com.bits.bdfp.bill.CreateBillService

@Component("deleteCreateBillAction")
class DeleteCreateBillAction extends Action {
    public static final Log log = LogFactory.getLog(DeleteCreateBillAction.class)
    private final String MESSAGE_HEADER = 'Delete Create Bill'
    private final String MESSAGE_SUCCESS = 'Create Bill deleted successfully'
    private final String MESSAGE_FAILURE = 'Create Bill delete failed'

    @Autowired
    CreateBillService createBillService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            CreateBill createBill = createBillService.read(Long.parseLong(params.id))
            createBillService.delete(createBill)
            return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, MESSAGE_FAILURE)
        }
    }
    public Object executeDeleteBill(def params, def object) {
        return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
    }

    public Object postCondition(def params, def object) {
        return null
    }
}