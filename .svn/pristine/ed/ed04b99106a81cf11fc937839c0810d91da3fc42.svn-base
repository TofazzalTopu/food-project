package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.settings.BusinessUnitConfiguration
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class Warehouse {
    EnterpriseConfiguration     enterpriseConfiguration
    BusinessUnitConfiguration   businessUnitConfiguration
    String      name
    String      code
    String      address
    String      note
    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                dateUpdated
    static constraints = {
        name(blank: false,nullable: false,unique: 'businessUnitConfiguration')
        code(nullable: false,blank: false,unique: 'businessUnitConfiguration')
        address(blank: true,nullable: true)
        note(blank: true,nullable: true)
        enterpriseConfiguration(nullable: false)
        businessUnitConfiguration(nullable: false)
        userCreated(blank: false,nullable: false)
        userUpdated(blank: true,nullable: true)
        dateCreated(blank: false,nullable: false)
        dateUpdated(blank: true,nullable: true)
    }

    static mapping = {
        address type: 'text'
    }
    @Override
    public String toString() {
        return name ;
    }
}
