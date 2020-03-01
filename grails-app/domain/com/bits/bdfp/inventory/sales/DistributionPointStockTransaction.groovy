package com.bits.bdfp.inventory.sales

import com.bits.bdfp.inventory.demandorder.Invoice
import com.docu.security.ApplicationUser

class DistributionPointStockTransaction {

    DistributionPointStock      distributionPointStock
    Date                        transactionDate
    Float                       inQuantity
    Float                       unitPrice
    Float                       outQuantity
    Invoice                     inInvoice       // In Reference
    Invoice                     outInvoice      // Out Reference

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static mapping = {
        inQuantity defaultValue: '0'
        outQuantity defaultValue: '0'
    }

    static constraints = {
        distributionPointStock(blank: false, nullable: false)
        transactionDate(blank: false, nullable: false)
        inQuantity(blank: false, nullable: false)
        outQuantity(blank: false, nullable: false)
        unitPrice(blank: false, nullable: false)
        inInvoice(blank: true, nullable: true)
        outInvoice(blank: true, nullable: true)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
    }
}
