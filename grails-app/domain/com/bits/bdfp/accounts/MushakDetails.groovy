package com.bits.bdfp.accounts

import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser

class MushakDetails {

    Mushak          mushak
    FinishProduct   finishProduct
    Float           quantity
    Float           rate
    Float           sdAmount
    Float           vatAmount
    Float           totalAmount
    Float           sdPercent
    Float           vatPercent

    static constraints = {
        mushak(blank: false,nullable: false)
        finishProduct(blank: false,nullable: false)
        quantity(blank: false,nullable: false)
        rate(blank: false,nullable: false)
        sdAmount(blank: true,nullable: true)
        vatAmount(blank: false,nullable: false)
        totalAmount(blank: false,nullable: false)
        sdPercent(blank: false,nullable: false)
        vatPercent(blank: false,nullable: false)
    }
}
