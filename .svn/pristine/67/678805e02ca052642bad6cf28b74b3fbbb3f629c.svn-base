package com.bits.bdfp.inventory.setup

import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class DeliveryTruck {
    EnterpriseConfiguration     enterpriseConfiguration
    DistributionPoint           distributionPoint
    String                      name
    String                      vehicleNumber
    Float                       loadingCapacity
    Float                       truckHeight
    Float                       truckWidth
    Float                       truckLength

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        dateUpdated

    static constraints = {
        enterpriseConfiguration(nullable: false)
        distributionPoint(nullable: false)
        name(nullable: false,unique: 'enterpriseConfiguration')
        vehicleNumber(nullable: false,unique: 'enterpriseConfiguration')
        loadingCapacity(nullable: false)
        truckHeight(blank: true, nullable: true)
        truckWidth(blank: true, nullable: true)
        truckLength(blank: true, nullable: true)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        dateUpdated(nullable: true)

    }
}
