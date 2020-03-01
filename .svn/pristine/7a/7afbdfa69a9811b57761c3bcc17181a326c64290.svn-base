package com.bits.bdfp.inventory.setup.incentive

import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser

class QuantityBasedIncentiveSlab {
    QuantityBasedIncentive      quantityBasedIncentive
    FinishProduct               finishProduct
    Float                       quantityFrom
    Float                       quantityTo
    Double                      incentiveAmount

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        quantityBasedIncentive(nullable: false, blank: false)
        finishProduct(nullable: false, blank: false)
        quantityFrom(nullable: false, blank: false)
        quantityTo(nullable: false, blank: false)
        incentiveAmount(nullable: false, blank: false)

        userCreated(nullable: false, blank: false)
        userUpdated(nullable: true, blank: true)
    }
}
