package com.docu.security

/**
 * Created by mdalinaser.khan on 11/11/15.
 */
class UserLoginFailStatus {
    ApplicationUser     applicationUser
    int                 loginFailCount
    int                 unlockCount

    static constraints = {
        applicationUser(blank: false, nullable: false, unique: true)
        loginFailCount(blank: false, nullable: false)
        unlockCount(blank: false, nullable: false)
    }
}
