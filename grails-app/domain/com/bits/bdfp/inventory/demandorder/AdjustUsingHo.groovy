package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.customer.CustomerMaster
import com.docu.security.ApplicationUser

class AdjustUsingHo {

    CustomerMaster      customerMaster
    Float               totalAmount

    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                dateUpdated

    static constraints = {
        customerMaster(blank: false,nullable: false)
        totalAmount(blank: false,nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        dateUpdated(nullable: true)
    }
}
