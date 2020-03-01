package com.bits.bdfp.settings

import com.docu.security.ApplicationUser

class MeasureUnitConfiguration {
    EnterpriseConfiguration enterpriseConfiguration
    String              name
    String              note
    Boolean             isFree
    Boolean             isMasterUom
    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                dateUpdated

    static constraints = {
        enterpriseConfiguration(nullable: false)
        name(blank: false,nullable: false,unique: 'enterpriseConfiguration',)
        note(blank: true,nullable: true)
        isFree(nullable: true)
        isMasterUom(nullable: true)
        userCreated(blank: false,nullable: false)
        userUpdated(blank: true,nullable: true)
        dateCreated(blank: false,nullable: false)
        dateUpdated(blank: true,nullable: true)
    }

    static mapping = {
        note type: 'text'
    }

    @Override
    public String toString() {
        return  name ;
    }
}
