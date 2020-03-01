package com.bits.bdfp.finance

import com.bits.bdfp.customer.CustomerMaster
import com.docu.security.ApplicationUser

class SecurityDeposit {
    CustomerPayment         customerPayment
    CustomerMaster          customerMaster
    Double                  deposited
    Double                  withdrawn
    Date                    dateTransaction

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    dateUpdated

    static constraints = {
        customerPayment(nullable: false)
        customerMaster(nullable: false)
        deposited(nullable: false)
        withdrawn(nullable: true)
        dateTransaction(nullable: false)

        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        dateUpdated(nullable: true)
    }
}
