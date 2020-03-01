package com.bits.bdfp.finance.expensefromdpcashpool

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.bits.bdfp.finance.ExpenseFromDPCashPool
import com.bits.bdfp.finance.ExpenseFromDPCashPoolService

@Component("readExpenseFromDPCashPoolAction")
class ReadExpenseFromDPCashPoolAction extends Action {
    public static final Log log = LogFactory.getLog(ReadExpenseFromDPCashPoolAction.class)

    @Autowired
    ExpenseFromDPCashPoolService expenseFromDPCashPoolService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        ExpenseFromDPCashPool expenseFromDPCashPool = expenseFromDPCashPoolService.read(Long.parseLong(params.id))
        Map objectData = ObjectUtil.getPersistenceData(expenseFromDPCashPool)
        return objectData
    }

    public Object postCondition(def params, def object) {
        return null
    }
}