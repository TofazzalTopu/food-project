package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import com.bits.bdfp.inventory.warehouse.FinishedProductBooked
import com.docu.security.ApplicationUser

class CustomerDemandOrderPayment {
    FinishedProductBooked               finishedProductBooked
    CustomerMaster                      customerMaster
    Date                                datePayment
    Double                              paidAmount
    DistributionPointStockTransaction   distributionPointStockTransaction

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        customerMaster(nullable: false)
        finishedProductBooked(nullable: true)
        distributionPointStockTransaction(nullable: true)
        datePayment(nullable: false)
        paidAmount(nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false,blank:false)
        lastUpdated(nullable: true,blank:true)
    }
}
