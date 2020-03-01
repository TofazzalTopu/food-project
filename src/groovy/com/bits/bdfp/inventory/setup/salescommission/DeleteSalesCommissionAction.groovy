package com.bits.bdfp.inventory.setup.salescommission

import com.bits.bdfp.common.DivisionService
import com.bits.bdfp.inventory.setup.SalesCommissionService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("deleteSalesCommissionAction")
class DeleteSalesCommissionAction extends Action {

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    SalesCommissionService salesCommissionService

    public Object preCondition(Object params, Object object) {
        try {
            return salesCommissionService.read(Long.parseLong(params.id.toString()))
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return salesCommissionService.delete(object)
        }
        catch (Exception ex) {
            log.error(ex.message)
            return new Integer(0)
        }
    }

}