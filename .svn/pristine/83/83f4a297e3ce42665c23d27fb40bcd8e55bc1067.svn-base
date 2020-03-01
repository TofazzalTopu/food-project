package com.bits.bdfp.bonus

import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser

class BonusCriteriaSetup {
    FinishProduct       finishProduct
    Float               requiredQuantity        //16
    Float               bonusQuantity           //1
    Boolean             isMultiplexing
    Boolean             isActive

    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                lastUpdated

    static constraints = {
        finishProduct(nullable: false)
        requiredQuantity(nullable: false)
        bonusQuantity(nullable: false)
        isMultiplexing(nullable: true)
        isActive(nullable: true)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        lastUpdated(nullable: true)
    }
}
