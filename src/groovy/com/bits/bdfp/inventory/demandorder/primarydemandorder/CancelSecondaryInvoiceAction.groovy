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
 * Created by mdtofazzal.hossain on 3/7/2017.
 */
@Component("cancelSecondaryInvoiceAction")
class CancelSecondaryInvoiceAction extends Action{

    static final String CANCEL_SECONDARY_INVOICE = "Secondary Invoice Cancelled Successfully";

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService
    @Autowired
    ProcessOrderService processOrderService
    Message message
    @Override

    public Object preCondition(Object params, Object object) {
        try {
            Map map = (Map) object

            boolean isValidate = true
            List<Invoice> cancelInvoiceList = map.cancelSecondaryInvoiceStatusList
            if (cancelInvoiceList && cancelInvoiceList.size() > 0) {
            } else {
                isValidate = false
                message = this.getMessage('cancelSecondaryInvoiceStatus', Message.ERROR, "You did not select any invoice .Please select")
            }

            if (isValidate) {
                message = this.getMessage('cancelSecondaryInvoiceStatus', Message.SUCCESS, this.SUCCESS_SAVE)

            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("cancelInvoiceStatus", Message.ERROR, "Exception-${ex.message}")
        }
    }


    @Override
    protected Object postCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    public Object execute(Object params, Object object) {
        try {
            StringBuffer msgBuffer = new StringBuffer();
            ApplicationUser applicationUser = (ApplicationUser) object
            Integer counter = 0
            Map map = [:]
            Invoice invoice = null
            List<Invoice> cancelSecondaryInvoiceStatusList = []
            params.items.each { key, val ->
                if (val instanceof Map) {
                    invoice = Invoice.findByCode(val.invoice_number)
                    cancelSecondaryInvoiceStatusList.add(invoice)
                }
            }
            map.put("cancelSecondaryInvoiceStatusList", cancelSecondaryInvoiceStatusList)

            message = this.preCondition(null, map)
            if (message.type == 1) {
                int noOfRows = (int) processOrderService.cancelSecondaryInvoice(map, applicationUser)
                //if (msgBuffer.toString().length() > 0 && noOfRows == 0) {
                if (noOfRows == 0) {
                    message = this.getMessage("Cancel Secondary Invoice", Message.ERROR, msgBuffer.toString())
                } else {
                    message = this.getMessage("Cancel Secondary Invoice", Message.SUCCESS, CANCEL_SECONDARY_INVOICE)
                }
            }

            return message
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
