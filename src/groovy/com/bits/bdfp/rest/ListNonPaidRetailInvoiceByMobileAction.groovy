package com.bits.bdfp.rest

import com.bits.bdfp.inventory.retailorder.RetailOrderService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 2/7/16.
 */
@Component("listNonPaidRetailInvoiceByMobileAction")
class ListNonPaidRetailInvoiceByMobileAction extends Action {
    public static final Log log = LogFactory.getLog(ListNonPaidRetailInvoiceByMobileAction.class)

    @Autowired
    RetailOrderService retailOrderService

    public Object preCondition(def params, Object object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            List result = retailOrderService.listNonPaidRetailInvoice(params)
            return result
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }


    public Object postCondition(def params, def object) {
        return null
    }
}
