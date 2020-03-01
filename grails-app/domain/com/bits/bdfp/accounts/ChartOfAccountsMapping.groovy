package com.bits.bdfp.accounts

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class ChartOfAccountsMapping {
    EnterpriseConfiguration     enterprise
    COAType                     coaType
    ChartOfAccounts             chartOfAccounts

    ApplicationUser             userCreated
    Date                        dateCreated

    static constraints = {
        enterprise(nullable: false)
        coaType(nullable: false, blank: false)
        chartOfAccounts(nullable: false)
        userCreated(nullable: false)
    }
}
