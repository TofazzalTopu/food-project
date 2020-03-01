package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.CustomerStatus
import com.bits.bdfp.geolocation.TerritorySubArea
import com.docu.security.ApplicationUser

class SecondaryDemandOrder {
    CustomerMaster      customerMaster
    ApplicationUser     userOrderPlaced
    CustomerMaster      userTentativeDelivery
    String              orderNo //Auto generate with format
    Date                dateOrder
    Date                dateDeliver
    Boolean             isForwared
    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                lastUpdated
    DemandOrderStatus   demandOrderStatus
    PrimaryDemandOrder  primaryDemandOrder
    TerritorySubArea    territorySubArea

    static constraints = {
        customerMaster(nullable: false)
        userOrderPlaced(nullable: false)
        userTentativeDelivery(nullable: false)
        orderNo(blank: false, nullable: false, unique: true)
        demandOrderStatus(blank: false, nullable: false)
        primaryDemandOrder(nullable: true)
        dateOrder(blank: false, nullable: false)
        dateDeliver(blank: false, nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        lastUpdated(nullable: true)
        isForwared(nullable: true)
        territorySubArea(nullable: true)
    }
}
