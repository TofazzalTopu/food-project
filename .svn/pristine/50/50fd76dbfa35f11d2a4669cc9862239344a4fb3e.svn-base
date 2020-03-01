package com.bits.bdfp.customer

import com.docu.security.ApplicationUser

class CustomerRecovery {

        AssetLending                assetLending
        Date                        recoveryDate
        Double                      amount

        ApplicationUser             userCreated
        ApplicationUser             userUpdated
        Date                        dateCreated
        Date                        lastUpdated

        static constraints = {
            assetLending(nullable: false)
            recoveryDate(blank: false, nullable: false)
            amount(blank: false, nullable: false)

            userCreated(nullable: false)
            userUpdated(nullable: true)
        }
}
