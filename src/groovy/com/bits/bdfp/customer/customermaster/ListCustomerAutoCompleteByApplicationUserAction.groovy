package com.bits.bdfp.customer.customermaster

import com.bits.bdfp.customer.CustomerMasterService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 9/27/15.
 */
@Component("listCustomerAutoCompleteByApplicationUserAction")
class ListCustomerAutoCompleteByApplicationUserAction extends Action{
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerMasterService customerMasterService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return customerMasterService.listCustomerByApplicationUser(params)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
    public Object listCustomerByApplicationUserForNewPrimaryDemandOrder(Object params, Object object) {
        try {
            return customerMasterService.listCustomerByApplicationUserForNewPrimaryDemandOrder(params)
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
