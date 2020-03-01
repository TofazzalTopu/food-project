package com.bits.bdfp.geolocation.territorysubarea

import com.bits.bdfp.geolocation.TerritoryConfigurationService
import com.bits.bdfp.geolocation.TerritorySubAreaService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 9/6/15.
 */
@Component("searchTerritorySubAreaByTerritoryAction")
class SearchTerritorySubAreaByTerritoryAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    TerritorySubAreaService territorySubAreaService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return territorySubAreaService.searchGeoLocationMappingByTerritory(params)
        } catch (Exception ex) {
            log.error(ex.message)
            return new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }
}
