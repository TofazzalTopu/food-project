package com.bits.bdfp.inventory.sales

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.warehouse.Warehouse
import com.docu.security.ApplicationUser

class DistributionPointWarehouse {
    DistributionPoint   distributionPoint
    Warehouse           warehouse
    CustomerMaster      inCharge
    CustomerMaster      defaultCustomer
    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                dateUpdated
    static constraints = {
        distributionPoint(nullable: false)
        warehouse(nullable: false, unique: true)
        userCreated(blank: false,nullable: false)
        userUpdated(blank: true,nullable: true)
        dateCreated(blank: false,nullable: false)
        dateUpdated(blank: true,nullable: true)
        inCharge(blank: true,nullable: true)
        defaultCustomer(blank: true,nullable: true)
    }
}
