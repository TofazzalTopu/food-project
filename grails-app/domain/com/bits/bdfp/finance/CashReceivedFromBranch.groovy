package com.bits.bdfp.finance

import com.bits.bdfp.common.BankAccount
import com.bits.bdfp.common.CashPool
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class CashReceivedFromBranch {

    EnterpriseConfiguration     enterpriseConfiguration
    DistributionPoint           distributionPoint
    DepositCashToDepositPool    depositCashToDepositPool
    BankAccount                 bankAccount
    CashPool                    cashPool
    Float                       sdAmount
    Float                       salesAmount

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        dateUpdated

    static constraints = {
        enterpriseConfiguration(nullable: false)
        distributionPoint(nullable: false)
        depositCashToDepositPool(nullable: false)
        bankAccount(nullable: true)
        cashPool(nullable: true)
        sdAmount(nullable: false)
        salesAmount(nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        dateUpdated(nullable: true)
    }
}
