package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.inventory.product.FinishProduct

/**
 * Created by abhijit.majumder on 1/17/2016.
 */
class InvoiceDetails {
    Invoice             invoice
    FinishProduct       finishProduct
    String              batchNumber
    float               quantity
    float               unitPrice  // Including VAT
    Float               unitVat
    BigDecimal          dpUnitPrice = 0.00 // Hold DP Price for each item
    Boolean             isIncentiveCalculated  = Boolean.FALSE

    static mapping = {
        unitVat defaultValue: '0'
        isIncentiveCalculated defaultValue: '0'
        dpUnitPrice defaultValue: '0.00'
    }

    static constraints = {
        invoice(nullable: false, blank:false)
        finishProduct(nullable: false, blank:false)
        unitPrice(nullable: false,blank:false)
        batchNumber(nullable: true,blank:true)
        unitVat(blank: true, nullable: true)
        dpUnitPrice(blank: true, nullable: true)
        isIncentiveCalculated(blank: true, nullable: true)
    }
}
