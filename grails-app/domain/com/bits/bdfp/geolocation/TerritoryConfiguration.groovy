package com.bits.bdfp.geolocation

import com.bits.bdfp.settings.BusinessUnitConfiguration
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class TerritoryConfiguration {

    EnterpriseConfiguration         enterpriseConfiguration
    BusinessUnitConfiguration       businessUnitConfiguration
    String                          name
    boolean                         isActive
    ApplicationUser                 userCreated
    ApplicationUser                 userUpdated
    Date                            dateCreated
    Date                            lastUpdated

    static mapping = {
        isActive defaultValue: '1'
    }

    static constraints = {
        name(blank: false, nullable: false, unique: 'enterpriseConfiguration', maxSize: 100)
        businessUnitConfiguration(nullable: true)
        isActive(nullable: true)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
    }


    @Override
    public String toString() {
        return name ;
    }
}
