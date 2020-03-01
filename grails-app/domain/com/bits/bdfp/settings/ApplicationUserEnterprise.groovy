package com.bits.bdfp.settings

import com.docu.security.ApplicationUser

class ApplicationUserEnterprise {
    ApplicationUser         applicationUser
    EnterpriseConfiguration enterpriseConfiguration
    Boolean                 isActive
    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    dateUpdated

    static constraints = {
        applicationUser(nullable: false)
        enterpriseConfiguration(nullable: false, unique: 'applicationUser')
        isActive(nullable: false)
        userCreated(blank: false,nullable: false)
        userUpdated(blank: true,nullable: true)
        dateCreated(blank: false,nullable: false)
        dateUpdated(blank: true,nullable: true)
    }
}
