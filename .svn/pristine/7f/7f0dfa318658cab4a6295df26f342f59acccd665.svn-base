package com.bits.bdfp.inventory.sales

import com.bits.bdfp.inventory.demandorder.Invoice
import com.docu.security.ApplicationUser

class LoadingSlipDetails {
    LoadingSlip             loadingSlip
    Invoice                 invoice

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    dateUpdated

    static constraints = {
        loadingSlip(nullable: false)
        invoice(nullable: false)
        userCreated(nullable: false,blank: false)
        dateCreated(nullable: false,blank: false)
        userUpdated(nullable: true,blank: true)
        dateUpdated(nullable: true,blank: true)
    }

}
