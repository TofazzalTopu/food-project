package com.docu.security

/**
 * Created by mdalinaser.khan on 11/10/15.
 * User Type: 1. Super Admin, 2. Admin, 3. Other, 4. Customer
 */
class UserType {
    String typeName
    String comments

    static constraints = {
        typeName(blank: false, nullable: false, unique: true, maxSize: 20)
        comments(blank: true, nullable: true, maxSize: 500)
    }

    @Override
    String toString() {
        return typeName
    }
}
