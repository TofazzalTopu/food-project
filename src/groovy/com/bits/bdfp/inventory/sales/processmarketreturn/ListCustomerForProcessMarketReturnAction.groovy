package com.bits.bdfp.inventory.sales.processmarketreturn

import com.bits.bdfp.inventory.sales.ProcessMarketReturnService
import com.docu.common.Action
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 7/31/2016.
 */
@Component("listCustomerForProcessMarketReturnAction")
class ListCustomerForProcessMarketReturnAction extends Action {
    public static final Log log = LogFactory.getLog(ListCustomerForProcessMarketReturnAction.class)

    @Autowired
    ProcessMarketReturnService processMarketReturnService

    Message message

    @Override
    public Object preCondition(def Object object1, def Object object2) {
        return null
    }

    @Override
    public Object postCondition(Object params, Object object) {
        return null
    }

    @Override
    public Object execute(Object params, Object object) {
        try {
            init(params)
            if (params.factory == '0') {
                params.put('appId', object.id)
                return processMarketReturnService.listCustomerByGeoOfUser(params)
            } else {
                return processMarketReturnService.listCustomerByUser()
            }
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }
}
