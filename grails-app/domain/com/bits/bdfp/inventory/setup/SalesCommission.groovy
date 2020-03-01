package com.bits.bdfp.inventory.setup

import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.docu.security.ApplicationUser

/**
 * Created by mddelower.hossain on 19-Jan-2016.
 */
class SalesCommission {
    TerritoryConfiguration      territory
    DistributionPoint           distributionPoint
    Float                       branchCommission
    Float                       salesManCommission
    String                      salesCommissionName

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static  constraints = {
        territory(nullable: false, blank: false)
        distributionPoint(nullable: false, blank: false)
        branchCommission(nullable: false, blank: false)
        salesManCommission(nullable: false, blank: false)

        userCreated(nullable: false, blank: false)
        userUpdated(nullable: true, blank: true)
    }

}
