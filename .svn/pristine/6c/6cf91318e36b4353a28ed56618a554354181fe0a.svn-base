package com.bits.bdfp.inventory.demandorder.secondarydemandorder

import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by prianka.adhikary on 9/22/2015.
 */
@Component("loadOrderNoAutoCompleteForUpdateAction")
class LoadOrderNoAutoCompleteForUpdateAction extends Action{
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    SecondaryDemandOrderService secondaryDemandOrderService


    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            String orderNO=''
            if (params?.searchKey) {
                orderNO = params?.searchKey
            }
            return secondaryDemandOrderService.listOrderNoForUpdate(orderNO,applicationUser)

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
