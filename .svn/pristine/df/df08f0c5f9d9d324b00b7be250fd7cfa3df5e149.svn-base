package com.bits.bdfp.inventory.product

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.MeasureUnitConfiguration
import com.docu.security.ApplicationUser

class ProductPackage {
    EnterpriseConfiguration     enterpriseConfiguration
    FinishProduct               finishProduct
    PackageType                 packageType
    MeasureUnitConfiguration    measureUnitConfiguration
    String                      conversionFactor
    Integer                     packSize

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        dateUpdated

    static constraints = {
        enterpriseConfiguration(nullable: false)
        finishProduct(nullable: false,unique: 'enterpriseConfiguration')
        packageType(nullable: false)
        measureUnitConfiguration(nullable: false)
        conversionFactor(nullable: true)
        packSize(nullable: false,min: 1)
        userCreated(blank: false,nullable: false)
        userUpdated(blank: true,nullable: true)
        dateCreated(blank: false,nullable: false)
        dateUpdated(blank: true,nullable: true)
    }

}
