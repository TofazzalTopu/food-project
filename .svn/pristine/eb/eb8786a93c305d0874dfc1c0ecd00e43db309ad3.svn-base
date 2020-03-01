package com.bits.bdfp.geolocation.territorysubarea

import com.bits.bdfp.geolocation.TerritorySubAreaService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 1/7/16.
 */
@Component("listTerritorySubAreaByApplicationUserAction")
class ListTerritorySubAreaByApplicationUserAction  extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    TerritorySubAreaService territorySubAreaService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return territorySubAreaService.listTerritorySubAreaByApplicationUser()
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
