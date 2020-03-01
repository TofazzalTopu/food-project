package com.bits.bdfp.finance

import com.bits.bdfp.common.CashPool
import com.bits.bdfp.common.DepositPool
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class DepositCashToDepositPool {

    EnterpriseConfiguration     enterpriseConfiguration
    DistributionPoint           distributionPoint
    CashPool                    cashPool
    DepositPool                 depositPool
    Boolean                     hoDeposit
    Float                       depositToBankAccount
    Float                       depositToHoCash
    Float                       salesAmount
    Float                       sdAmount
    Float                       total
    String                      transactionNo
    String                      status

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        enterpriseConfiguration(blank: false, nullable: false)
        distributionPoint(blank: false, nullable: false)
        cashPool(blank: false, nullable: false)
        depositPool(blank: true, nullable: true)
        hoDeposit(blank: true, nullable: true)
        depositToBankAccount(blank: true, nullable: true)
        depositToHoCash(blank: true, nullable: true)
        salesAmount(blank: true, nullable: true)
        sdAmount(blank: true, nullable: true)
        total(blank: false, nullable: false)
        transactionNo(blank: false, nullable: false)
        status(blank: false, nullable: false)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
        dateCreated(blank: false, nullable: false)
        lastUpdated(blank: true, nullable: true)
    }
}
