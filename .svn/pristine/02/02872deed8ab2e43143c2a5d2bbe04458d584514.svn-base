package com.bits.bdfp.customer

import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser

class CustomerStock {
    CustomerMaster          deliveryMan
    FinishProduct           finishProduct
    String                  batchNo
    Float                   inQuantity
    Float                   outQuantity

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    lastUpdated

    static constraints = {
        deliveryMan(blank: false, nullable: false)
        finishProduct(blank: false, nullable: false)
        batchNo(blank: true, nullable: true)
        inQuantity(blank: false, nullable: false)
        outQuantity(blank: false, nullable: false)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
    }

    static mapping = {
        inQuantity defaultValue: "0.00"
        outQuantity defaultValue: "0.00"
    }
}
