package com.bits.bdfp.inventory.setup.salescommission

import com.bits.bdfp.inventory.setup.SalesCommissionService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mddelower.hossain on 2/8/2016.
 */
@Component("listBranchCommissionAmountAction")
class ListBranchCommissionAmountAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    SalesCommissionService salesCommissionService
    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            List result = salesCommissionService.listBranchCommissionByDP(params)
            return result;
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw  new RuntimeException(ex.message)
        }
    }
    
    public Object postCondition(Object params, Object object) {
        return null
    }
}
