package com.bits.bdfp.customer

import com.bits.bdfp.geolocation.TerritorySubArea
import com.docu.security.ApplicationUser

class CustomerTerritorySubArea {
    CustomerMaster      customerMaster
    TerritorySubArea    territorySubArea

    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                lastUpdated

    static constraints = {
        customerMaster(nullable: false)
        territorySubArea(nullable: false,unique: 'customerMaster')
        userCreated(nullable: false)
        userUpdated(nullable: true)
    }
}
