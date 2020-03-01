package com.bits.bdfp.bill.createbill


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import com.docu.commons.CommonConstants
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.bill.CreateBill
import com.bits.bdfp.bill.CreateBillService



@Component("updateCreateBillAction")
class UpdateCreateBillAction extends Action {
    public static final Log log = LogFactory.getLog(UpdateCreateBillAction.class)
    private final String MESSAGE_HEADER = 'Update Create Bill'
    private final String MESSAGE_SUCCESS = 'Bill Updated Successfully'

    @Autowired
    CreateBillService createBillService
    

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            CreateBill createBill = createBillService.read(Long.parseLong(params.id))
            createBill.properties = params
            
            
           /* if(Long.parseLong(params.version) > createBill.version) {
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, CommonConstants.ALREADY_UPDATED)
            }*/
            
            
            if (!createBill.validate()) {
                this.getValidationErrorMessage(createBill)
            }
            createBillService.update(createBill)
            return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}
