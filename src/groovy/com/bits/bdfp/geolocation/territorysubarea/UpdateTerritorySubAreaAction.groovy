package com.bits.bdfp.geolocation.territorysubarea

import com.bits.bdfp.geolocation.TerritorySubArea
import com.bits.bdfp.geolocation.TerritorySubAreaService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateTerritorySubAreaAction")
class UpdateTerritorySubAreaAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    TerritorySubAreaService territorySubAreaService

    public Object preCondition(Object params, Object object) {
        TerritorySubArea territorySubArea = TerritorySubArea.read(Long.parseLong(params?.id?.toString()))
        territorySubArea.properties = params
        if (!territorySubArea.validate()) {
            return null
        }
        return territorySubArea
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return territorySubAreaService.update(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }
}
