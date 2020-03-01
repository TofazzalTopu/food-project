package com.bits.bdfp.currency

import com.docu.security.ApplicationUser

class CurrencyDemonstration {
    LocalCurrency       localCurrency
    String              noteName
    Float               value
    Boolean             isActive

    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                dateUpdated

    static mapping = {
        isActive defaultValue: '1'
    }
    static constraints = {
        localCurrency(blank: false, nullable: false)
        noteName(blank: false, nullable: false, unique: 'localCurrency')
        value(blank: false, nullable: false)
        isActive(blank: false, nullable: false)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
        dateCreated(blank: false, nullable: false)
        dateUpdated(blank: true, nullable: true)
    }
}
