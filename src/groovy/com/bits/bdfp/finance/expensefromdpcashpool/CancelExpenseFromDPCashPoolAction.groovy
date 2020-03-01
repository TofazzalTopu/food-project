package com.bits.bdfp.finance.expensefromdpcashpool

import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.finance.ExpenseFromDPCashPool
import com.bits.bdfp.finance.ExpenseFromDPCashPoolService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdtofazzal.hossain on 4/19/2017.
 */

@Component("cancelExpenseFromDPCashPoolAction")
class CancelExpenseFromDPCashPoolAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    private final String MESSAGE_HEADER = 'Cancel Payment'
    @Autowired
    ExpenseFromDPCashPoolService expenseFromDPCashPoolService
    @Autowired
    SessionFactory sessionFactory

    public Object preCondition(def Object object1,def Object object2) {
        return null
    }

    public Object postCondition(def Object object1,def Object object2) {
        return null
    }

    public Object execute(Object params, Object object) {
       try{
           ApplicationUser applicationUser = (ApplicationUser) object

           ExpenseFromDPCashPool expenseFromDPCashPool = ExpenseFromDPCashPool.read(Long.parseLong(params.id))
           if(expenseFromDPCashPool){
               expenseFromDPCashPool.isActive = false
               expenseFromDPCashPool.expenseAmount = 0.00
               expenseFromDPCashPool.userUpdated = applicationUser
           }

           String tableName = sessionFactory.getClassMetadata(ExpenseFromDPCashPool).tableName
           Journal journal = Journal.findByTransactionNoAndTableName(expenseFromDPCashPool.transactionNo, tableName)
           List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()

           if(journal){
               journal.isActive = false
               journal.userUpdated = applicationUser

               journalDetailsList = JournalDetails.findAllByJournal(journal)
               if(journalDetailsList && journalDetailsList.size()>0){
                   journalDetailsList.each {
                       it.isActive = false
                       it.debitAmount = 0.00
                       it.creditAmount = 0.00
                       it.userUpdated = applicationUser
                   }
               }
           }

           Map map = [:]
           map.put("expenseFromDPCashPool", expenseFromDPCashPool)
           map.put("journal", journal)
           map.put("journalDetailsList", journalDetailsList)

           int count = expenseFromDPCashPoolService.cancelExpense(map)

           if(count > 0){
               return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, "Expense successfully canceled for the transaction no: " + expenseFromDPCashPool.transactionNo)
           }else{
               return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Expense cancel failed for the transaction no: " + expenseFromDPCashPool.transactionNo)
           }
       }catch(Exception ex){
           log.error(ex.message)
           throw new RuntimeException("Exception-${ex.message}")
       }

    }
}
