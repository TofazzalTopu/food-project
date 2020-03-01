package com.bits.bdfp.settings.bussinessday

import com.docu.security.ApplicationUser

class BusinessDay {
    Date            businessDate
    Date            systemDate
    String          openedFrom
    Boolean         isOpen

    ApplicationUser     userCreated
    ApplicationUser     userLastUpdated
    Date                dateCreated
    Date                dateLastUpdated

    static constraints = {
        businessDate(nullable: false)
        systemDate(nullable: false)
        openedFrom(nullable: false)
        isOpen(nullable: false)
        dateLastUpdated(nullable: true,blank:true)
        userLastUpdated(nullable: true,blank:true)

    }
    static mapping = {
     openedFrom type: 'text'
    }
}
