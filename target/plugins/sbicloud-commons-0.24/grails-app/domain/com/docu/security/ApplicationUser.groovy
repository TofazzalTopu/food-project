package com.docu.security

class ApplicationUser {
    UserType            userType
    String              username   //pin if employee
    String              deviceId   //for Mobile app user /** added on 14-02-2017 */
    String              password
    String              confirmPassword
    String              fullName
    Boolean             enabled
    Boolean             accountExpired
    Boolean             accountLocked
    Boolean             passwordExpired
    String              colorTheme
    String              emailAddress
    Long                customerMasterId

    Date                dateCreated
    Date                lastUpdated

    static constraints = {
        userType(blank: true, nullable: true)
        username(blank: false, nullable: false, minSize: 3, maxSize: 30, unique: true)
        deviceId(blank: true, nullable: true, minSize: 3, maxSize: 30)
        password(blank: false, nullable: false)
        fullName()
        enabled()
        accountExpired()
        accountLocked()
        passwordExpired()
        colorTheme(nullable: true)
        emailAddress(nullable: true, blank: true, email: true)
        customerMasterId(blank: true, nullable: true)
    }

    static transients = ['confirmPassword']

    Set<UserAuthority> getAuthorities() {

        return ApplicationUserAuthority.findAllByApplicationUser(this).collect { it.userAuthority } as Set
    }
}
