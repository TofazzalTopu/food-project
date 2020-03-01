package com.bits.bdfp.settings.bussinessday

import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.docu.security.ApplicationUser

class LocalHoliday {
    TerritoryConfiguration  territoryConfiguration
    FinancialYear           financialYear
    Integer                 day
    Integer                 month
    Integer                 year
    String                  note

    ApplicationUser     userCreated
    ApplicationUser     userLastUpdated
    Date                dateCreated
    Date                dateLastUpdated

    static constraints = {
        territoryConfiguration(nullable: false)
        financialYear(nullable: false)
        day(nullable: false)
        month(nullable: false)
        year(nullable: false)
        userCreated(blank: false,nullable: false)
        userLastUpdated(blank: true,nullable: true)
        dateCreated(blank: false,nullable: false)
        dateLastUpdated(blank: true,nullable: true)
    }
    static mapping={
        note type: 'text'
    }
}
