package com.bits.bdfp.promotion

import com.docu.security.ApplicationUser

class Promotion {
    String          name
    Date            effectiveFrom
    Date            effectiveTo
    boolean         isActive
    String          calculationStatus
    ApplicationUser userCreated
    ApplicationUser userUpdated
    Date            dateCreated
    Date            dateUpdated

    static mapping = {
        isActive defaultValue: '1'
    }

    static constraints = {
        name(blank: false, nullable: false)
        calculationStatus(blank: false, nullable: false)
        effectiveFrom(blank: false, nullable: false)
        effectiveTo(blank: false, nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        dateUpdated(nullable: true)
    }
}
