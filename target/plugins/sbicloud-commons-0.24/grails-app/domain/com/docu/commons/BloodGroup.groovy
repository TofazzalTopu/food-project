package com.docu.commons

class BloodGroup {
    String          groupName
    String          description

    static constraints = {
        groupName(blank: false, nullable: false, maxSize: 10, unique: true)
        description(blank:true, nullable:true, maxSize: 150)
    }

    static mapping = {
        cache: 'read-only'
    }

    def String toString() {
      return groupName;    //To change body of overridden methods use File | Settings | File Templates.
    }
}
