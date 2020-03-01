package com.bits.bdfp.finance

import com.bits.bdfp.currency.CurrencyDemonstration
import com.docu.security.ApplicationUser

class CustomerPaymentCurrencyDenomination {
    CustomerPayment         customerPayment
    CurrencyDemonstration   currencyDemonstration
    long                    quantity

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    dateUpdated

    static constraints = {
        customerPayment(nullable: false)
        currencyDemonstration(nullable: false)
        quantity(nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        dateUpdated(nullable: true)
    }
}
