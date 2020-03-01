package com.docu.commons

class Religion {

    String          religionName
    String          description

    static constraints = {
        religionName(blank: false, nullable: false, maxSize: 30, unique: true)
        description(blank:true, nullable:true, maxSize: 150)
    }

    static mapping = {
        cache: 'read-only'
    }

    def String toString() {
        return religionName;    //To change body of overridden methods use File | Settings | File Templates.
    }

}
