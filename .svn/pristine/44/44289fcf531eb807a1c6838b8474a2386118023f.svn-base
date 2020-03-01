package com.bits.bdfp.common

import com.docu.security.ApplicationUser

class BankBranch {
    Bank bank
    String name
    String note
    Boolean isActive
    ApplicationUser userCreated
    ApplicationUser userUpdated
    Date dateCreated
    Date lastUpdated

    static constraints = {
        bank(nullable: false)
        name(blank: false, nullable: false, unique: 'bank')
        note(blank: true, nullable: true)
        isActive(nullable: true)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
    }


    @Override
    public String toString() {
        return  name;
    }
}
