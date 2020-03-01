package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser

class FinishGoodBatchStock {
    String          batchNo
    FinishProduct   finishProduct
    Double          quantity
    Double          totalPrice
    ApplicationUser userCreated
    ApplicationUser userUpdated
    Date            dateCreated
    Date            dateUpdated

    static constraints = {
        batchNo(blank: false,nullable: false)
        finishProduct(nullable: false,blank:false)
        quantity(nullable: false,blank:false)
        totalPrice(nullable: false,blank:false)
        userCreated(nullable: false,blank: false)
        dateCreated(nullable: false,blank: false)
        userUpdated(nullable: true,blank: true)
        dateUpdated(nullable: true,blank: true)

    }
}
