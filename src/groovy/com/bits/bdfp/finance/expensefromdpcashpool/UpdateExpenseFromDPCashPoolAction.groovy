package com.bits.bdfp.finance.expensefromdpcashpool


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import com.docu.commons.CommonConstants
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.finance.ExpenseFromDPCashPool
import com.bits.bdfp.finance.ExpenseFromDPCashPoolService


import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService


@Component("updateExpenseFromDPCashPoolAction")
class UpdateExpenseFromDPCashPoolAction extends Action {
    public static final Log log = LogFactory.getLog(UpdateExpenseFromDPCashPoolAction.class)
    private final String MESSAGE_HEADER = 'Update Expense From DPC ash Pool'
    private final String MESSAGE_SUCCESS = 'Expense From DPC ash Pool Updated Successfully'

    @Autowired
    ExpenseFromDPCashPoolService expenseFromDPCashPoolService
    
    @Autowired
    SpringSecurityService springSecurityService
    

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            ExpenseFromDPCashPool expenseFromDPCashPool = expenseFromDPCashPoolService.read(Long.parseLong(params.id))
            expenseFromDPCashPool.properties = params
            
            
         /*   if(Long.parseLong(params.version) > expenseFromDPCashPool.version) {
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, CommonConstants.ALREADY_UPDATED)
            }*/
            
            
            expenseFromDPCashPool.userUpdated = (ApplicationUser) springSecurityService?.getCurrentUser()
            
            if (!expenseFromDPCashPool.validate()) {
                this.getValidationErrorMessage(expenseFromDPCashPool)
            }
            expenseFromDPCashPoolService.update(expenseFromDPCashPool)
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
