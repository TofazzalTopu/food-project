package com.bits.bdfp.customer

import com.docu.security.ApplicationUser

class CustomerEligibility {
    CustomerEligibilityMaster customerEligibilityMaster
    CustomerMaster customerMaster
    boolean isActive = false
    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated
    static constraints = {
        customerEligibilityMaster(nullable: false)
        isActive(nullable: false)
        customerMaster(nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        lastUpdated(nullable: true)
    }
}
