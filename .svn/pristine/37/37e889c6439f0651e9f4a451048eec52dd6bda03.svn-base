package com.bits.bdfp.inventory.sales

import com.bits.bdfp.customer.CustomerMaster
import com.docu.security.ApplicationUser

class AdjustSalesCommission {

    CustomerMaster          customerMaster
    BigDecimal              calculatedCommission = 0.00
    Float                   commission  // Amount not adjusted / Remaining Commission
    BigDecimal              adjustedAmount = 0.00
    String                  transactionNo

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    lastUpdated

    static mapping = {
        calculatedCommission defaultValue: "0.00"
        adjustedAmount defaultValue: "0.00"
    }

    static constraints = {
        customerMaster(nullable:false, blank:false)
        commission(nullable:false, blank:false)
        calculatedCommission(nullable: true)
        adjustedAmount(nullable: false)
        transactionNo(blank: true, nullable: true)
        userCreated(nullable: false)
        userUpdated(nullable: true)
    }
}
