package com.bits.bdfp.bonus

import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import com.bits.bdfp.inventory.warehouse.FinishGoodStockTransaction
import com.docu.security.ApplicationUser

/**
 * Created by NZ on 8/28/2016.
 */
class QuantityBasedBonus {
    Invoice                             invoice
    FinishProduct                       finishProduct
    Float                               quantity
    Float                               rate
    String                              batchNo
    FinishGoodStockTransaction          finishGoodStockTransaction
    DistributionPointStockTransaction   distributionPointStockTransaction

    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                lastUpdated

    static constraints = {
        invoice(nullable: false)
        finishProduct(nullable: false)
        quantity(nullable: false)
        rate(nullable: false)
        batchNo(nullable: true)
        finishGoodStockTransaction(nullable: true)
        distributionPointStockTransaction(nullable: true)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        lastUpdated(nullable: true)
    }
}
