package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.CustomerShippingAddress
import com.bits.bdfp.geolocation.TerritorySubArea
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.docu.security.ApplicationUser

class PrimaryDemandOrder {
    ApplicationUser         userOrderPlaced
    DistributionPoint       distributionPoint
    CustomerMaster          customerOrderFor
    CustomerShippingAddress shippingAddress
    String                  orderNo //Auto generate with format
    Date                    dateProposedDelivery // used as actual delivery date for update delivery date screen
    Date                    dateExpectedDeliver
    Date                    updatedDeliveryDate
    Date                    orderDate

    Boolean                 isApprovalRequired
    Boolean                 isApproved

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    lastUpdated
    DemandOrderStatus       demandOrderStatus
    TerritorySubArea        territorySubArea
    Boolean                 isNew = Boolean.FALSE

    static mapping = {
        isApprovalRequired defaultValue: '0'
        isApproved defaultValue: '0'
        isNew defaultValue: '0'
    }

    static constraints = {
        userOrderPlaced(nullable: false)
        distributionPoint(nullable: true)
        customerOrderFor(nullable: false)
        orderNo(blank: false, nullable: false, unique: true)
        dateProposedDelivery(nullable: false)
        dateExpectedDeliver(nullable: false)
        updatedDeliveryDate(nullable: true)
        orderDate(nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable:false, blank:false)
        lastUpdated(nullable: true, blank:true)
        demandOrderStatus(nullable: true, blank:true)
        shippingAddress(nullable: true)
        territorySubArea(nullable: true)
        isNew(nullable: true)
    }
}
