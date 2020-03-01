package com.bits.bdfp.settings

import com.docu.security.ApplicationUser

class EnterpriseConfiguration {
    String              code
    String              name
    String              note
    Integer             noOfLayers
    Integer             codeLength
    Boolean             layerUsed
    Integer             coaUsed

    String              address
    String              phone
    String              email
    String              fax
    String              extension

    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                dateUpdated

    static constraints = {
        code(blank: false,nullable: false, unique: true)
        name(blank: false,nullable: false, unique: true)
        note(blank: true,nullable: true)
        noOfLayers(blank: false, nullable: false)
        codeLength(blank: false, nullable: false)
        layerUsed(blank: false, nullable: false)
        coaUsed(blank: false, nullable: false)

        address(blank: true, nullable: true)
        phone(blank: true, nullable: true)
        email(blank: true, nullable: true)
        fax(blank: true, nullable: true)
        extension(blank: true, nullable: true)

        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
        dateCreated(blank: false, nullable: false)
        dateUpdated(blank: true, nullable: true)
    }

    static mapping = {
        note type: 'text'
    }

    @Override
    public String toString() {
        return name + '-[' + code+']'
    }
}
