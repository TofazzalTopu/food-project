package com.bits.bdfp.common

import com.docu.security.ApplicationUser

class ThanaUpazilaPouroshova {
    District            district
    String              geoCode
    String              name

    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                lastUpdated

    static constraints = {
        district(nullable: false)
        geoCode(blank: true, nullable: true, maxSize: 30)
        name(blank: false, nullable: false, maxSize: 50, unique: 'district')
        userCreated(blank: false,nullable: false)
        userUpdated(blank: true,nullable: true)
    }

    @Override
    String toString() {
        return name
    }
}
