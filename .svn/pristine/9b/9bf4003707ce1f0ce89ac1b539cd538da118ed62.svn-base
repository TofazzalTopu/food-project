package com.bits.bdfp.inventory.setup.incentive

import com.docu.security.ApplicationUser

class SalesAmountBasedIncentiveSlab {
    SalesAmountBasedIncentive   salesAmountBasedIncentive
    Double                      salesValueFrom
    Double                      salesValueTo
    Double                      incentiveAmount
    Float                       incentiveInPct

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        salesAmountBasedIncentive(nullable: false, blank: false)
        salesValueFrom(nullable: false, blank: false)
        salesValueTo(nullable: false, blank: false)
        incentiveAmount(nullable: true, blank: true)
        incentiveInPct(nullable: true, blank: true)

        userCreated(nullable: false, blank: false)
        userUpdated(nullable: true, blank: true)
    }
}
