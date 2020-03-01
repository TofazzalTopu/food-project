package com.bits.bdfp.settings.enterpriseconfiguration

import com.bits.bdfp.settings.BusinessUnitConfigurationService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import groovy.sql.GroovyRowResult
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by tofazzal on 9/6/2015.
 */
@Component("enterpriseAutocompleteListAction")
class EnterpriseAutocompleteListAction extends Action{
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    BusinessUnitConfigurationService businessUnitConfigurationService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public List<GroovyRowResult> execute(Object object, Object params) {
        try {
            ApplicationUser applicationUser=(ApplicationUser) object
            return businessUnitConfigurationService.enterpriseList(applicationUser)
        } catch (Exception ex) {
            log.error(ex.message)
            throw  new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }
}
