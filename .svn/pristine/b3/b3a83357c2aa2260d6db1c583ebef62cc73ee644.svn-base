package com.bits.bdfp.finance.customerpayment

import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.finance.CustomerPaymentService
import com.bits.bdfp.finance.DepositCashToDepositPool
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by liton.miah on 3/20/2017.
 */
@Component("rollbackDepositToHoAction")
class RollbackDepositToHoAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    private final String MESSAGE_HEADER = 'Rollback Deposit to HO'

    @Autowired
    CustomerPaymentService customerPaymentService
    Message message

    public Object preCondition(Object params, Object object){
        return null
    }

    public Object execute(Object params, Object object){
        try{
            ApplicationUser applicationUser = (ApplicationUser) object
            DepositCashToDepositPool depositCashToDepositPool = DepositCashToDepositPool.findByTransactionNo(params.transactionNo)

            Journal journal = Journal.findByTransactionNo(depositCashToDepositPool.transactionNo)
            journal.isActive = false
            journal.userUpdated = applicationUser
            journal.lastUpdated = new Date()

            List<JournalDetails> journalDetailsList = JournalDetails.findAllByJournal(journal)
            journalDetailsList.each {
                it.isActive = false
                it.debitAmount = 0.00
                it.creditAmount = 0.00
                it.userUpdated = applicationUser
                it.lastUpdated = new Date()
            }

            Map map = [:]
            map.put("depositCashToDepositPool",depositCashToDepositPool)
            map.put("journal",journal)
            map.put("journalDetailsList",journalDetailsList)

            int count = customerPaymentService.rollbackDepositToHo(map)

            if(count > 0){
                message = this.getMessage(MESSAGE_HEADER, Message.SUCCESS, "Rollback successfully done for the transaction no "+depositCashToDepositPool.transactionNo)
            }else{
                message = this.getMessage(MESSAGE_HEADER, Message.ERROR, "Rollback failed for the transaction no "+depositCashToDepositPool.transactionNo)
            }

            return message

        }catch(Exception ex){
            log.error(ex.message)
            throw new RuntimeException("Exception-${ex.message}")
        }
    }

    public Object postCondition(Object params, Object object){
        return null
    }
}
