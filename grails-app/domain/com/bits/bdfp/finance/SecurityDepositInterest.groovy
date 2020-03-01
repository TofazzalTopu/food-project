package com.bits.bdfp.finance

import com.bits.bdfp.customer.CustomerMaster
import com.docu.security.ApplicationUser

class SecurityDepositInterest {
    CustomerMaster          customerMaster
    String                  quarterName
    Double                  lastQuarterBalance
    Float                   interestPercentage
    Double                  interestAmount
    Date                    transactionDate

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    dateUpdated

    static constraints = {
        customerMaster(nullable: false)
        quarterName(nullable: false)
        lastQuarterBalance(nullable: false)
        interestPercentage(nullable: false)
        interestAmount(nullable: false)
        transactionDate(nullable: false)

        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        dateUpdated(nullable: true)
    }
}
