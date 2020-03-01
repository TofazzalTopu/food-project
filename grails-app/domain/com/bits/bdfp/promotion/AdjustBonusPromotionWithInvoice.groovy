package com.bits.bdfp.promotion

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.warehouse.FinishGoodStock
import com.docu.security.ApplicationUser

class AdjustBonusPromotionWithInvoice {
    PromotionPackage       promotionPackage
    CustomerMaster         customer
    Invoice                invoice
    String                 stock
    FinishProduct          product
    int                    quantity
    float                  rate
    double                 discountAmount
    boolean                isActive
    boolean                isAdjusted

    ApplicationUser        userCreated
    ApplicationUser        userUpdated
    Date                   dateCreated
    Date                   dateUpdated

    static mapping = {
        isActive defaultValue: '1'
        isAdjusted defaultValue: '0'
    }

    static constraints = {
        promotionPackage(blank: false, nullable: false)
        customer(blank: false, nullable: false)
        invoice(blank: false, nullable: false)
        stock(blank: true, nullable: true)
        product(blank: true, nullable: true)
        quantity(nullable: true)
        rate(nullable: true)
        discountAmount(nullable: true)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        dateUpdated(nullable: true)
    }
}
