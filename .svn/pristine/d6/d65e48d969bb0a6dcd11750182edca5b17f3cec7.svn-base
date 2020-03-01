package com.bits.bdfp.settings.bussinessday

import com.docu.security.ApplicationUser

class BusinessDayTime {
    FinancialYear   financialYear
    Integer         date
    Integer         month
    Integer         year
    String          monthString
    String          startTime
    String          endTime
    Date            businessDate


    ApplicationUser     userCreated
    ApplicationUser     userLastUpdated
    Date                dateCreated
    Date                dateLastUpdated

    static constraints = {
        startTime(nullable: true,blank:true)
        endTime(nullable: true,blank:true)
        financialYear(nullable: false,blank:false)
        date(nullable: false,blank:false)
        month(nullable: false,blank:false)
        monthString(nullable: false,blank: false)
        year(nullable: false,blank:false)
        businessDate(nullable: false,blank:false)
        userCreated(nullable: false,blank:false)
        userLastUpdated(nullable: true,blank:true)
        dateCreated(nullable: false,blank:false)
        dateLastUpdated(nullable: true,blank:true)
    }
}
