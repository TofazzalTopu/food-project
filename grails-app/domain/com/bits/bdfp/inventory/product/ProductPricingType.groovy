package com.bits.bdfp.inventory.product

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class ProductPricingType {
    EnterpriseConfiguration enterpriseConfiguration
    String                  name       //standard price , negotiating price
    String                  note
    Boolean                 isActive
    Integer                 priority

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    dateUpdated

    static constraints = {
        enterpriseConfiguration(nullable: false)
        name(blank: false, nullable: false, unique: 'enterpriseConfiguration', maxSize: 100)
        note(blank: true, nullable: true, maxSize: 512)
        isActive(nullable: true)
        priority(nullable: true)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
        dateCreated(blank: false, nullable: false)
        dateUpdated(blank: true, nullable: true)
    }

    @Override
    String toString() {
        return name
    }
}
