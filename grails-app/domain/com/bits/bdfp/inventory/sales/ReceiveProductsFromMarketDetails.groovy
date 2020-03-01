package com.bits.bdfp.inventory.sales

import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser

class ReceiveProductsFromMarketDetails {

    ReceiveProductsFromMarket receiveProductsFromMarket

    FinishProduct           finishProduct
    String                  mrType
    Float                   quantity
    Boolean                 batchAvailable
    Invoice                 invoice
    String                  batch
    String                  reference
    String                  remarks
    DistributionPointStock  distributionPointStock


    static constraints = {
        receiveProductsFromMarket(blank: false, nullable: false)
        finishProduct(blank: false, nullable: false)
        mrType(blank: false, nullable: false)
        quantity(blank: false, nullable: false)
        batchAvailable(blank: false, nullable: false)
        invoice(blank: true, nullable: true)
        batch(blank: true, nullable: true)
        reference(blank: true, nullable: true)
        remarks(blank: true, nullable: true)
        distributionPointStock(blank: false, nullable: false)
    }
}
