package com.bits.bdfp.inventory.retailorder

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.geolocation.TerritorySubArea
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class RetailOrder {

    EnterpriseConfiguration enterprise
    TerritorySubArea        territorySubArea
    String                  orderNo
    CustomerMaster          orderPlacedFor      // Retail Outlet Customer
    ApplicationUser         orderPlacedBy       // Log In User
    CustomerMaster          deliveryMan         // Sales Man
    Date                    orderDate
    Date                    deliveryDate
    Date                    actualDeliveryDate
    Boolean                 isOrderSubmitted
    Invoice                 invoice
    SecondaryDemandOrder    secondaryDemandOrder
    String                  comments

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    lastUpdated

    static mapping = {
        orderDate sqlType: 'date'
        deliveryDate sqlType: 'date'
        isOrderSubmitted defaultValue: '0'
    }


    static constraints = {
        enterprise(blank: false, nullable: false)
        territorySubArea(blank: false, nullable: false)
        orderNo(blank: false, nullable: false, unique: true, maxSize: 20)
        orderPlacedFor(blank: false, nullable: false)
        orderPlacedBy(blank: false, nullable: false)
        deliveryMan(blank: false, nullable: false)
        orderDate(blank: false, nullable: false)
        deliveryDate(blank: false, nullable: false)
        actualDeliveryDate(blank: true, nullable: true)
        isOrderSubmitted(blank: false, nullable: false)
        invoice(blank: true, nullable: true)
        secondaryDemandOrder(blank: true, nullable: true)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
        comments(blank: true, nullable: true, maxSize: 255)
    }
}
