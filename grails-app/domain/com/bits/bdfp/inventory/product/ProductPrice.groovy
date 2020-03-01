package com.bits.bdfp.inventory.product

import com.bits.bdfp.settings.BusinessUnitConfiguration
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class ProductPrice {
    EnterpriseConfiguration     enterpriseConfiguration
    BusinessUnitConfiguration   businessUnitConfiguration
    ProductPricingType          productPricingType
    String                      name
    Date                        dateEffectiveFrom
    Date                        dateEffectiveTo
    Boolean                     isActive

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        enterpriseConfiguration(nullable: false)
        businessUnitConfiguration(nullable: false)
        productPricingType(nullable: false)
        name(nullable: false, blank: false, unique: ['productPricingType','enterpriseConfiguration'])
        dateEffectiveFrom(nullable: false)
        dateEffectiveTo(nullable: true)
        isActive(nullable: false)
        userCreated(blank: false,nullable: false)
        userUpdated(blank: true,nullable: true)
    }
}
