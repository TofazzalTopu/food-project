package com.bits.bdfp.inventory.setup.incentive

import com.bits.bdfp.customer.CustomerMaster
import com.docu.security.ApplicationUser

class UnadjustedIncentive {
    String                      incentiveType
    Long                        incentiveProgram
    CustomerMaster              customer
    Double                      incentiveAmount
    Double                      adjustedAmount
    Double                      unadjustedAmount
    Boolean                     isActive

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        incentiveType(nullable: false, blank: false)
        incentiveProgram(nullable: false, blank: false)
        customer(nullable: false, blank: false)
        incentiveAmount(nullable: false, blank: false)
        adjustedAmount(nullable: false, blank: false)
        unadjustedAmount(nullable: false, blank: false)
        isActive(nullable: false, blank: false)

        userCreated(nullable: false, blank: false)
        userUpdated(nullable: true, blank: true)
    }
}
