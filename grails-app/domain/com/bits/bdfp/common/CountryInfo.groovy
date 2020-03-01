package com.bits.bdfp.common

import com.docu.security.ApplicationUser

class CountryInfo {

    String              code
    String              name

    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                lastUpdated

    static constraints = {
        code(blank: true, nullable: true, maxSize: 20)
        name(blank: false, nullable: false, unique: true, maxSize: 30)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
    }

    def String toString() {
        return name;
    }


}
