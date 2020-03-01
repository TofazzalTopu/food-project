package com.bits.bdfp.finance

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser
import com.sun.org.apache.xpath.internal.operations.Bool

class WithdrawSecurityDeposit {

    String code
    EnterpriseConfiguration enterpriseConfiguration
    Date date
    TerritoryConfiguration territoryConfiguration
    DistributionPoint distributionPoint

    CustomerMaster customerMaster
    Double securityDepositBalance
    Double receivableAmount

    Double withdrawalAmount
    Double forceWithdrawalAmount

    Boolean isCashPayment
    Boolean isBankPayment
    String  bankRef

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    dateUpdated



    static constraints = {
        code(nullable: false, blank: false)
        enterpriseConfiguration(nullable: false, blank: false)
        date(nullable: true, blank: true)
        territoryConfiguration(nullable: true, blank: true)
        distributionPoint (nullable: true, blank: true)

        customerMaster (nullable: true, blank: true)
        securityDepositBalance(nullable: true, blank: true)
        receivableAmount(nullable: true, blank: true)
        withdrawalAmount(nullable: false, blank: false)
        forceWithdrawalAmount(nullable: true, blank: true)


        isCashPayment(nullable: true, blank: true)
        isBankPayment(nullable: true, blank: true)
        bankRef(nullable: true, blank: true)

        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        dateUpdated(nullable: true)


    }
}
