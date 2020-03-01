package com.bits.bdfp.inventory.sales.loadingslip

import com.bits.bdfp.inventory.sales.LoadingSlip
import com.bits.bdfp.inventory.sales.LoadingSlipService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 9/30/2015.
 */
@Component("invoiceNoAutoCompleteAction")
class InvoiceNoAutoCompleteAction extends Action {

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    LoadingSlipService loadingSlipService


    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return loadingSlipService.listOrderNo(params)
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
