package com.bits.bdfp.common

import com.docu.security.ApplicationUser

class BankAccount {
    Bank                bank
    BankBranch          bankBranch
    String              accountNo
    String              ledgerAccountCode
    Boolean             isActive
    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                lastUpdated

    static constraints = {
        bank(nullable: false)
        bankBranch(nullable: false)
        accountNo(blank: false, nullable: false, unique: 'bankBranch')
        ledgerAccountCode(blank: false, nullable: false, maxSize: 30)
        isActive(nullable: true)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
    }

    @Override
    public String toString() {
        return "[" + accountNo + "] " + bank.name + "(" + bankBranch.name + ")"
    }
}
