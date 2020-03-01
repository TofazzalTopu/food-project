package com.bits.bdfp.inventory.retailorder.processretailorder

import com.bits.bdfp.customer.CustomerStockService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 4/20/16.
 */
@Component("listCustomerAvailableStockAction")
class ListCustomerAvailableStockAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerStockService customerStockService

    public Object preCondition(Object params, Object object) {
        return null
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return customerStockService.customerAvailableStock(params)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}