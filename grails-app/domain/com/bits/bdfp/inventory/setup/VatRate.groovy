package com.bits.bdfp.inventory.setup

import com.bits.bdfp.inventory.product.FinishProduct
import com.docu.security.ApplicationUser

class VatRate {

        FinishProduct			finishProduct
        Float  				    rate
        Float 				    supplementaryDuty
        Float 				    vat
        Date 					effectiveFromDate
        Date 					effectiveToDate
        Boolean					isActive

        ApplicationUser 		userCreated
        ApplicationUser 		userUpdated
        Date			 		dateCreated
        Date 					lastUpdated

    static constraints = {

        finishProduct           (nullable: false)
        rate                    (blank: false,nullable: false)
        supplementaryDuty       (blank: true,nullable: true)
        vat                     (blank: false,nullable: false)
        effectiveFromDate       (blank: false,nullable: false)
        effectiveToDate         (blank: false,nullable: false)
        isActive                (blank: true)

        userCreated             (nullable: false)
        userUpdated             (nullable: true)

    }
}
