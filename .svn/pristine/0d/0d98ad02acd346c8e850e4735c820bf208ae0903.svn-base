package com.bits.bdfp.inventory.product.externalproduct

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

/**
 * Created by mdtofazzal.hossain on 1/13/2019.
 */

class ExternalProduct {
    EnterpriseConfiguration enterpriseConfiguration

    String name
    String description
    String code
    Boolean isActive = Boolean.TRUE

    ApplicationUser userCreated
    ApplicationUser userUpdated
    Date dateCreated
    Date dateUpdated

    static constraints = {
        name(blank: false, nullable: false)
        description(blank: true, nullable: true)
        code(blank: false, nullable: false)
        enterpriseConfiguration(blank: false, nullable: false)
        isActive(nullable: true)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
        dateCreated(blank: false, nullable: false)
        dateUpdated(blank: true, nullable: true)
    }

    static mapping = {
        isActive defaultValue: "1"
    }

    @Override
    public String toString() {
        return name + '-' + code
    }
}
