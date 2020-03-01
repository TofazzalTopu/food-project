package com.bits.bdfp.promotion

import com.bits.bdfp.customer.CustomerMaster
import com.docu.security.ApplicationUser

class PromotionPackageCustomers {
    PromotionPackage       promotionPackage
    CustomerMaster         customer
    boolean                isActive
    ApplicationUser userCreated
    ApplicationUser userUpdated
    Date            dateCreated
    Date            dateUpdated

    static mapping = {
        isActive defaultValue: '1'
    }

    static constraints = {
        promotionPackage(blank: false, nullable: false)
        customer(blank: false, nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        dateUpdated(nullable: true)
    }
}
