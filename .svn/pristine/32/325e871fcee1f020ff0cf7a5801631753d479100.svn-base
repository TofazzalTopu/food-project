package com.bits.bdfp.settings.bussinessday

import com.docu.security.ApplicationUser

class BusinessHoliday {
    FinancialYear financialYear
    Integer day
    Integer month
    Integer year
    String note

    ApplicationUser     userCreated
    ApplicationUser     userLastUpdated
    Date                dateCreated
    Date                dateLastUpdated

    static constraints = {

        userCreated(blank: false,nullable: false)
        userLastUpdated(blank: true,nullable: true)
        dateCreated(blank: false,nullable: false)
        dateLastUpdated(blank: true,nullable: true)
    }

    static mapping = {
        sort 'name'
        note type: 'text'
      //  id generator: 'sequence', params: [sequence: 'bank_id_sequence']

    }
}
