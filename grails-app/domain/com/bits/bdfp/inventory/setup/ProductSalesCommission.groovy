package com.bits.bdfp.inventory.setup

import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser

class ProductSalesCommission {
    CustomerSalesCommission     customerSalesCommission
    FinishProduct               finishProduct

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        customerSalesCommission(nullable: false,blank:false)
        finishProduct(nullable: false,blank:false)
//
        userCreated(nullable: false, blank: false)
        userUpdated(nullable: true, blank: true)
    }
}
