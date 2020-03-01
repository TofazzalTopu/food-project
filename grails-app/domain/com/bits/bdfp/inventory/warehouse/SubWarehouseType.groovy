package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

/*
FG
Scrap
By Product
Etc.
 */
class SubWarehouseType {
    EnterpriseConfiguration enterpriseConfiguration
    String                  name
    String                  note

    ApplicationUser userCreated
    ApplicationUser userUpdated
    Date            dateCreated
    Date            dateUpdated

    static constraints = {
        enterpriseConfiguration(nullable: false)
        name(blank: false,nullable: false,unique: 'enterpriseConfiguration')
        note(nullable: true,blank: true)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
        dateCreated(blank: false, nullable: false)
        dateUpdated(blank: true, nullable: true)
    }
    @Override
    public String toString() {
        return name;
    }
}
