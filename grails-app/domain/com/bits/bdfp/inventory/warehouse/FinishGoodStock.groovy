package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser

class FinishGoodStock {

    SubWarehouse                subWarehouse
    FinishProduct               finishProduct
    String                      batchNo
    Float                       inQuantity
    Float                       outQuantity

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static mapping = {
        inQuantity defaultValue: "0"
        outQuantity defaultValue: "0"
    }

    static constraints = {
        subWarehouse(blank: false, nullable: false)
        finishProduct(nullable: false, blank:false)
        batchNo(nullable: true, blank: true)
        inQuantity(nullable: false, blank:false)
        outQuantity(nullable: false, blank: false)
        userCreated(nullable: false,blank: false)
        userUpdated(nullable: true,blank: true)
    }
}
