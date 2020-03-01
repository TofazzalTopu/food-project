package com.bits.bdfp.inventory.setup.salescommission

import com.bits.bdfp.inventory.setup.SalesCommissionService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 5/4/2016.
 */
@Component("fetchCommissionForCustomerAction")
class FetchCommissionForCustomerAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    SalesCommissionService salesCommissionService

    public Object preCondition(Object params, Object object) {
        return null
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            List commission = salesCommissionService.listCommission(params)
//            List percent = salesCommissionService.commissionPercent(params)
            List previous = salesCommissionService.previousCommission(params)
//            return (previous?previous[0].commission:0)+((commission[0].commission != null?commission[0].commission:0)*(percent?percent[0].sales_man_commission:1)/100)
            return (previous?previous[0].commission:0)+(commission[0].commission != null?commission[0].commission:0)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
