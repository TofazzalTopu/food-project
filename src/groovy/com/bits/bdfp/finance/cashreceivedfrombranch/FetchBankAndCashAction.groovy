package com.bits.bdfp.finance.cashreceivedfrombranch

import com.bits.bdfp.finance.CashReceivedFromBranchService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 4/20/2016.
 */
@Component("fetchBankAndCashAction")
class FetchBankAndCashAction extends Action{
    @Autowired
    CashReceivedFromBranchService cashReceivedFromBranchService

    Message message

    @Override
    public Object preCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    public Object postCondition(Object params, Object object) {
        return null
    }

    @Override
    public Object execute(Object params, Object Object) {
        try {
            Map map = cashReceivedFromBranchService.fetchBankAndCash(params)
            return map
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }
}
