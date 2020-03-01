package com.bits.bdfp.finance.customerpayment

import com.bits.bdfp.finance.AdjustMiscellaneousFeesWithReceivableService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by liton.miah on 5/12/2016.
 */
@Component("utilAdjustMiscellaneousFeesWithReceivableAction")
class UtilAdjustMiscellaneousFeesWithReceivableAction extends Action{
    private static final Log log = LogFactory.getLog(this)

    @Autowired
    AdjustMiscellaneousFeesWithReceivableService adjustMiscellaneousFeesWithReceivableService

    public Object preCondition(Object params, Object object) {
        return null
    }

    public Object execute(Object params, Object object) {
        return null
    }

    public Object postCondition(Object params, Object object) {
        return null
    }

    public List getCustomerInfoByCustomerId(Long customerId){
        return  adjustMiscellaneousFeesWithReceivableService.getCustomerInfoByCustomerId(customerId)
    }

}
