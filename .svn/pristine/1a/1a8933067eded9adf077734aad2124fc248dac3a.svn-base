package com.bits.bdfp.inventory.demandorder

import com.docu.security.ApplicationUser

class WriteOff {

    Invoice     invoice
    Float       writeOffAmount

    ApplicationUser userCreated
    ApplicationUser userUpdated
    Date            dateCreated
    Date            lastUpdated

    static constraints = {
        invoice(blank: false,nullable: false)
        writeOffAmount(blank: false,nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        lastUpdated(nullable: true)
    }
}
