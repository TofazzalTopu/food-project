package com.bits.bdfp.history

import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder
import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser

class SecondaryOrderHistory {

    SecondaryDemandOrder    secondaryDemandOrder
    FinishProduct           finishProduct
    Double                  previousQuantity
    Double                  newQuantity
    ApplicationUser         updatedBy
    Date                    dateCreated

    static constraints = {
        secondaryDemandOrder(nullable: false)
        finishProduct(nullable: false)
        previousQuantity(nullable: false)
        newQuantity(nullable: false)
        updatedBy(nullable: false)
    }
}
