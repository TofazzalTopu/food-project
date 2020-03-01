package com.bits.bdfp.finance.customerpayment

import com.bits.bdfp.finance.CustomerPaymentService
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 5/11/16.
 */
@Component("listCustomerByDPAction")
class ListCustomerByDPAction extends Action {

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerPaymentService customerPaymentService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            DistributionPoint distributionPoint = DistributionPoint.read(Long.parseLong(params.distributionPointId))
            if(!distributionPoint){
                throw new RuntimeException("Factory/DP is not selected")
            }
            return customerPaymentService.fetchCustomerListByDP(params, distributionPoint)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }
}
