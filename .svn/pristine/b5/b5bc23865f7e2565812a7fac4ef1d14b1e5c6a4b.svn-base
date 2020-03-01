package com.bits.bdfp.finance

import com.bits.bdfp.customer.CustomerMaster
import com.docu.security.ApplicationUser

class CustomerAccount {
    CustomerMaster      customerMaster
    Float               balance
    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                dateUpdated

    static constraints = {
        customerMaster(nullable: false,unique: true)
        balance(nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        dateUpdated(nullable: true)
    }
}
