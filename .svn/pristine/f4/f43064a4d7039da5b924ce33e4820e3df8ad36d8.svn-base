package com.bits.bdfp.common

import com.docu.security.ApplicationUser

class UnionInfo {
    ThanaUpazilaPouroshova    thanaUpazilaPouroshova
    String                    name
    String                    geoCode

    ApplicationUser userCreated
    ApplicationUser userUpdated
    Date            dateCreated
    Date            lastUpdated

    static constraints = {
        name(blank: false, nullable: false, maxSize: 50, unique: 'thanaUpazilaPouroshova')
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
    }

    def String toString() {
        return name;
    }
}
