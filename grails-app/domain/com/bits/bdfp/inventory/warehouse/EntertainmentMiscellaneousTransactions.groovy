package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.settings.Department
import com.docu.security.ApplicationUser

class EntertainmentMiscellaneousTransactions {
    DistributionPoint           distributionPoint
    Department                  department
    Warehouse                   inventory
    SubWarehouse                subInventory
    FinishProduct               product
    Float                       entertainmentQty
    String                      remarks

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        distributionPoint(nullable: false,blank: false)
        department(nullable: false,blank: false)
        inventory(nullable: false,blank: false)
        subInventory(nullable: false,blank: false)
        product(nullable: false,blank: false)
        entertainmentQty(nullable: false,blank: false)

        userCreated(nullable: false,blank: false)
        userUpdated(nullable: true,blank: true)
    }
}
