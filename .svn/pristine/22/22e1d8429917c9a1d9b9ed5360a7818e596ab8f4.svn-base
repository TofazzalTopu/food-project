package com.bits.bdfp.inventory.setup.incentive

import com.bits.bdfp.settings.MeasureUnitConfiguration
import com.docu.security.ApplicationUser

class VolumeBasedIncentiveSlab {
    VolumeBasedIncentive        volumeBasedIncentive
    MeasureUnitConfiguration    masterUom
    String                      productSetName
    Float                       volumeFrom
    Float                       volumeTo
    Float                       incentiveAmountPct
    Float                       perUnitMasterUomCost
    Double                      incentiveAmountValue

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        volumeBasedIncentive(nullable: false, blank: false)
        masterUom(nullable: false, blank: false)
        productSetName(nullable: false, blank: false, maxSize: 50)
        volumeFrom(nullable: false, blank: false)
        volumeTo(nullable: false, blank: false)
        incentiveAmountPct(nullable: true, blank: true)
        perUnitMasterUomCost(nullable: false, blank: false)
        incentiveAmountValue(nullable: true, blank: true)

        userCreated(nullable: false, blank: false)
        userUpdated(nullable: true, blank: true)
    }
}
