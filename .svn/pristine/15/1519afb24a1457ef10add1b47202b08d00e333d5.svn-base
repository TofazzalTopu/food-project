package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.docu.security.ApplicationUser

class SampleMiscellaneousTransactions {
    DistributionPoint           distributionPoint
    String                      customerType
    CustomerMaster              customer
    String                      customerName
    String                      customerAddress
    Warehouse                   inventory
    SubWarehouse                subInventory
    FinishProduct               product
    Float                       sampleQty
    String                      remarks

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        distributionPoint(nullable: false,blank: false)
        customerType(nullable: false,blank: false)
        customer(nullable: true,blank: true)
        customerName(nullable: true,blank: true)
        customerAddress(nullable: false,blank: false)
        inventory(nullable: false,blank: false)
        subInventory(nullable: false,blank: false)
        product(nullable: false,blank: false)
        sampleQty(nullable: false,blank: false)
        remarks(nullable: true,blank: true)

        userCreated(nullable: false,blank: false)
        userUpdated(nullable: true,blank: true)
    }
}
