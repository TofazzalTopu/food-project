package com.bits.bdfp.inventory.demandorder.primarydemandorder

import com.bits.bdfp.inventory.demandorder.DemandOrderStatus
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder
import com.bits.bdfp.inventory.demandorder.RollbackReceivedPrimaryInvoiceService
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdtofazzal.hossain on 5/24/2017.
 */
@Component("rollbackReceivedPrimaryInvoiceAction1")
class RollbackReceivedPrimaryInvoiceAction extends Action{
    private static final Log log = LogFactory.getLog(this)

    static final String RECEIVED_INVOICE_ROLLBACK_SUCCESS = "Received Primary Invoice rollback successful";
    static final String RECEIVED_INVOICE_ROLLBACK_FAILED = "Invoice can not be rollback. Secondary demand order already generated against this invoice";
    Message message
    @Autowired
    RollbackReceivedPrimaryInvoiceService rollbackReceivedPrimaryInvoiceService

    public Object preCondition(Object params, Object object) {
        try {
            Map map = (Map) object

            boolean isValidate = true
            List<Invoice> cancelInvoiceList = map.invoiceList
            if (cancelInvoiceList && cancelInvoiceList.size() > 0) {
            } else {
                isValidate = false
                message = this.getMessage('receiveInvoice', Message.ERROR, "Invoice not found")
            }

            if (isValidate) {
                message = this.getMessage('receiveInvoice', Message.SUCCESS, this.SUCCESS_SAVE)

            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("receiveInvoice", Message.ERROR, "Exception-${ex.message}")
        }
    }

    @Override
    public Object postCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    public Object execute(Object params,Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            Invoice invoice = Invoice.findById(params.invoiceID)

            if (invoice) {
                PrimaryDemandOrder primaryDemandOrder = invoice.primaryDemandOrder
                int count = 0
                if(primaryDemandOrder){
                    List<SecondaryDemandOrder> secondaryDemandOrderList = SecondaryDemandOrder.findAllByPrimaryDemandOrder(primaryDemandOrder)
                    secondaryDemandOrderList.each {secondaryDemandOrder->
                        if(secondaryDemandOrder.demandOrderStatus == DemandOrderStatus.DELIVERED) {
                            count += 1;
                        }
                    }
                }
                if(count >= 1){
                    message = this.getMessage("Rollback Received Invoice", Message.ERROR, RECEIVED_INVOICE_ROLLBACK_FAILED)
                }else{
                    int noOfRows = (int) rollbackReceivedPrimaryInvoiceService.execute(invoice, applicationUser)
                    if (noOfRows == 1) {
                        message = this.getMessage("Rollback Received Invoice", Message.SUCCESS, RECEIVED_INVOICE_ROLLBACK_SUCCESS)
                    }
                }
            }else{
                message = this.getMessage("Rollback Received Invoice", Message.ERROR, "Invoice not found")
            }

            return message
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
