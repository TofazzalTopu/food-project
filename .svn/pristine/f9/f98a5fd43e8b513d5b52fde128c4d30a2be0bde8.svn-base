package com.bits.bdfp.inventory.retailorder.processretailorder

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.retailorder.RetailOrderService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 2/9/16.
 */
@Component("cancelRetailInvoiceAction")
class CancelRetailInvoiceAction extends Action {

    private static final Log log = LogFactory.getLog(this)

    @Autowired
    RetailOrderService retailOrderService

    Message message

    public Object preCondition(Object params, Object object) {
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            String cancelInvoiceSuccessMessage = "Invoice:"
            Invoice invoice = null
            List<Invoice> invoiceList = new ArrayList<Invoice>()
            String selectedInvoiceIds = params.selectedInvoiceIds
            String[] selectedInvoiceIdList = selectedInvoiceIds.split(",")
            for(int i = 0; i < selectedInvoiceIdList.size(); i++){
                invoice = Invoice.get(Long.parseLong(selectedInvoiceIdList[i]))
                if(invoice.paidAmount == 0.00 && invoice.isActive){
                    if(retailOrderService.cancelRetailInvoice(invoice)){
                        cancelInvoiceSuccessMessage += invoice.code + ";"
                    }
                }
            }
            cancelInvoiceSuccessMessage += " Canceled Successfully."
            return this.getMessage("Message", Message.SUCCESS, cancelInvoiceSuccessMessage)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Message", Message.ERROR, "${ex.message}")
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}
