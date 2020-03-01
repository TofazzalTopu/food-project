package com.docu.security

class LoginHistory {

    String      userName       // ApplicationUser username = pin
    String      officeCode
    long        officeTypeId
    Date        loginTime     // systemDate
    Date        logoutTime
    String      loginIPAddress
    String      logoutIPAddress

    static constraints = {
        userName()
        officeCode()
        officeTypeId()
        loginTime()
        logoutTime(nullable: true)
        loginIPAddress(nullable: true)
        logoutIPAddress(nullable:true)
    }

}
