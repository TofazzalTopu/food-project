package com.bits.bdfp.customer

import com.docu.security.ApplicationUser

class CustomerAssetLending {

        AssetLending                assetLending
        String                      assetName
        Date                        lendingDate
        String                      modelNumber
        Double                      assetCost

        ApplicationUser             userCreated
        ApplicationUser             userUpdated
        Date                        dateCreated
        Date                        lastUpdated

        static constraints = {
            assetLending(nullable: false)
            assetName(blank: false, nullable: false, maxSize: 100)
            lendingDate(blank: false, nullable: false)
            modelNumber(blank: false, nullable: false, maxSize: 100)
            assetCost(blank: false, nullable: false)

            userCreated(nullable: false)
            userUpdated(nullable: true)
        }

    @Override
    String toString() {
        return assetName
    }
}
