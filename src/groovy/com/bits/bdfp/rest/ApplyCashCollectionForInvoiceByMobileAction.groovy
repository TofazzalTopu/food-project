package com.bits.bdfp.rest

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.finance.CustomerAccount
import com.bits.bdfp.finance.CustomerPayment
import com.bits.bdfp.finance.CustomerPaymentInvoice
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.common.CodeGenerationUtil
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
@Component("applyCashCollectionForInvoiceByMobileAction")
class ApplyCashCollectionForInvoiceByMobileAction extends Action {

    private static final Log log = LogFactory.getLog(this)

    @Autowired
    RestDataService restDataService
    @Autowired
    SpringSecurityService springSecurityService
    Message message

    public Object preCondition(Object params, Object object) {
        return null
    }

    public Map execute(Object params, Object object) {
        try {
            Invoice invoice = null
            ApplicationUser applicationUser = (ApplicationUser) object
            double customerPreviousBalance = 0.00
            List<Invoice> invoiceList = new ArrayList<Invoice>()
            Float amountReceived = Float.parseFloat(params?.cashReceived)
            Float confirmAmount = Float.parseFloat(params?.confirmAmount)
            if(amountReceived != confirmAmount){
                return [success:false,message:"Received amount and confirmed amount must be same."]
            }
            CustomerMaster customerMaster = CustomerMaster.get(Long.parseLong(params.customerId))
            if(!customerMaster){
                return [success:false,message:"Customer is not selected."]
            }

            CustomerPayment customerPayment = new CustomerPayment()
            customerPayment.transNo = CodeGenerationUtil.generate(8).toString()
            customerPayment.mrNo = CodeGenerationUtil.generate(8).toString()
            customerPayment.dateTransaction = new Date()
            customerPayment.dateCreated = new Date()
            customerPayment.userCreated = applicationUser

            CustomerAccount customerAccount = CustomerAccount.findByCustomerMaster(customerMaster)
            if(customerAccount){
                customerPreviousBalance = customerAccount.balance
                customerAccount.userUpdated = applicationUser
                customerAccount.dateUpdated = new Date()
            }else{
                customerAccount = new CustomerAccount()
                customerAccount.customerMaster = customerMaster
                customerAccount.dateCreated = new Date()
                customerAccount.userCreated = applicationUser
            }

            double actualApplied = amountReceived + customerPreviousBalance
            double invoicePayment = 0.00
            CustomerPaymentInvoice customerPaymentInvoice = null
            List<CustomerPaymentInvoice> customerPaymentInvoiceList = new ArrayList<CustomerPaymentInvoice>()
            String selectedInvoiceIds = ""
            if(params.invoiceList){
                params.invoiceList.each{
                        invoice = Invoice.get(it.invoiceId)
                        if(selectedInvoiceIds){
                            selectedInvoiceIds += ','+it.invoiceId
                        }else{
                            selectedInvoiceIds = it.invoiceId
                        }

                        double dueInInvoice = invoice.invoiceAmount - invoice.paidAmount
                        dueInInvoice = dueInInvoice.round(2)
                        double receiveForInvoice = it.amountToBeReceived

                        if(invoice.isActive && dueInInvoice > 0.00){
                            if(invoicePayment < actualApplied){
                                customerPaymentInvoice = new CustomerPaymentInvoice(customerPayment: customerPayment, userCreated: applicationUser)
                                customerPaymentInvoice.invoice = invoice
                                if(dueInInvoice >= receiveForInvoice){
                                    invoice.paidAmount += receiveForInvoice
                                    invoicePayment += receiveForInvoice
                                    customerPaymentInvoice.paidAmount = receiveForInvoice
                                }else{
                                    invoice.paidAmount += dueInInvoice
                                    invoicePayment += dueInInvoice
                                    customerPaymentInvoice.paidAmount = dueInInvoice
                                }
                                customerPaymentInvoiceList.add(customerPaymentInvoice)
                            }

                            invoiceList.add(invoice)
                        }
//                    }
                }
            }
            if(actualApplied < invoicePayment){
                return [success:false,message:"Adjusted Invoice Amount is greater than Actual Available Invoice."]
            }

            customerAccount.balance = actualApplied - invoicePayment

            customerPayment.invoices = selectedInvoiceIds
            customerPayment.paymentMode = 'Cash'
            customerPayment.customerMaster = customerMaster
            customerPayment.amount = amountReceived
            customerPayment.confirmAmount = amountReceived
            customerPayment.isDeposited = true
            customerPayment.isSecurityDeposit = false

            Map map = [:]
            map.put('customerPayment', customerPayment)
            map.put('customerPaymentInvoiceList', customerPaymentInvoiceList)
            map.put('retailInvoiceList', invoiceList)
            map.put("customerAccount", customerAccount)

            if(restDataService.applyCashCollectionForInvoice(map,applicationUser)){
                return [success:true,message:"Cash received successfully. MR No: " + customerPayment.mrNo,mrNo:customerPayment.mrNo]
            } else {
                return [success:false,message:"Cash receive failed."]
            }

        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}
