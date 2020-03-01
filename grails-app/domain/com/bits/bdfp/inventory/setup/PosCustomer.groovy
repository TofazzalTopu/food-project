package com.bits.bdfp.inventory.setup

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class PosCustomer {
    EnterpriseConfiguration     enterpriseConfiguration
    DistributionPoint           distributionPoint
    CustomerMaster              customerMaster

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        dateUpdated

    static constraints = {
        enterpriseConfiguration(nullable: false)
        distributionPoint(blank: false,nullable: false)
        customerMaster(nullable: false, unique: 'distributionPoint')
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        dateUpdated(nullable: true)
    }
}
