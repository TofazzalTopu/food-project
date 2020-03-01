package com.bits.bdfp.customer

import com.bits.bdfp.inventory.demandorder.Invoice
import com.docu.security.ApplicationUser

/* Item wise transaction details */
class CustomerStockTransaction {
    CustomerStock           customerStock
    Date                    transactionDate
    Invoice                 inInvoice
    Invoice                 outInvoice
    Float                   inQuantity
    Float                   outQuantity
    Float                   unitPrice

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    lastUpdated

    static constraints = {
        customerStock(blank: false, nullable: false)
        transactionDate(blank: false, nullable: false)
        inInvoice(blank: true, nullable: true)
        outInvoice(blank: true, nullable: true)
        inQuantity(blank: false, nullable: false)
        outQuantity(blank: false, nullable: false)
        unitPrice(blank: false, nullable: false)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
    }

    static mapping = {
        inQuantity defaultValue: "0.00"
        outQuantity defaultValue: "0.00"
    }
}
