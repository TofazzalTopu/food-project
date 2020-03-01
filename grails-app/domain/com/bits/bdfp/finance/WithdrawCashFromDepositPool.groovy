package com.bits.bdfp.finance

import com.bits.bdfp.common.CashPool
import com.bits.bdfp.common.DepositPool
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class WithdrawCashFromDepositPool {

    EnterpriseConfiguration     enterprise
    DistributionPoint           distributionPoint
    CashPool                    cashPool
    DepositPool                 depositPool
    String                      transactionNo
    Float                       withdrawAmount


    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated


    static constraints = {
        enterprise(blank: false, nullable: false)
        distributionPoint(blank: false, nullable: false)
        cashPool(blank: false, nullable: false)
        depositPool(blank: false, nullable: false)
        transactionNo(blank: false, nullable: false)
        withdrawAmount(blank: false, nullable: false)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
    }
}
