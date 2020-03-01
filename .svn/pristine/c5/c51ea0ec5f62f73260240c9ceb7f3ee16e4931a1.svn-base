package com.bits.bdfp.inventory.sales.distributionpoint

import com.bits.bdfp.inventory.sales.DistributionPointService
import com.bits.bdfp.inventory.warehouse.SubWarehouseService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by prianka.adhikary on 9/16/2015.
 */
@Component("loadDistributionPointByEnterpriseAction")
class LoadDistributionPointByEnterpriseAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
     DistributionPointService distributionPointService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return distributionPointService.listDistributionPointByEnterprise(params)
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
