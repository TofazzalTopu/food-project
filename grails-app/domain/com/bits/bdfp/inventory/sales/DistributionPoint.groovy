package com.bits.bdfp.inventory.sales

import com.bits.bdfp.common.BankAccount
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class DistributionPoint {
    EnterpriseConfiguration enterpriseConfiguration
    String                  name
    String                  code   // Legacy  ID
    String                  address
    String                  email
    String                  mobile
    Boolean                 isFactory
    BankAccount             bankAccount
    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    dateUpdated

    static constraints = {
        enterpriseConfiguration(nullable: false)
        name(blank: false, nullable: false, unique: 'enterpriseConfiguration')
        code(blank: false, nullable: false)
        address(nullable: false,blank: false)
        email(blank: false, nullable: false,email: true)
        isFactory(nullable: true)
        mobile(blank: false, nullable: false,)
        bankAccount(blank: true, nullable: true)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
        dateCreated(blank: false, nullable: false)
        dateUpdated(blank: true, nullable: true)
    }

    @Override
    public String toString() {
        return name;
    }
}
