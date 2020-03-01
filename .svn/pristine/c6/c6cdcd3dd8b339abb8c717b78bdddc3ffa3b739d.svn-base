package com.bits.bdfp.inventory.setup.salescommission

import com.bits.bdfp.common.Division
import com.bits.bdfp.common.DivisionService
import com.bits.bdfp.inventory.setup.SalesCommission
import com.bits.bdfp.inventory.setup.SalesCommissionService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("updateSalesCommissionAction")
class UpdateSalesCommissionAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    SalesCommissionService SalesCommissionService

    public Object preCondition(Object params, Object object) {
        ApplicationUser applicationUser=(ApplicationUser)object
        SalesCommission SalesCommission = SalesCommission.read(Long.parseLong(params?.id?.toString()))
        SalesCommission.properties = params
        SalesCommission.userUpdated=applicationUser
        if (!SalesCommission.validate()) {
            return null
        }
        return SalesCommission
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return SalesCommissionService.update(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }
}
