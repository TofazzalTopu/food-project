package com.bits.bdfp.inventory.product.externalproduct

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class ExternalProductStock {
    EnterpriseConfiguration enterpriseConfiguration

    ExternalProduct             externalProduct
    Float                       inQuantity
    Float                       outQuantity

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static mapping = {
        inQuantity defaultValue: "0"
        outQuantity defaultValue: "0"
    }

    static constraints = {
        enterpriseConfiguration(nullable: false, blank:false)
        externalProduct(nullable: false, blank:false)
        inQuantity(nullable: false, blank:false)
        outQuantity(nullable: false, blank: false)
        userCreated(nullable: false,blank: false)
        userUpdated(nullable: true,blank: true)
    }
    @Override
    public String toString() {
        return externalProduct.name
    }
}
