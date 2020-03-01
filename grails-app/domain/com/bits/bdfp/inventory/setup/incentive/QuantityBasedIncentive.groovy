package com.bits.bdfp.inventory.setup.incentive

import com.docu.security.ApplicationUser

class QuantityBasedIncentive {
    String                      programName
    Date                        effectiveDateFrom
    Date                        effectiveDateTo

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        programName(nullable: false, blank: false, unique: true)
        effectiveDateFrom(nullable: false, blank: false)
        effectiveDateTo(nullable: true, blank: true)

        userCreated(nullable: false, blank: false)
        userUpdated(nullable: true, blank: true)
    }
}
