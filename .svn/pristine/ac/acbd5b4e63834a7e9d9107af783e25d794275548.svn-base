package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.docu.security.ApplicationUser

class InventoryToInventoryTransfer {
    DistributionPoint                   senderDp
    DistributionPoint                   receiverDp
    Warehouse                           senderInventory
    Warehouse                           receiverInventory
    SubWarehouse                        senderSubInventory
    String                              transferNo
    Date                                transferDate
    String                              transferChallanNumber
    FinishProduct                       transferProduct
    Float                               transferQty
    String                              batch
    Float                               unitPrice
    InventoryToInventoryTransferStatus  transferChallanStatus

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        senderDp(nullable: false,blank: false)
        receiverDp(nullable: false,blank: false)
        senderInventory(nullable: false,blank: false)
        receiverInventory(nullable: false,blank: false)
        senderSubInventory(nullable: false,blank: false)
        transferNo(nullable: true,blank: true)
        transferDate(nullable: false,blank: false)
        transferChallanNumber(nullable: false,blank: false)
        transferProduct(nullable: false,blank: false)
        transferQty(nullable: false,blank: false)
        batch(nullable: true,blank: true)
        unitPrice(nullable: false,blank: false)
        transferChallanStatus(nullable: false,blank: false)

        userCreated(nullable: false,blank: false)
        userUpdated(nullable: true,blank: true)
    }
}
