package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.customer.CustomerMaster
import com.docu.security.ApplicationUser

class SalesHeadByVolume {
    int                     targetYear
    CustomerMaster          employee
    Date                    startDate
    Date                    endDate
    boolean                 isActive

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    lastUpdated

    static mapping = {
        isActive defaultValue: '1'
    }

    static constraints = {
        targetYear(blank: false, nullable: false, maxSize: 4)
        employee(nullable: false, unique: 'targetYear')
        startDate(blank: false, nullable: false)
        endDate(blank: false, nullable: false)
        isActive(blank: false, nullable: false)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
    }

}
