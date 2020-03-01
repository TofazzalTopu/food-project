package com.bits.bdfp.customer

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

/***
 * a. VIP
 b. High Priority
 c. Moderate Priority
 d. Low Priority
 e. Blacklisted
 */
class CustomerPriority {
    EnterpriseConfiguration enterpriseConfiguration
    String                  name
    Integer                 priority
    String                  note
    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    lastUpdated

    static constraints = {
        enterpriseConfiguration(nullable: false)
        name(nullable: false, blank: false, unique: 'enterpriseConfiguration', maxSize: 30)
        priority(nullable: false)
        note(nullable: true, blank: true, maxSize: 512)
        userCreated(nullable: false)
        userUpdated(nullable: true)
    }

    @Override
    public String toString() {
        return name;
    }
}
