package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.customer.CustomerMaster
import com.docu.security.ApplicationUser

class YearlySalesTargetByVolume {

    int                         targetYear
    CustomerMaster              employee
    Date                        startDate
    Date                        endDate
    boolean                     isActive
    boolean                     isSalesManIncluded
    YearlySalesTargetByVolume   yearlySalesTargetByVolume

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static mapping = {
        isActive defaultValue: '1'
        isSalesManIncluded defaultValue: '0'
    }

    static constraints = {
        targetYear(blank: false, nullable: false, maxSize: 4)
        employee(nullable: false, unique: 'targetYear')
        startDate(blank: false, nullable: false)
        endDate(blank: false, nullable: false)
        isActive(blank: false, nullable: false)
        isSalesManIncluded(blank: false, nullable: false)
        yearlySalesTargetByVolume(blank: true, nullable: true)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
    }
}
