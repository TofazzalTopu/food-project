package com.bits.bdfp.inventory.product

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser
class MasterProduct {
    EnterpriseConfiguration enterpriseConfiguration
    String              code
    String              name
    String              note
    Boolean             isActive
    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                dateUpdated
    Long                sequenceNumber

    static constraints = {
        enterpriseConfiguration(nullable: false)
        code(blank: false,nullable: false,unique: 'enterpriseConfiguration')
        name(blank: false,nullable: false,unique: 'enterpriseConfiguration')
        note(blank: true,nullable: true)
        sequenceNumber(blank: true, nullable: true)
        isActive(blank: true, nullable: true)
        userCreated(blank: false,nullable: false)
        userUpdated(blank: true,nullable: true)
        dateCreated(blank: false,nullable: false)
        dateUpdated(blank: true,nullable: true)
    }

    static mapping = {
        note type: 'text'
        isActive defaultValue: '1'
        sequenceNumber defaultValue: "100"
    }

    @Override
    public String toString() {
        return name + '[' + code + ']';
    }
}
