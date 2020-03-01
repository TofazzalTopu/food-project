package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser

class PrimaryDemandOrderDetails {
    PrimaryDemandOrder      primaryDemandOrder
//    CustomerMaster          customerOrderFor
//    String                  secondaryOrderNo
    FinishProduct           finishProduct
    Float                   rate
    Float                   quantity
    Float                   amount

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    lastUpdated


    static constraints = {
        primaryDemandOrder(nullable: false)
//        customerOrderFor(nullable: false)
//        secondaryOrderNo(nullable: true)
        finishProduct(nullable: false)
        rate(nullable: false)
        quantity(nullable: false)
        amount(nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false,blank:false)
        lastUpdated(nullable: true,blank:true)
    }
}
