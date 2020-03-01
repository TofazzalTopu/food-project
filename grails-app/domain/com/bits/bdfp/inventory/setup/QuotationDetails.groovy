package com.bits.bdfp.inventory.setup

import com.bits.bdfp.inventory.product.FinishProduct

class QuotationDetails {

    Quotation       quotation
    FinishProduct   finishProduct
    Float           quantity
    Float           rate
    Float           total


    static constraints = {
        quotation(nullable: false, blank: false)
        finishProduct(nullable: false, blank: false)
        quantity(nullable: false, blank: false)
        rate(nullable: false, blank: false)
        total(nullable: false, blank: false)
    }
}
