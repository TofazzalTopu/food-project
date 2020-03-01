package com.bits.bdfp.promotion

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.warehouse.FinishGoodStock
import com.docu.security.ApplicationUser

class PromotionPackageProducts {
    PromotionPackage       promotionPackage
    FinishProduct          product
    FinishGoodStock        stockId
    int                    purchaseQuantity
    int                    quantityLimit
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
        stockId(blank: false, nullable: false)
        purchaseQuantity(nullable: false)
        quantityLimit(nullable: false)
        productRate(nullable: true)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        dateUpdated(nullable: true)
    }
}
