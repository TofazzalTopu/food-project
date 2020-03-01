package com.bits.bdfp.settings.enterpriseconfiguration

import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 11/19/15.
 */
@Component("enterpriseMappingForApplicationUserAction")
class EnterpriseMappingForApplicationUserAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return enterpriseConfigurationService.enterpriseMappingByApplicationUser(params)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }
}
