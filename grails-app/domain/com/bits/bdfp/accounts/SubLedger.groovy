package com.bits.bdfp.accounts

import com.docu.security.ApplicationUser

/***
 * a. Inside
 b. Outside
 c. Export
 d. Institutional
 */
class SubLedger {
    String          vin             // Voucher No
    String          accCode
    String          description
    Double          debit
    Double          credit
    String          transactionNo
    int             transactionType

    ApplicationUser userCreated
    ApplicationUser userUpdated
    Date            dateCreated
    Date            lastUpdated
    Boolean         isActive

    static constraints = {
        vin(blank: false,nullable: false,unique: true)
        accCode(blank: true,nullable: true)
        description(blank: true,nullable: false)
        debit(blank: true,nullable: false)
        credit(blank: true,nullable: false)
        transactionNo(blank: true,nullable: false)
        transactionType(blank: true,nullable: false)
        userCreated(nullable: false)
        isActive(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        lastUpdated(nullable: true)
    }

    @Override
    public String toString() {
        return vin;
    }
}
