package com.bits.bdfp.finance.expensefromdpcashpool

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.finance.ExpenseFromDPCashPool
import com.bits.bdfp.finance.ExpenseFromDPCashPoolService

@Component("deleteExpenseFromDPCashPoolAction")
class DeleteExpenseFromDPCashPoolAction extends Action {
    public static final Log log = LogFactory.getLog(DeleteExpenseFromDPCashPoolAction.class)
    private final String MESSAGE_HEADER = 'Delete Expense From DPC ash Pool'
    private final String MESSAGE_SUCCESS = 'Expense From DPC ash Pool deleted successfully'
    private final String MESSAGE_FAILURE = 'Expense From DPC ash Pool delete failed'

    @Autowired
    ExpenseFromDPCashPoolService expenseFromDPCashPoolService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            ExpenseFromDPCashPool expenseFromDPCashPool = expenseFromDPCashPoolService.read(Long.parseLong(params.id))
            expenseFromDPCashPool.isActive=0
            expenseFromDPCashPoolService.update(expenseFromDPCashPool)
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