package com.bits.bdfp.customer

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class AssetLending {

    CustomerMaster              customerMaster
    EnterpriseConfiguration     enterpriseConfiguration

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        customerMaster(nullable: false, unique: true)
        enterpriseConfiguration(nullable: true)

        userCreated(nullable: false)
        userUpdated(nullable: true)
    }
}
