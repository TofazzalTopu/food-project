package com.bits.bdfp.customer

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

/**
 * Cash
 * Credit
 */
class CustomerPaymentType {
    EnterpriseConfiguration enterpriseConfiguration
    String          name
    String          note

    ApplicationUser userCreated
    ApplicationUser userUpdated
    Date            dateCreated
    Date            lastUpdated
    static constraints = {
        enterpriseConfiguration(nullable: false)
        name(nullable: false,blank: false,unique: 'enterpriseConfiguration')
        note(nullable: true,blank: true)
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
