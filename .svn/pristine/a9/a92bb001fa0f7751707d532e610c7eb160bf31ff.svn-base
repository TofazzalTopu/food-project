package com.bits.bdfp.geolocation.territoryconfiguration

import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.geolocation.TerritoryConfigurationService
import com.bits.bdfp.settings.BusinessUnitConfiguration
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readTerritoryConfigurationAction")
class ReadTerritoryConfigurationAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    TerritoryConfigurationService territoryConfigurationService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            TerritoryConfiguration territoryConfiguration = territoryConfigurationService.read(Long.parseLong(params.id))
            EnterpriseConfiguration enterpriseConfiguration = EnterpriseConfiguration.read(territoryConfiguration.enterpriseConfiguration.id)
            BusinessUnitConfiguration businessUnitConfiguration = BusinessUnitConfiguration.read(territoryConfiguration.businessUnitConfiguration.id)
            Map result = [enterpriseConfiguration: enterpriseConfiguration, territoryConfiguration: territoryConfiguration, businessUnitConfiguration: businessUnitConfiguration]
            return result
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