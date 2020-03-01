package com.bits.bdfp.customer

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class CustomerSettlement {
    EnterpriseConfiguration enterprise
    CustomerMaster          customerMaster
    Date                    settlementDate
    Float                   receivableAmount
    Float                   securityDeposit
    Float                   dueToCustomer
    Float                   dueToCompany
    Float                   adjustAmount
    Float                   withdrawAmount
    String                  paidThrough
    String                  reference
    String                  transactionNo

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    lastUpdated

    static mapping = {
        receivableAmount defaultValue: "0.00"
        securityDeposit defaultValue: "0.00"
        dueToCustomer defaultValue: "0.00"
        dueToCompany defaultValue: "0.00"
        adjustAmount defaultValue: "0.00"
        withdrawAmount defaultValue: "0.00"
    }

    static constraints = {
        enterprise(nullable: false)
        customerMaster(nullable: false)
        settlementDate(blank: false, nullable: false)
        receivableAmount(blank: false, nullable: false)
        securityDeposit(blank: false, nullable: false)
        dueToCustomer(blank: false, nullable: false)
        dueToCompany(blank: false, nullable: false)
        adjustAmount(blank: false, nullable: false)
        withdrawAmount(blank: true, nullable: false)
        paidThrough(nullable: true, inList: ["Cash", "Bank"])
        reference(blank: true, nullable: true)
        transactionNo(blank: false, nullable: false)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
    }
}
