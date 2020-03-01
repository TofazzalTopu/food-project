package com.bits.bdfp.common

import com.docu.security.ApplicationUser

class Division {

    CountryInfo         countryInfo
    String              geoCode
    String              name

    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                lastUpdated

    static constraints = {
        geoCode(blank: true, nullable: true, maxSize: 30)
        name(blank: false, nullable: false, maxSize: 30, unique: 'countryInfo')
        userCreated(blank: false,nullable: false)
        userUpdated(blank: true,nullable: true)
    }

    def String toString() {
        return name;
    }
}
