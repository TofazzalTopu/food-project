package com.bits.bdfp.inventory.sales

import com.bits.bdfp.inventory.demandorder.Invoice

class AdjustSalesCommissionDetails {

    AdjustSalesCommission adjustSalesCommission
    Invoice               invoice
    BigDecimal            adjustedAmount

    static constraints = {
        adjustSalesCommission(nullable: false)
        invoice(nullable: false)
        adjustedAmount(nullable: false)
    }
}
