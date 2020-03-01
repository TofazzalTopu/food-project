package com.bits.bdfp.inventory.demandorder

import com.docu.security.ApplicationUser

/**
 * Created by prianka.adhikary on 9/21/2015.
 */
class PrintInvoiceStatus {
    PrimaryDemandOrder   primaryDemandOrder
    SecondaryDemandOrder secondaryDemandOrder
    Integer              printStatus
    String               invoiceNumber
    ApplicationUser      userCreated
    ApplicationUser      userUpdated
    Date                 dateCreated
    Date                 lastUpdated


    static constraints = {
        primaryDemandOrder(nullable: true)
        secondaryDemandOrder(nullable: true)
        printStatus(nullable: true)
        invoiceNumber(blank: false,nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        lastUpdated(nullable: true)
    }

}
