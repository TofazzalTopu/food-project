package com.docu.commons

class AddressType {

    String          addressTypeName    //present, parmanent, emergency, Work
    String          description

    static constraints = {
        addressTypeName(blank: false, nullable: false, maxSize: 30, unique: true)
        description(blank:true, nullable:true, maxSize: 150)
    }

    static mapping = {
        cache: 'read-only'
    }

    def String toString() {
        return addressTypeName;    //To change body of overridden methods use File | Settings | File Templates.
    }
}
