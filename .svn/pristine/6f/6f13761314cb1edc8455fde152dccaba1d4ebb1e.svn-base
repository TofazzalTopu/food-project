package com.bits.bdfp.inventory.demandorder.secondarydemandorder

import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderService
import com.bits.bdfp.settings.BusinessUnitConfigurationService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by prianka.adhikary on 9/13/2015.
 */
@Component("listForUpdateDemandOrderAction")
class ListForUpdateDemandOrderAction extends Action{
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    SecondaryDemandOrderService secondaryDemandOrderService
    @Autowired
    BusinessUnitConfigurationService businessUnitConfigurationService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Map execute(Object object, Object params) {
        try {
            ApplicationUser applicationUser=(ApplicationUser) object
            List enterpriseList=businessUnitConfigurationService.enterpriseList(applicationUser)
            List listForUpdate= secondaryDemandOrderService.listForUpdate(applicationUser,params)
            return[enterpriseList:enterpriseList,listForUpdate:listForUpdate]
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
