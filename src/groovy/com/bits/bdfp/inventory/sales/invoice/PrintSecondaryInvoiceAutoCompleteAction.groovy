package com.bits.bdfp.inventory.sales.invoice

import com.bits.bdfp.inventory.sales.InvoiceService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 8/14/16.
 */
@Component("printSecondaryInvoiceAutoCompleteAction")
class PrintSecondaryInvoiceAutoCompleteAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    InvoiceService invoiceService


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
            return invoiceService.listPrintSecondaryInvoiceAutoComplete(orderNO, applicationUser)

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
