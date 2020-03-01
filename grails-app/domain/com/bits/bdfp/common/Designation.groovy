package com.bits.bdfp.common

import com.docu.security.ApplicationUser

class Designation {
    String              code
    String              name
    String              note
    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                dateUpdated

    static constraints = {
        code(blank: false,nullable: false,unique: true)
        name(blank: false,nullable: false,unique: true)
        note(blank: true,nullable: true)
        userCreated(blank: false,nullable: false)
        userUpdated(blank: true,nullable: true)
        dateCreated(blank: false,nullable: false)
        dateUpdated(blank: true,nullable: true)
    }

    static mapping = {
        note type: 'text'
    }

    @Override
    String toString() {
        return name
    }
}
