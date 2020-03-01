package com.bits.bdfp.common

import com.docu.security.ApplicationUser

class BankPaymentMethod {
    String              name
    String              shortName
    Boolean             isActive
    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                lastUpdated

    static constraints = {
        name(blank: false, nullable: false, unique: true, maxSize: 30)
        shortName(blank: true, nullable: true, unique: true, maxSize: 20)
        isActive(nullable: true)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
    }


    @Override
    public String toString() {
        return  name ;
    }
}
