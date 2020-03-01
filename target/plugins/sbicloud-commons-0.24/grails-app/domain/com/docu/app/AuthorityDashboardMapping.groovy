package com.docu.app

import com.docu.security.UserAuthority

class AuthorityDashboardMapping {
    UserAuthority authority
    long moduleId
    String dashboardUrl

    static constraints = {
    }

}
