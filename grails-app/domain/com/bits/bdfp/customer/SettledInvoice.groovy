package com.bits.bdfp.customer

import com.bits.bdfp.inventory.demandorder.Invoice

class SettledInvoice {

    CustomerSettlement      customerSettlement
    Invoice                 invoice
    Float                   settledAmount

    static constraints = {
        customerSettlement(nullable: false)
        invoice(nullable: false)
        settledAmount(blank: false, nullable: false)
    }
}
