package com.bits.bdfp.accounts

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class Journal {
    EnterpriseConfiguration     enterprise
    String                      journalNo
    Date                        transactionDate
    JournalStatus               journalStatus

    String                      moduleName
    String                      tableName
    String                      transactionNo
    Boolean                     isActive

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static mapping = {
        isActive defaultValue: '1'
    }

    static constraints = {
        journalNo(nullable: false, blank: false, unique: true)
        transactionNo(blank: false, nullable: false, unique: ['tableName', 'moduleName', 'isActive'] )
        transactionDate(blank: false, nullable: false)
        journalStatus(nullable: false)
        moduleName(blank: false, nullable: false)
        tableName(blank: false, nullable: false)
        isActive(blank: true, nullable: true)

        userCreated(nullable: false)
        userUpdated(nullable: true)
    }
}
