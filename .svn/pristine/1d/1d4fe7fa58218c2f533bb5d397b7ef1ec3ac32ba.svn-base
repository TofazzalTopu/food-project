package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser

class YearlySalesTargetFinishProduct {

    YearlySalesTargetByVolume   yearlySalesTargetByVolume
    FinishProduct               finishProduct
    int                         quantity

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        yearlySalesTargetByVolume(nullable: false)
        finishProduct(nullable: false, unique: 'yearlySalesTargetByVolume')
        quantity(blank: false, nullable: false)

        userCreated(nullable: false)
        userUpdated(nullable: true)
    }
}
