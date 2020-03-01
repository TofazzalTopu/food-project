package com.bits.bdfp.finance.customerpayment

import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.finance.CustomerPayment
import com.bits.bdfp.finance.CustomerPaymentCurrencyDenomination
import com.bits.bdfp.finance.CustomerPaymentInvoice
import com.bits.bdfp.finance.CustomerPaymentService
import com.bits.bdfp.finance.SecurityDeposit
import com.bits.bdfp.inventory.demandorder.Invoice
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by liton.miah on 3/28/2017.
 */
@Component("cancelPaymentAction")
class CancelPaymentAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    private final String MESSAGE_HEADER = 'Cancel Payment'

    @Autowired
    CustomerPaymentService customerPaymentService
    Message message

    public Object preCondition(Object params, Object object){
        return null
    }

    public Object execute(Object params, Object object){
        try{
            ApplicationUser applicationUser = (ApplicationUser) object
            CustomerPayment customerPayment = CustomerPayment.read(Long.parseLong(params.id))
            List<CustomerPaymentInvoice> customerPaymentInvoiceList = CustomerPaymentInvoice.findAllByCustomerPayment(customerPayment)
            List<Invoice> invoiceList = new ArrayList<Invoice>()
            customerPaymentInvoiceList.each {
                Invoice invoice = it.invoice
                invoice.paidAmount -= it.paidAmount
                invoice.userUpdated = applicationUser
                invoice.lastUpdated = new Date()
                invoiceList.add(invoice)
            }

            SecurityDeposit securityDeposit = SecurityDeposit.findByCustomerPayment(customerPayment)

            Map map = [:]

            Journal journal = Journal.findByTransactionNo(customerPayment.transNo)
            List<JournalDetails> journalDetailsList = JournalDetails.findAllByJournal(journal)
            List<CustomerPaymentCurrencyDenomination> customerPaymentCurrencyDenominationList = CustomerPaymentCurrencyDenomination.findAllByCustomerPayment(customerPayment)

            if(journal){
                journal.isActive = false
                journal.userUpdated = applicationUser
                journal.lastUpdated = new Date()
            }

            if(journalDetailsList && journalDetailsList.size()>0){
                journalDetailsList.each {
                    it.isActive = false
                    it.userUpdated = applicationUser
                    it.lastUpdated = new Date()
                }
            }

            map.put("securityDeposit", securityDeposit)
            map.put("journal", journal)
            map.put("journalDetailsList", journalDetailsList)
            map.put("customerPaymentCurrencyDenominationList", customerPaymentCurrencyDenominationList)

            map.put("customerPayment", customerPayment)
            map.put("customerPaymentInvoiceList", customerPaymentInvoiceList)
            map.put("invoiceList", invoiceList)

            int count = customerPaymentService.cancelPayment(map)

            if(count > 0){
                message = this.getMessage(MESSAGE_HEADER, Message.SUCCESS, "Payment successfully canceled for the transaction no "+customerPayment.transNo)
            }else{
                message = this.getMessage(MESSAGE_HEADER, Message.ERROR, "Payment cancel failed for the transaction no "+customerPayment.transNo)
            }
            return message
        }catch(Exception ex){
            log.error(ex.message)
            throw new RuntimeException("Exception-${ex.message}")
        }
    }

    public Object postCondition(Object params, Object object){
        return null
    }
}
