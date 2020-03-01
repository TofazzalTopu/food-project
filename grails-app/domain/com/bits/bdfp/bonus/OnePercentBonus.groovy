package com.bits.bdfp.bonus

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class OnePercentBonus {

    EnterpriseConfiguration enterpriseConfiguration
    CustomerMaster customerMaster
    FinishProduct finishProduct
    Date lastCalculated
    Date lastCalculatedFactory
    Boolean isActive
    BonusPercent bonusPercent

    ApplicationUser                 userCreated
    ApplicationUser                 userUpdated
    Date                            dateCreated
    Date                            lastUpdated

    static constraints = {
        enterpriseConfiguration(nullable: false)
        customerMaster(nullable: false)
        finishProduct(nullable: false)
        lastCalculated(nullable: false)
        lastCalculatedFactory(nullable: false)
        bonusPercent(nullable: false)
        isActive(nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        lastUpdated(nullable: true)
    }
}
