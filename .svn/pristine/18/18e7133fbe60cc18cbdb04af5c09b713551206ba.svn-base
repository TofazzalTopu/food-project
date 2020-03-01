package com.bits.bdfp.bonus

import com.docu.security.ApplicationUser

/**
 * Created by NZ on 1/30/2017.
 */
class BonusPercent {

    Double percentage

    ApplicationUser                 userCreated
    ApplicationUser                 userUpdated
    Date                            dateCreated
    Date                            lastUpdated

    static constraints = {
        percentage(nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        lastUpdated(nullable: true)
    }
}
