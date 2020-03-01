package com.bits.bdfp.inventory.setup

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class ChargeType {
    EnterpriseConfiguration enterpriseConfiguration
    String                  name
    String                  note
    String                  accountCode

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    dateUpdated

    static constraints = {
        enterpriseConfiguration(nullable: false)
        name(blank: false,nullable: false,unique: 'enterpriseConfiguration')
        accountCode(blank: true, nullable: true, maxSize: 30)
        note(nullable: true,blank: true)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        dateUpdated(nullable: true)
    }

    @Override
    String toString() {
        return name
    }
}
