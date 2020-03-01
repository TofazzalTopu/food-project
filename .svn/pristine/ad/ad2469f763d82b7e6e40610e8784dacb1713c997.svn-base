package com.bits.bdfp.customer

import com.docu.security.ApplicationUser

/**
 * Employee
 * Customer
 *
 */
class MasterType {
    String          name
    String          note

    ApplicationUser userCreated
    ApplicationUser userUpdated
    Date            dateCreated
    Date            lastUpdated
    static constraints = {
        name(blank: false, nullable: false, unique: true, maxSize: 100)
        note(blank: true, nullable: true, maxSize: 512)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        lastUpdated(nullable: true)
    }

    @Override
    public String toString() {
        return name;
    }
}

