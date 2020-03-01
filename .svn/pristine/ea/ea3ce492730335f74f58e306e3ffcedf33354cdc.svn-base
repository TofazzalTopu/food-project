package com.bits.bdfp.finance

import com.bits.bdfp.customer.CustomerMaster
import com.docu.security.ApplicationUser

class AdjustMiscellaneousFeesWithReceivable {
    CustomerMaster              customerMaster
    String                      expenseType
    Double                      receivableAmount
    Double                      appliedAmount
    Double                      dueAmount
    Double                      withdrawAmount
    String                      ref
    String                      paymentType

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        customerMaster(nullable: false,blank: false)
        expenseType(nullable: false,blank: false)
        receivableAmount(nullable: false,blank: false)
        appliedAmount(nullable: false,blank: false)
        dueAmount(nullable: false,blank: false)
        withdrawAmount(nullable: true,blank: true)
        ref(nullable: true,blank: true)
        paymentType(nullable: true,blank: true)

        userCreated(nullable: false,blank: false)
        userUpdated(nullable: true,blank: false)
        dateCreated(nullable: false,blank: false)
        lastUpdated(nullable: true,blank: false)
    }
}
