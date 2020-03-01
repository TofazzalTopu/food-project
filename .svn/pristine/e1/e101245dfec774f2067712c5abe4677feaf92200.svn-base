package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser

class DailySalesTargetFinishProduct {

    DailySalesTargetByVolume    dailySalesTargetByVolume
    FinishProduct               finishProduct
    int                         quantity

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        dailySalesTargetByVolume(nullable: false)
        finishProduct(nullable: false, unique: 'dailySalesTargetByVolume')
        quantity(blank: false, nullable: false)

        userCreated(nullable: false)
        userUpdated(nullable: true)
    }
}
