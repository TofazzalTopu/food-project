package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.customer.CustomerMaster
import com.docu.security.ApplicationUser

class DailySalesTargetByVolume {

    YearlySalesTargetByVolume   yearlySalesTargetByVolume
    Date                        targetDate
    CustomerMaster              employee
    boolean                     isActive

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static mapping = {
        isActive defaultValue: '1'
    }

    static constraints = {
        yearlySalesTargetByVolume(nullable: false)
        targetDate(blank: false, nullable: false)
        employee(nullable: false, unique: 'targetDate')
        isActive(blank: false, nullable: false)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
    }
}
