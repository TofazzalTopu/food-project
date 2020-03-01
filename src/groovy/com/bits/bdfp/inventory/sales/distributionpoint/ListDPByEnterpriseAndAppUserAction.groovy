package com.bits.bdfp.inventory.sales.distributionpoint

import com.bits.bdfp.inventory.sales.DistributionPointService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 5/11/16.
 */
@Component("listDPByEnterpriseAndAppUserAction")
class ListDPByEnterpriseAndAppUserAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    DistributionPointService distributionPointService
    @Autowired
    SpringSecurityService springSecurityService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
            params.put("applicationUserId", applicationUser.id)
            return distributionPointService.listDpByEnterpriseAndAppUser(params)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }
}
