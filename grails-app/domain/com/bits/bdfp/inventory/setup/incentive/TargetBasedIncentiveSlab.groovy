package com.bits.bdfp.inventory.setup.incentive

import com.docu.security.ApplicationUser

class TargetBasedIncentiveSlab {
    TargetBasedIncentive        targetBasedIncentive
    Float                       achievementFrom
    Float                       achievementTo
    Double                      incentiveAmount

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        targetBasedIncentive(nullable: false, blank: false)
        achievementFrom(nullable: false, blank: false)
        achievementTo(nullable: false, blank: false)
        incentiveAmount(nullable: false, blank: false)

        userCreated(nullable: false, blank: false)
        userUpdated(nullable: true, blank: true)
    }
}
