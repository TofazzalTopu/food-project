package com.bits.bdfp.settings

import com.bits.bdfp.inventory.sales.DistributionPoint
import com.docu.security.ApplicationUser

class ApplicationUserDistributionPoint {

    ApplicationUser         applicationUser
    DistributionPoint       distributionPoint
    Boolean                 isActive

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    lastUpdated

    static constraints = {
        applicationUser(nullable: false)
        distributionPoint(nullable: false, unique: 'applicationUser')
        isActive(nullable: false)
        userCreated(blank: false,nullable: false)
        userUpdated(blank: true,nullable: true)
    }
}
