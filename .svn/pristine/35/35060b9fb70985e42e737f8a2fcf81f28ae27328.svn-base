package com.bits.bdfp.inventory.setup.incentive

import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser

class VolumeBasedIncentiveProductSet {
    FinishProduct               finishProduct
    VolumeBasedIncentiveSlab    productSet

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        productSet(nullable: false, blank: false)
        finishProduct(nullable: false, blank: false)

        userCreated(nullable: false, blank: false)
        userUpdated(nullable: true, blank: true)
    }
}
