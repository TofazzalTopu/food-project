package com.bits.bdfp.geolocation.territorysubarea

import com.bits.bdfp.geolocation.TerritorySubAreaService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 6/20/16.
 */
@Component("flexboxTerritorySubAreaByTerritoryAction")
class FlexboxTerritorySubAreaByTerritoryAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    TerritorySubAreaService territorySubAreaService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return territorySubAreaService.listFlexBoxTerritorySubAreaByTerritory(params)
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
