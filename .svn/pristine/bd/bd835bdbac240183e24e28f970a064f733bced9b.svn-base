package com.bits.bdfp.inventory.sales.invoice

import com.bits.bdfp.inventory.sales.InvoiceService
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 3/22/2016.
 */
@Component("listCustomerForUnadjustedInvoiceAction")
class ListCustomerForUnadjustedInvoiceAction extends Action {

    @Autowired
    InvoiceService invoiceService

    public Object preCondition(def params, def object) {
        //not need to implement
        return null
    }

    public Object execute(def params, def object) {
        try {
            init(params)
            ApplicationUser applicationUser = (ApplicationUser) object
            params.put('appId', applicationUser.id)
            params.put('isCustomer', applicationUser.customerMasterId? applicationUser.customerMasterId : 'false')
            List objectList = null

            return invoiceService.listCustomer(params)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}
