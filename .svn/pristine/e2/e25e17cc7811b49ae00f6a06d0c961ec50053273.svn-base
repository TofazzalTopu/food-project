package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser

class SalesHeadFinishProduct {

    SalesHeadByVolume       salesHeadByVolume
    FinishProduct           finishProduct
    int                     quantity

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    lastUpdated

    static constraints = {
        salesHeadByVolume(nullable: false)
        finishProduct(nullable: false, unique: 'salesHeadByVolume')
        quantity(blank: false, nullable: false)

        userCreated(nullable: false)
        userUpdated(nullable: true)
    }
}
