package com.bits.bdfp.settings.bussinessday

import com.docu.security.ApplicationUser

class BusinessMonth {
    FinancialYear financialYear
    Date          startDate
    Date          endDate
    Date          openDate
    Date          closeDate
    Boolean       isOpen
    Integer       month
    String        monthName


    ApplicationUser     userCreated
    ApplicationUser     userLastUpdated
    Date                dateCreated
    Date                dateLastUpdated

    static constraints = {
        financialYear(nullable: false)
        startDate(nullable: false)
        endDate(nullable: true)
        openDate(nullable: true)
        closeDate(nullable: true)
        isOpen(nullable: true)
        month(nullable: false)
        monthName(nullable:false)
        userCreated(blank: false,nullable: false)
        userLastUpdated(blank: true,nullable: true)
        dateCreated(blank: false,nullable: false)
        dateLastUpdated(blank: true,nullable: true)
    }
}
