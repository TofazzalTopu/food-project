package com.bits.bdfp.bonus

import com.bits.bdfp.inventory.warehouse.FinishedProductBooked
import com.bits.bdfp.inventory.warehouse.FinishedProductBookedDetails
import com.docu.security.ApplicationUser
import com.sun.org.apache.bcel.internal.generic.FLOAD

class CustomerBonusFinishGood {
    FinishedProductBookedDetails    finishedProductBookedDetails
    BonusCriteriaSetup              bonusCriteriaSetup
    Float                           bonusQuantity

    ApplicationUser                 userCreated
    ApplicationUser                 userUpdated
    Date                            dateCreated
    Date                            lastUpdated
    Boolean                         isConfirmedBonus
    static constraints = {
        finishedProductBookedDetails(nullable: false)
        bonusCriteriaSetup(nullable: false)
        bonusQuantity(nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        lastUpdated(nullable: true)
        isConfirmedBonus(nullable: true)
    }
}
