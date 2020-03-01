package com.bits.bdfp.finance

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class AdjustSecurityDepositWithInvoice {
    String                  code
    EnterpriseConfiguration enterpriseConfiguration
    Date                    date
    TerritoryConfiguration  territoryConfiguration
    DistributionPoint       distributionPoint

    Boolean                 isDpCustomer
    CustomerMaster          customerMaster

    Double                  amountAdjusted

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    dateUpdated
    static constraints = {
        code(nullable: false, blank: false)
        enterpriseConfiguration(nullable: false, blank: false)
        date(nullable: true, blank: true)
        territoryConfiguration(nullable: false, blank: false)
        distributionPoint (nullable: false, blank: false)

        isDpCustomer(nullable: false, blank: false)
        customerMaster(nullable: false, blank: false)
        amountAdjusted(nullable: true, blank: true)

        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        dateUpdated(nullable: true)
    }
}
