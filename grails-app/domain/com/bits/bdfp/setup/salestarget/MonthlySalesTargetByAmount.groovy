package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.customer.CustomerMaster
import com.docu.security.ApplicationUser

class MonthlySalesTargetByAmount {
    YearlySalesTargetByAmount   yearlySalesTargetByAmount
    int                         targetYear
    int                         targetMonth

    CustomerMaster              employee
    BigDecimal                  targetAmount
    Date                        startDate
    Date                        endDate
    boolean                     isActive

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static mapping = {
        targetAmount defaultValue: '0'
        isActive defaultValue: '1'
    }

    static constraints = {
        yearlySalesTargetByAmount(nullable: false)
        targetYear(blank: false, nullable: false, maxSize: 4)
        targetMonth(blank: false, nullable: false, maxSize: 2)
        employee(nullable: false, unique: ['targetYear', 'targetMonth'])
        targetAmount(blank: false, nullable: false)
        startDate(blank: false, nullable: false)
        endDate(blank: false, nullable: false)
        isActive(blank: false, nullable: false)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
    }
}
