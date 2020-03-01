package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.docu.security.ApplicationUser

class DamageMiscellaneousTransactions {
    DistributionPoint           distributionPoint
    Warehouse                   inventory
    SubWarehouse                subInventory
    FinishProduct               product
    Float                       damageQty
    String                      remarks

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        distributionPoint(nullable: false,blank: false)
        inventory(nullable: false,blank: false)
        subInventory(nullable: false,blank: false)
        product(nullable: false,blank: false)
        damageQty(nullable: false,blank: false)
        remarks(nullable: true,blank: true)

        userCreated(nullable: false,blank: false)
        userUpdated(nullable: true,blank: true)
    }
}
