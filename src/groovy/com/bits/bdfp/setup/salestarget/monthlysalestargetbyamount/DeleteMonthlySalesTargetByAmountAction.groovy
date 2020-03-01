package com.bits.bdfp.setup.salestarget.monthlysalestargetbyamount

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.setup.salestarget.MonthlySalesTargetByAmount
import com.bits.bdfp.setup.salestarget.MonthlySalesTargetByAmountService

@Component("deleteMonthlySalesTargetByAmountAction")
class DeleteMonthlySalesTargetByAmountAction extends Action {
    public static final Log log = LogFactory.getLog(DeleteMonthlySalesTargetByAmountAction.class)
    private final String MESSAGE_HEADER = 'Delete Monthly Sales Target By Amount'
    private final String MESSAGE_SUCCESS = 'Monthly Sales Target By Amount deleted successfully'
    private final String MESSAGE_FAILURE = 'Monthly Sales Target By Amount delete failed'

    @Autowired
    MonthlySalesTargetByAmountService monthlySalesTargetByAmountService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            MonthlySalesTargetByAmount monthlySalesTargetByAmount = monthlySalesTargetByAmountService.read(Long.parseLong(params.id))
            monthlySalesTargetByAmountService.delete(monthlySalesTargetByAmount)
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