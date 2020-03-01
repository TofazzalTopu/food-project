package com.bits.bdfp.customer

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

/*
a. Proprietor
b. Sales Manager
c. Owners Representative
d. Others
 */
class CustomerContactType {
    EnterpriseConfiguration enterpriseConfiguration
    String                  name
    String                  note
    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    lastUpdated

    static constraints = {
        enterpriseConfiguration(nullable: false)
        name(nullable: false, blank: false, unique:'enterpriseConfiguration', maxSize: 100 )
        note(blank: true, nullable: true, maxSize: 512)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        lastUpdated(nullable: true)
    }

    @Override
    public String toString() {
        return name;
    }
}
