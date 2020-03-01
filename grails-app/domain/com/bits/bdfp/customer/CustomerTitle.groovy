package com.bits.bdfp.customer

import com.docu.security.ApplicationUser

class CustomerTitle {

    String          name
    String          note

    ApplicationUser userCreated
    ApplicationUser userUpdated
    Date            dateCreated
    Date            lastUpdated
    static constraints = {
        name(blank: false, nullable: false, unique: true)
        note(blank: true, nullable: true)
        userCreated(nullable: false)
        userUpdated(nullable: true)
    }

    @Override
    String toString() {
        return name
    }
}
