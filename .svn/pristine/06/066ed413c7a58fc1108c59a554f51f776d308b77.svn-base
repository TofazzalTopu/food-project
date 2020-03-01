package com.bits.bdfp.inventory.sales

import com.bits.bdfp.geolocation.TerritorySubArea
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class DistributionPointTerritorySubArea {
    DistributionPoint   distributionPoint
    TerritorySubArea    territorySubArea

    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                dateUpdated


    static constraints = {
        distributionPoint(nullable: false)
        territorySubArea(nullable: false)
        userCreated(blank: false,nullable: false)
        userUpdated(blank: true,nullable: true)
        dateCreated(blank: false,nullable: false)
        dateUpdated(blank: true,nullable: true)
    }
}
