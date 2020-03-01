package com.bits.bdfp.inventory.sales

import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.product.FinishProduct

/**
 * Created by NZ on 1/6/2016.
 */
class MarketReturnDetails {

    String mrType
    FinishProduct finishProduct
    Invoice invoice
    String batch
    Float quantity
    String reference
    String remarks
    Float price

    DistributionPointStock distributionPointStock
    MarketReturn marketReturn
    String mrSattelementStatus

    static constraints = {
        finishProduct(blank: false, nullable: false)
        quantity(blank: false, nullable: false)
        marketReturn(blank: false, nullable: false)
        distributionPointStock(blank: true, nullable: true)
        reference(blank: true,nullable: true)
        mrType(blank: false, nullable: false)
        remarks(blank: true, nullable: true)
        invoice(blank: true, nullable: true)
        batch(blank: true,nullable: true)
        price(blank: false,nullable: false)
        mrSattelementStatus(blank: true,nullable: true)
    }
    @Override
    public String toString() {
        return null;
    }
}
