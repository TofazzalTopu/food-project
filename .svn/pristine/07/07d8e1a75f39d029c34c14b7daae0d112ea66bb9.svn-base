package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.customer.CustomerMaster
import com.docu.security.ApplicationUser


/**
 * Created by abhijit.majumder on 2/23/2016.
 */
class VisitPlan {
    CustomerMaster      customerMaster
    String              placeOfVisit
    Date                dateFrom
    Date                dateTo
    String              timeFrom
    String              timeTo
    String              purpose
    String              remarks
    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                dateUpdated

    static constraints = {
        customerMaster(blank: false,nullable: false)
        placeOfVisit(nullable: false,blank:false)
        dateFrom(nullable: false,blank:false)
        dateTo(nullable: false,blank:false)
        timeFrom(nullable: false,blank:false)
        timeTo(nullable: false,blank:false)
        purpose(nullable: true,blank:true)
        remarks(nullable: true,blank:true)
        userCreated(nullable: false,blank: false)
        dateCreated(nullable: false,blank: false)
        userUpdated(nullable: true,blank: true)
        dateUpdated(nullable: true,blank: true)

    }
}
