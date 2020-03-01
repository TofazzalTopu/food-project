package com.bits.bdfp.finance.sdinterestcalculation

import com.bits.bdfp.finance.CustomerPaymentService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by liton.miah on 2/2/2016.
 */
@Component("sdInterestCalculationAction")
class SdInterestCalculationAction extends  Action{
    private static final Log log = LogFactory.getLog(this)

    @Autowired
    CustomerPaymentService customerPaymentService

    public Object preCondition(Object params, Object object) {
        return null
    }

    public Object execute(Object params, Object object) {
        return null
    }

    public Object postCondition(Object params, Object object) {
        return null
    }

    public List getDpDefaultCustomersIc(Object params){
        return customerPaymentService.getDpDefaultCustomersIc(Long.parseLong(params.customerId), params.date);
    }
}
