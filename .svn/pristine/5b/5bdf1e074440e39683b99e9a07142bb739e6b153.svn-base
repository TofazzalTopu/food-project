package com.bits.bdfp.inventory.sales

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.warehouse.SubWarehouse
import com.bits.bdfp.inventory.warehouse.Warehouse
import com.docu.security.ApplicationUser

class ReceiveProductsFromMarket {

    DistributionPoint       receivingDp
    Warehouse               receivingInventory
    SubWarehouse            receivingSubInventory
    CustomerMaster          customerMaster

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    dateUpdated

    static constraints = {
        receivingDp(blank: false, nullable: false)
        receivingInventory(blank: false, nullable: false)
        receivingSubInventory(blank: false, nullable: false)
        customerMaster(blank: false, nullable: false)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
        dateCreated(blank: false, nullable: false)
        dateUpdated(blank: true, nullable: true)
    }
}
