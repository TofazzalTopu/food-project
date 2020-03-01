package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser

class FinishedProductBookedDetails {
    FinishedProductBooked   finishedProductBooked
    FinishProduct           finishProduct
    Double                  quantity
    Double                  amount
    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    dateUpdated
    Double                  purchageRate

    static constraints = {
        finishedProductBooked(nullable: false,blank:false)
        finishProduct(nullable: false,blank:false)
        userCreated(nullable: false,blank: false)
        dateCreated(nullable: false,blank: false)
        userUpdated(nullable: true,blank: true)
        dateUpdated(nullable: true,blank: true)
        purchageRate(nullable: false,blank:false)
    }
}
