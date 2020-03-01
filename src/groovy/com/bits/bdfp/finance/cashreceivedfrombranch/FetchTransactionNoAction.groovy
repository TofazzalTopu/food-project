package com.bits.bdfp.finance.cashreceivedfrombranch

import com.bits.bdfp.finance.CashReceivedFromBranchService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 4/20/2016.
 */
@Component("fetchTransactionNoAction")
class FetchTransactionNoAction extends Action{
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
            List list = cashReceivedFromBranchService.fetchTransactionNo(params)
            return list
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
