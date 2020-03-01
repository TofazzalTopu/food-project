package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser

class FinishGoodWarehouseDetails {
    FinishGoodWarehouse finishGoodWarehouse
    String              productRefNo
    FinishProduct       finishProduct
    Double              quantity
    Double              cost
    Double              confirmCost
    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                dateUpdated
    static constraints = {

        finishGoodWarehouse(blank: false, nullable: false)
        productRefNo(blank: false, nullable: false)
        finishProduct(blank: false, nullable: false)
        quantity(blank: false, nullable: false)
        cost(blank: false, nullable: false)
        confirmCost(blank: false, nullable: false)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
        dateCreated(blank: false, nullable: false)
        dateUpdated(blank: true, nullable: true)
    }
}
