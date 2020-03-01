package com.bits.bdfp.common

import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class CashPool {
    EnterpriseConfiguration enterpriseConfiguration
    String                  code
    String                  name
    String                  accountNo
    DistributionPoint       distributionPoint
    Boolean                 isActive
    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    lastUpdated

    static constraints = {
        enterpriseConfiguration(nullable: false)
        code(blank: false, nullable: false, unique: 'enterpriseConfiguration')
        name(blank: false, nullable: false, unique: 'enterpriseConfiguration')
        accountNo(nullable: true, blank: true)
        distributionPoint(blank: true, nullable: true)
        isActive(nullable: true)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
    }

    @Override
    public String toString() {
        return name + '['+ code+']';
    }
}
