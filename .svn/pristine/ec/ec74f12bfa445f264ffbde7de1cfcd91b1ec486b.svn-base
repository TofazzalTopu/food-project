package com.bits.bdfp.promotion

import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.warehouse.FinishGoodStock
import com.bits.bdfp.inventory.warehouse.SubWarehouse
import com.docu.security.ApplicationUser

class PromotionPackagePromotionalProducts {
    PromotionPackage       promotionPackage
    FinishProduct          product
    SubWarehouse           subWarehouse
    FinishGoodStock        stockId
    int                    bonusQuantity
    float                  productRate
    boolean                isActive
    ApplicationUser        userCreated
    ApplicationUser        userUpdated
    Date                   dateCreated
    Date                   dateUpdated

    static mapping = {
        isActive defaultValue: '1'
    }

    static constraints = {
        promotionPackage(blank: false, nullable: false)
        product(blank: false, nullable: false)
        subWarehouse(blank: false, nullable: false)
        stockId(blank: false, nullable: false)
        bonusQuantity(nullable: false)
        productRate(nullable: true)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        dateUpdated(nullable: true)
    }
}
