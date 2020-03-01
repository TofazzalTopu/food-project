package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser

class MonthlySalesTargetFinishProduct {

    MonthlySalesTargetByVolume  monthlySalesTargetByVolume
    FinishProduct               finishProduct
    int                         quantity

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        monthlySalesTargetByVolume(nullable: false)
        finishProduct(nullable: false, unique: 'monthlySalesTargetByVolume')
        quantity(blank: false, nullable: false)

        userCreated(nullable: false)
        userUpdated(nullable: true)
    }
}
