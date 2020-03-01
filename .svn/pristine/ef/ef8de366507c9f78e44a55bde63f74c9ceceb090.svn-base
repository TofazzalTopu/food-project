package com.docu.commons

class Gender {
    String      genderType
    String      description

    static constraints = {
        genderType(blank: false, nullable: false, maxSize: 30, unique: true)
        description(blank:true, nullable: true, maxSize: 150)
    }

    static mapping = {
        cache: 'read-only'
    }

    def String toString() {
      return genderType;    //To change body of overridden methods use File | Settings | File Templates.
    }
}
