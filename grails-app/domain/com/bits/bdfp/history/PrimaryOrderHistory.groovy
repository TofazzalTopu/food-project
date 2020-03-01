package com.bits.bdfp.history

import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder
import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser

class PrimaryOrderHistory {
    PrimaryDemandOrder      primaryDemandOrder
    FinishProduct           finishProduct
    Double                  previousQuantity
    Double                  newQuantity
    ApplicationUser         updatedBy
    Date                    dateCreated

    static constraints = {
        primaryDemandOrder(nullable: false)
        finishProduct(nullable: false)
        previousQuantity(nullable: false)
        newQuantity(nullable: false)
        updatedBy(nullable: false)
    }
}
