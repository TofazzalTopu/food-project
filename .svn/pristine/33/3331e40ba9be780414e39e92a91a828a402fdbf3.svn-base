package com.bits.bdfp.settings.bussinessday

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class FinancialYear {
    EnterpriseConfiguration enterpriseConfiguration
    Date dateStart
    Date dateEnd
    Boolean isOpened
    String name

    ApplicationUser     userCreated
    ApplicationUser     userLastUpdated
    Date                dateCreated
    Date                dateLastUpdated


    static constraints = {
        enterpriseConfiguration(nullable: false)
        userLastUpdated(nullable: true,blank:true)
        dateLastUpdated(nullable: true,blank:true)
        dateStart(nullable: false,blank:false)
        dateEnd(nullable: false,blank:false)
        name(nullable: false,blank:false,unique: 'enterpriseConfiguration')
        userCreated(nullable: false,blank:false)
        dateCreated(nullable: false,blank:false)
    }
    def String toString() {
        return name;
    }
}
