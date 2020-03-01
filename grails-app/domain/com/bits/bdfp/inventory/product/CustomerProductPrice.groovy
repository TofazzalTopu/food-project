package com.bits.bdfp.inventory.product

import com.bits.bdfp.customer.CustomerMaster
import com.docu.security.ApplicationUser

class CustomerProductPrice {

    ProductPrice        productPrice
    CustomerMaster      customerMaster

    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                lastUpdated

    static constraints = {
        productPrice(nullable: false)
        customerMaster(nullable: false, unique: 'productPrice')
        userCreated(nullable: false)
        userUpdated(nullable: true)
    }
}
