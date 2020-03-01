package com.bits.bdfp.inventory.setup.poscustomer

import com.bits.bdfp.inventory.setup.PosCustomerService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 12/20/15.
 */
@Component("readPosCustomerByApplicationUserAction")
class ReadPosCustomerByApplicationUserAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    PosCustomerService posCustomerService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return posCustomerService.posCustomerByApplicationUser()
        } catch (Exception ex) {
            log.error(ex.message)
            return ex.message
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }
}