package com.bits.bdfp.finance.cashreceivedfrombranch

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.finance.CashReceivedFromBranch
import com.bits.bdfp.finance.CashReceivedFromBranchService

@Component("deleteCashReceivedFromBranchAction")
class DeleteCashReceivedFromBranchAction extends Action {
    public static final Log log = LogFactory.getLog(DeleteCashReceivedFromBranchAction.class)
    private final String MESSAGE_HEADER = 'Delete Cash Received From Branch'
    private final String MESSAGE_SUCCESS = 'Cash Received From Branch deleted successfully'
    private final String MESSAGE_FAILURE = 'Cash Received From Branch delete failed'

    @Autowired
    CashReceivedFromBranchService cashReceivedFromBranchService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            CashReceivedFromBranch cashReceivedFromBranch = cashReceivedFromBranchService.read(Long.parseLong(params.id))
            cashReceivedFromBranchService.delete(cashReceivedFromBranch)
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