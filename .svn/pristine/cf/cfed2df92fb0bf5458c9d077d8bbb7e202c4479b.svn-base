package com.bits.bdfp.bonus

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class AdjustOnePercentBonus {

    EnterpriseConfiguration         enterpriseConfiguration
    CustomerMaster                  customerMaster
    Date                            calculatedTo
    Date                            calculatedFrom
    Float                           bonus
    String                          transactionNo

    ApplicationUser                 userCreated
    ApplicationUser                 userUpdated
    Date                            dateCreated
    Date                            lastUpdated

    static constraints = {
        enterpriseConfiguration(nullable: false)
        calculatedTo(nullable: false)
        calculatedFrom(nullable: false)
        bonus(nullable: false)
        transactionNo(nullable: false)
        customerMaster(nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        lastUpdated(nullable: true)
    }
}
