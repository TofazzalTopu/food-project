package com.bits.bdfp.accounts

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

/**
 * Created by abhijit.majumder on 1/21/2016.
 */
class ChartOfAccounts {
    EnterpriseConfiguration         enterpriseConfiguration
    ChartOfAccountLayer             chartOfAccountLayer
    String                          chartOfAccountCodeAuto
    String                          chartOfAccountCodeUser
    String                          chartOfAccountName
    int                             parentId
    String                          parentCode
    String                          accountType

    ApplicationUser                 userCreated
    ApplicationUser                 userUpdated
    Date                            dateCreated
    Date                            lastUpdated
    Boolean                         isActive

    static constraints = {
        chartOfAccountLayer(blank: false,nullable: false,unique: false)
        chartOfAccountCodeAuto(blank: false,nullable: false)
        chartOfAccountCodeUser(blank: false,nullable: false)
        chartOfAccountName(blank: false,nullable: false)
        enterpriseConfiguration(blank: false,nullable: false)
        parentId(blank: true,nullable: true)
        parentCode(blank: true,nullable: true)
        accountType(blank: true,nullable: true)

        userCreated(nullable: false)
        isActive(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        lastUpdated(nullable: true)
    }

    @Override
    String toString() {
        return chartOfAccountName
    }
}
