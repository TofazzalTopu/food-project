package com.bits.bdfp.inventory.setup.incentive

import com.bits.bdfp.customer.CustomerMaster
import com.docu.security.ApplicationUser

class VolumeBasedIncentiveCustomers {
    VolumeBasedIncentive        volumeBasedIncentive
    CustomerMaster              customerMaster
    String                      sattlementStatus

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        volumeBasedIncentive(nullable: false, blank: false)
        customerMaster(nullable: false, blank: false)
        sattlementStatus(nullable: true, blank: true)

        userCreated(nullable: false, blank: false)
        userUpdated(nullable: true, blank: true)
    }
}
