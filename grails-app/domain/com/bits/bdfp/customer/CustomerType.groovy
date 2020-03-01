package com.bits.bdfp.customer

import com.docu.security.ApplicationUser

/***
 * a. Inside
 b. Outside
 c. Export
 d. Institutional
 */
class CustomerType {
    String          name
    String          note
    String          receivableCode
    String          advanceCode

    ApplicationUser userCreated
    ApplicationUser userUpdated
    Date            dateCreated
    Date            lastUpdated
    static constraints = {
        name(blank: false, nullable: false, unique: true, maxSize: 100)
        note(blank: true, nullable: true, maxSize: 512)
        receivableCode(blank: true, nullable: false, maxSize: 30)
        advanceCode(blank: true, nullable: false, maxSize: 30)
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
