package com.bits.bdfp.inventory.setup.incentive

import com.bits.bdfp.customer.CustomerMaster
import com.docu.security.ApplicationUser

class SalesAmountBasedIncentiveCustomers {
    SalesAmountBasedIncentive   salesAmountBasedIncentive
    CustomerMaster              customerMaster
    String                      sattlementStatus

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        salesAmountBasedIncentive(nullable: false, blank: false)
        customerMaster(nullable: false, blank: false)
        sattlementStatus(nullable: true, blank: true)

        userCreated(nullable: false, blank: false)
        userUpdated(nullable: true, blank: true)
    }
}
