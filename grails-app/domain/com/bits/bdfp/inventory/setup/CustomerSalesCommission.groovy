package com.bits.bdfp.inventory.setup

import com.bits.bdfp.customer.CustomerMaster
import com.docu.security.ApplicationUser

class CustomerSalesCommission {
    SalesCommission             salesCommission
    CustomerMaster              customerMaster
    Date                        effectiveDateFrom
    Date                        effectiveDateTo


    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        salesCommission(nullable: false, blank: false)
        customerMaster(nullable: false, blank: false)
        effectiveDateFrom(nullable: false, blank: false)
        effectiveDateTo(nullable: false, blank: false)


        userCreated(nullable: false, blank: false)
        userUpdated(nullable: true, blank: true)
    }
}
