package com.bits.bdfp.promotion

import com.docu.security.ApplicationUser

class PromotionPackage {
    Promotion       promotion
    String          packageName
    Double          discountAmount
    String          remarks
    boolean         isActive
    boolean         isUsed
    ApplicationUser userCreated
    ApplicationUser userUpdated
    Date            dateCreated
    Date            dateUpdated

    static mapping = {
        isActive defaultValue: '1'
        isUsed defaultValue: '0'
    }

    static constraints = {
        promotion(blank: false, nullable: false)
        packageName(blank: false, nullable: false)
        discountAmount(nullable: true)
        remarks(nullable: true)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        dateUpdated(nullable: true)
    }
}
