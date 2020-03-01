package com.bits.bdfp.accounts

import com.docu.security.ApplicationUser

class JournalDetails {

    Journal             journal
    ChartOfAccounts     chartOfAccounts
    String              particular
    Double              debitAmount
    Double              creditAmount
    String              prefixCode
    String              postfixCode
    Boolean             isActive

    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                lastUpdated

    static mapping = {
        debitAmount defaultValue: '0'
        creditAmount defaultValue: '0'
        isActive defaultValue: '1'
        prefixCode index: 'prefix_code_idx'
        postfixCode index: 'postfix_code_idx'
    }

    static constraints = {
        journal(nullable: false)
        chartOfAccounts(nullable: false)
        particular(blank: true, nullable: true)
        debitAmount(nullable: false)
        creditAmount(nullable: false)
        prefixCode(nullable: true)
        postfixCode(nullable: true)
        isActive(blank: true, nullable: true)

        userCreated(nullable: false)
        userUpdated(nullable: true)
    }
}
