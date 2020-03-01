package com.bits.bdfp.inventory.demandorder.primarydemandorder

import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by prianka.adhikary on 9/14/2015.
 */
@Component("orderNoAutoCompleteListAction")
class OrderNoAutoCompleteListAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService


    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            boolean isNew = false
            ApplicationUser applicationUser = (ApplicationUser) object
            String orderNO = ""
            if (params?.searchKey) {
                orderNO = params?.searchKey
            }
            if(params.isNew && params.isNew == 'true'){
                isNew = true
            }
            return primaryDemandOrderService.listOrderNo(orderNO, applicationUser, isNew)

        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object customerCOAdetails(Object params) {
        try {
//            ApplicationUser applicationUser = (ApplicationUser) object
            Long customerId = Long.parseLong(params.customerId)
            return primaryDemandOrderService.coaDetailList(customerId)

        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }
}
