package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.docu.security.ApplicationUser

class ReplacementMiscellaneousTransactions {
    DistributionPoint           distributionPoint
    Warehouse                   inventory
    SubWarehouse                subInventory
    FinishProduct               product
    Float                       replacementQty

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        inventory(nullable: false,blank: false)
        subInventory(nullable: false,blank: false)
        product(nullable: false,blank: false)
        replacementQty(nullable: false,blank: false)

        userCreated(nullable: false,blank: false)
        userUpdated(nullable: true,blank: true)
    }
}
