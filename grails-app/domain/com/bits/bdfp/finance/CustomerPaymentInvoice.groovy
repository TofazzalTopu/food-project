package com.bits.bdfp.finance

import com.bits.bdfp.inventory.demandorder.Invoice
import com.docu.security.ApplicationUser

class CustomerPaymentInvoice {

    CustomerPayment             customerPayment
    Invoice                     invoice
    Double                      paidAmount

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static mapping = {
        paidAmount defaultValue: '0.00'
    }

    static constraints = {
        customerPayment(nullable: false)
        invoice(nullable: false, unique: 'customerPayment')
        paidAmount(blank: false, nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
    }
}
