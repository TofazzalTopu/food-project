package com.bits.bdfp.customer

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

/**
 * a. Modern Trade
 b. Institutional Sales
 c. Retail Sales
 d. Others
 Information related to sales
 channel is required for reporting
 purpose.
 */
class CustomerSalesChannel {
    EnterpriseConfiguration enterpriseConfiguration
    String                  name
    String                  note
    Boolean                 isActive = true

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    lastUpdated

    static mapping = {
        isActive defaultValue: '1'
    }

    static constraints = {
        enterpriseConfiguration(nullable: false)
        name(nullable: false, blank: false, unique: 'enterpriseConfiguration', maxSize: 100)
        note(nullable: true,blank: true, maxSize: 512)
        isActive(nullable: true)
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
