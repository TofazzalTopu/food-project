package com.bits.bdfp.finance.expensefromdpcashpool

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.finance.ExpenseFromDPCashPoolService

@Component("searchExpenseFromDPCashPoolAction")
class SearchExpenseFromDPCashPoolAction extends Action {
    public static final Log log = LogFactory.getLog(SearchExpenseFromDPCashPoolAction.class)

    @Autowired
    ExpenseFromDPCashPoolService expenseFromDPCashPoolService

    public Object preCondition(Object params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            String strFieldName =  params.fieldName.toString()
            String strFieldValue = params.fieldValue.toString()
            if(strFieldName.length() == 0){
                return null
            }
            return expenseFromDPCashPoolService.search(strFieldName, strFieldValue)
        }catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    protected Object fetchCashPoolList(Object params, Object object2) {
        try {
            List list

            list = expenseFromDPCashPoolService.fetchCashPoolList(params)

            return list
        }catch(Exception ex){
            log.error(ex.message)
            return null
        }
    }
    protected Object fetchExpenditureHeads(Object params, Object object2) {
        try {
            List list

            list = expenseFromDPCashPoolService.fetchExpenditureHeads()


            return list
        }catch(Exception ex){
            log.error(ex.message)
            return null
        }
    }

    protected Object fetchDistributionPointList(Object params, Object object2) {
        try {
            List list

            list = expenseFromDPCashPoolService.fetchDistributionPointList()


            return list
        }catch(Exception ex){
            log.error(ex.message)
            return null
        }
    }


    public Object postCondition(Object params, def object) {
        return null
    }
}