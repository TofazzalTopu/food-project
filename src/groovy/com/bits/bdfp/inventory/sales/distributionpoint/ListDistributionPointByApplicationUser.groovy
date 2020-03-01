package com.bits.bdfp.inventory.sales.distributionpoint

import com.bits.bdfp.inventory.sales.DistributionPointService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 12/5/15.
 */
@Component("listDistributionPointByApplicationUser")
class ListDistributionPointByApplicationUser extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    DistributionPointService distributionPointService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return distributionPointService.listDistributionPointByApplicationUser(params)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public List getListDistributionPointByDpWarehouseDefaultCustomer(){
        return distributionPointService.listDistributionPointByDpWarehouseDefaultCustomer();
    }
    public List getGeolocationByCustomer(Object params){
        return distributionPointService.getGeolocationByCustomer(params)
    }

}
