package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser

class SubInventoryToSubInventoryTransfer {
    Warehouse                           inventory
    SubWarehouse                        subInventoryFrom
    SubWarehouse                        subInventoryTo
    Date                                transferDate
    FinishProduct                       product
    Float                               transferQty
    String                              batchNo
    Float                               unitPrice

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        inventory(nullable: false,blank: false)
        subInventoryFrom(nullable: false,blank: false)
        subInventoryTo(nullable: false,blank: false)
        transferDate(nullable: false,blank: false)
        product(nullable: false,blank: false)
        transferQty(nullable: false,blank: false)
        batchNo(nullable: true, blank: true)
        unitPrice(nullable: false,blank: false)

        userCreated(nullable: false,blank: false)
        userUpdated(nullable: true,blank: true)
    }
}
