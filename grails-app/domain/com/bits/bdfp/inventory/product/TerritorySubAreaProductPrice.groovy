package com.bits.bdfp.inventory.product

import com.bits.bdfp.geolocation.TerritorySubArea
import com.docu.security.ApplicationUser

class TerritorySubAreaProductPrice {

    ProductPrice        productPrice
    TerritorySubArea    territorySubArea

    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                lastUpdated

    static constraints = {
        productPrice(nullable: false)
        territorySubArea(nullable: false, unique: 'productPrice')
        userCreated(nullable: false)
        userUpdated(nullable: true)
    }
}
