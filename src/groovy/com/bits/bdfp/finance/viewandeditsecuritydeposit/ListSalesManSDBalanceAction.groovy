package com.bits.bdfp.finance.viewandeditsecuritydeposit

import com.bits.bdfp.finance.CustomerPaymentService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 9/27/16.
 */
@Component("listSalesManSDBalanceAction")
class ListSalesManSDBalanceAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerPaymentService customerPaymentService

    public Object preCondition(Object params, Object object) {
        return null
    }

    public List execute(Object params, Object object) {
        try{
            List objectList = null
            objectList = customerPaymentService.listSalesManSDBalance(params)
            return objectList
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object objList, Object object) {
        return null
    }

}
