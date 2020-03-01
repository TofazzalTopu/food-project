package com.bits.bdfp.inventory.workflow

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class Workflow {
    EnterpriseConfiguration enterpriseConfiguration
    String                  name
    String                  menuName
    String                  description
    Boolean                 isActive

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    dateUpdated

    static mapping = {
        isActive defaultValue: '1'
    }

    static constraints = {
        enterpriseConfiguration(nullable: false)
        name(nullable: true, blank: true, unique: 'enterpriseConfiguration')
        description(blank: false, nullable: false)
        menuName(blank: false, nullable: false)
        isActive(nullable: true)
        userCreated(blank: false,nullable: false)
        userUpdated(blank: true,nullable: true)
        dateCreated(blank: false,nullable: false)
        dateUpdated(blank: true,nullable: true)
    }

    @Override
    public String toString() {
        return name ;
    }
}
