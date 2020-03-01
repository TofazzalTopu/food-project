package com.bits.bdfp.accounts

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

/**
 * Created by abhijit.majumder on 1/21/2016.
 */
class ChartOfAccountLayer {
    int             layerNumber
    String          layerName
    int             layerCodeLength
    EnterpriseConfiguration enterpriseConfiguration
    String          parentLayerId

    ApplicationUser userCreated
    ApplicationUser userUpdated
    Date            dateCreated
    Date            lastUpdated
    Boolean         isActive

    static constraints = {
        layerNumber(blank: false,nullable: false,unique: false)
        layerName(blank: false,nullable: false)
        layerCodeLength(blank: false,nullable: false)
        parentLayerId(nullable: true)

        userCreated(nullable: false)
        isActive(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        lastUpdated(nullable: true)
    }
}
