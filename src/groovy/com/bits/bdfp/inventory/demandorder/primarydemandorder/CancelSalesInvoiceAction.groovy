package com.bits.bdfp.inventory.demandorder.primarydemandorder

import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.bits.bdfp.inventory.demandorder.ProcessOrderService
import com.bits.bdfp.inventory.sales.LoadingSlipDetails
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by abhijit.majumder on 9/21/2015.
 */
@Component("cancelSalesInvoiceAction")
class CancelSalesInvoiceAction extends Action {

    static final String CANCEL_INVOICE = "Invoice Cancelled Successfully";

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService
    @Autowired
    ProcessOrderService processOrderService

    Message message

    public Object preCondition(Object params, Object object) {
        try {
            Map map = (Map) object

            boolean isValidate = true
            List<Invoice> cancelInvoiceList = map.cancelInvoiceStatusList
            if (cancelInvoiceList && cancelInvoiceList.size() > 0) {
            } else {
                isValidate = false
                message = this.getMessage('cancelInvoiceStatus', Message.ERROR, "You did not select any invoice .Please select")
            }

            if (isValidate) {
                message = this.getMessage('cancelInvoiceStatus', Message.SUCCESS, this.SUCCESS_SAVE)

            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("cancelInvoiceStatus", Message.ERROR, "Exception-${ex.message}")
        }
    }

    public Object execute(Object params, Object object) {
        try {
            StringBuffer msgBuffer = new StringBuffer();
            ApplicationUser applicationUser = (ApplicationUser) object
            Integer counter = 0
            Map map = [:]
            Invoice invoice = null
            List<Invoice> cancelInvoiceStatusList = []
            params.items.each { key, val ->
                if (val instanceof Map) {
                    invoice = Invoice.findByCode(val.invoice_number)
                    List<LoadingSlipDetails> loadingSlipDetailsList = LoadingSlipDetails.findAllByInvoice(invoice)
                    if (loadingSlipDetailsList.size() > 0) {
                        msgBuffer.append("Loading Slip Issued For: " + val.invoice_number)
                    } else {
                        cancelInvoiceStatusList.add(invoice)
                    }
                }
            }
            map.put("cancelInvoiceStatusList", cancelInvoiceStatusList)

            message = this.preCondition(null, map)
            if (message.type == 1) {
                int noOfRows = (int) processOrderService.cancelInvoice(map, applicationUser)
                if (msgBuffer.toString().length() > 0 && noOfRows < 0) {
                    message = this.getMessage("Cancel Invoice", Message.ERROR, msgBuffer.toString())
                } else {
                    message = this.getMessage("Cancel Invoice", Message.SUCCESS, CANCEL_INVOICE)
                }
            }

            return message
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }

}
