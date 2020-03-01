package com.docu.security

class SetupOption {

    String          keyName
    String          keyValue
    String          comments

    static mapping = {
        version: false
        cache: 'read-only'
    }

    static constraints = {
        keyName(blank: false, nullable: false, unique: true)
        keyValue(blank: false, nullable: false, unique: true)
        comments(blank: true, nullable: true)
    }

    @Override
    String toString() {
        return keyName
    }
}
