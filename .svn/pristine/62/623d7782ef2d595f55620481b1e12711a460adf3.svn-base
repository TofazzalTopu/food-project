package com.bits.bdfp.currency

import com.docu.security.ApplicationUser

class LocalCurrency {
    String              name
    String              symbol
    String              note
    Boolean             isActive = true

    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                dateUpdated

    static mapping = {
        isActive defaultValue: '1'
    }

    static constraints = {
        name(blank: false, nullable: false, unique: true)
        symbol(blank: true, nullable: true)
        note(blank: true, nullable: true)
        isActive(blank: false, nullable: false)
        userCreated(blank: false,nullable: false)
        userUpdated(blank: true,nullable: true)
        dateCreated(blank: false,nullable: false)
        dateUpdated(blank: true,nullable: true)
    }
    def String toString() {
        return name;
    }
}
