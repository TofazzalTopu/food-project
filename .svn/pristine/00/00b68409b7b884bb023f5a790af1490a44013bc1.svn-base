package com.bits.bdfp.inventory.retailorder

import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser

class RetailOrderDetails {

    RetailOrder             retailOrder
    FinishProduct           finishProduct
    Float                   rate
    Float                   quantity
    Float                   amount

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    lastUpdated

    static constraints = {
        retailOrder(blank: false, nullable: false)
        finishProduct(blank: false, nullable: false, unique: 'retailOrder')
        rate(blank: false, nullable: false)
        quantity(blank: false, nullable: false)
        amount(blank: false, nullable: false)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
    }
}
