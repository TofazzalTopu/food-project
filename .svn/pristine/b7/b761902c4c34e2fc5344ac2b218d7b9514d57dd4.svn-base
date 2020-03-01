package com.bits.bdfp.customer.customer

import com.bits.bdfp.customer.CustomerMasterService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 9/10/2015.
 */
@Component("flexListCustomerAction")
class FlexListCustomerAction extends Action{
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerMasterService customerMasterService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return customerMasterService.getFlexBoxCustomerList(params)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }
}
