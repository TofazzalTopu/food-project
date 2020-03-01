package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser

class SecondaryDemandOrderDetails {
    SecondaryDemandOrder     secondaryDemandOrder
    FinishProduct   finishProduct
    Float           rate
    Float           quantity
    Float           amount

    ApplicationUser userCreated
    ApplicationUser userUpdated
    Date            dateCreated
    Date            lastUpdated

    static constraints = {
        secondaryDemandOrder(nullable: false)
        finishProduct(nullable: false)
        rate(nullable: false)
        quantity(nullable: false)
        amount(nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        lastUpdated(nullable: true)
    }
}
