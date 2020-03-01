package com.bits.bdfp.finance.customerpayment

import com.bits.bdfp.finance.CustomerPaymentService
import com.docu.common.Action
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 7/21/16.
 */
@Component("fetchCashPoolBalanceAction")
class FetchCashPoolBalanceAction  extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerPaymentService customerPaymentService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return customerPaymentService.fetchCashPoolBalance(params)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object fetchCashPoolBalanceForCashInHandFromCOA(Object params, Object Object) {
        try {
            List objectList

//                 objectList = customerPaymentService.fetchCashPoolBalanceForCashInHandFromCOA(params)
                objectList = customerPaymentService.fetchCashPoolBalanceForCashInHandFromCOANonBankVault(params)

            return objectList
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }
}