package com.bits.bdfp.settings.businessunitconfiguration

import com.bits.bdfp.settings.BusinessUnitConfigurationService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 9/7/15.
 */
@Component("listBusinessUnitEnterpriseAction")
class ListBusinessUnitEnterpriseAction extends Action {

    @Autowired
    BusinessUnitConfigurationService businessUnitConfigurationService

    @Override
    protected Object preCondition(def Object object1, def Object object2) {
        return null
    }

    @Override
    protected Object postCondition(def Object object1, def Object object2) {
        return null
    }

    @Override
    protected Object execute(Object params, Object object) {
        try {
            List objectList

            objectList = businessUnitConfigurationService.businessList(params)
            return objectList
        }
        catch (Exception e) {

        }
    }
}
