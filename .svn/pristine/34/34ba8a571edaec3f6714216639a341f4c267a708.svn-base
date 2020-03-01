package com.bits.bdfp.bonus
import com.bits.bdfp.inventory.demandorder.Invoice
import com.docu.security.ApplicationUser

class AdjustOnePercentBonusDetails {
    AdjustOnePercentBonus           adjustOnePercentBonus
    Invoice                         invoice
    BigDecimal                      paidAmount

    ApplicationUser                 userCreated
    ApplicationUser                 userUpdated
    Date                            dateCreated
    Date                            lastUpdated

    static constraints = {
        adjustOnePercentBonus(nullable: false)
        paidAmount(nullable: false)
        invoice(nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        lastUpdated(nullable: true)
    }
}
