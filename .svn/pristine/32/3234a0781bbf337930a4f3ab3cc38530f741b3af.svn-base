package com.docu.security


import com.docu.security.ApplicationUser

class UserFirstLoginPasswordVerification {

    ApplicationUser applicationUser
    Date            dateVerification
    Boolean         isVerified

    static constraints = {
        applicationUser(nullable: false,unique: true)
        dateVerification(nullable: true)
        isVerified(nullable: false)
    }
}
