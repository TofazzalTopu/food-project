package com.bits.bdfp.common

import com.bits.bdfp.inventory.sales.DistributionPoint

class DistributionPointNonBankVolt {
    DistributionPoint       distributionPoint
    NonBankVolt             nonBankVolt

    static constraints = {
        distributionPoint(nullable: false)
        nonBankVolt(nullable: false, unique: 'distributionPoint')
    }
}
