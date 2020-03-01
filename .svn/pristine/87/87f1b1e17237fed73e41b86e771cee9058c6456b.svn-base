package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.customer.CustomerMaster
import com.docu.security.ApplicationUser

class DailySalesTargetByAmount {
    YearlySalesTargetByAmount   yearlySalesTargetByAmount
    Date                        targetDate
    CustomerMaster              employee
    BigDecimal                  targetAmount
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
        targetDate(blank: false, nullable: false)
        employee(nullable: false, unique: 'targetDate')
        targetAmount(blank: false, nullable: false)
        isActive(blank: false, nullable: false)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
    }
}
