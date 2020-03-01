package com.bits.bdfp.inventory.demandorder.primarydemandorder

import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Topu on 10/04/2015.
 */
@Component("primaryProcessOrderAutoCompleteAction")
class PrimaryProcessOrderAutoCompleteAction extends Action {

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService


    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            String orderNO=''
            if (params?.searchKey) {
                orderNO = params?.searchKey
            }
            return primaryDemandOrderService.listOrderNoWithInvoice(orderNO)

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
