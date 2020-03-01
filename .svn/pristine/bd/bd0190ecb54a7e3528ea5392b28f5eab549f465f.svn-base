package com.bits.bdfp.inventory.product

import com.docu.security.ApplicationUser

class ProductPriceProduct {
    ProductPrice                productPrice
    PricingCategory             pricingCategory
    FinishProduct               finishProduct
    Float                       price
    Float                       vatPercentage
    Float                       vatAmount
    Float                       totalAmount
    Boolean                     isActive

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        productPrice(blank: false, nullable: false)
        pricingCategory(blank: false, nullable: false)
        finishProduct(blank: false, nullable: false, unique: ['productPrice', 'pricingCategory'])
        isActive(blank: false, nullable: false)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
    }
}
