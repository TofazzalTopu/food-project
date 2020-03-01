package com.bits.bdfp.geolocation.territoryconfiguration

import com.bits.bdfp.geolocation.TerritoryConfigurationService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 9/6/15.
 */
@Component("searchTerritoryByEnterpriseAction")
class SearchTerritoryByEnterpriseAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    TerritoryConfigurationService territoryConfigurationService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return territoryConfigurationService.searchTerritoryByEnterprise(Long.parseLong(params.enterpriseId.toString()))
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
