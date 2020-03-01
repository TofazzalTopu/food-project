package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.inventory.demandorder.Invoice
import com.docu.security.ApplicationUser

class FinishGoodStockTransaction {
    FinishGoodStock             finishGoodStock
    Date                        transactionDate
    Float                       inQuantity
    Float                       unitPrice
    Float                       outQuantity
    FinishGoodWarehouseDetails  finishGoodWarehouseDetails   // In Reference
    Invoice                     outInvoice                   // Out Reference

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static mapping = {
        inQuantity defaultValue: "0"
        outQuantity defaultValue: "0"
    }

    static constraints = {
        finishGoodStock(nullable: false, blank: false)
        unitPrice(nullable: false ,blank:false)
        inQuantity(nullable: false, blank:false)
        outQuantity(nullable: false, blank: false)
        finishGoodWarehouseDetails(nullable: true, blank: true)
        outInvoice(nullable: true, blank: true)
        userCreated(nullable: false,blank: false)
        userUpdated(nullable: true,blank: true)
    }
}
