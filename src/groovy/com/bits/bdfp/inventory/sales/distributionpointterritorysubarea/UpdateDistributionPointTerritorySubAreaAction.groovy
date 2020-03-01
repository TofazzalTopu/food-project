package com.bits.bdfp.inventory.sales.distributionpointterritorysubarea

import com.bits.bdfp.inventory.sales.DistributionPointTerritorySubArea
import com.bits.bdfp.inventory.sales.DistributionPointTerritorySubAreaService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateDistributionPointTerritorySubAreaAction")
class UpdateDistributionPointTerritorySubAreaAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    DistributionPointTerritorySubAreaService distributionPointTerritorySubAreaService

    public Object preCondition(Object params, Object object) {
        DistributionPointTerritorySubArea distributionPointTerritorySubArea = DistributionPointTerritorySubArea.read(Long.parseLong(params?.id?.toString()))
        distributionPointTerritorySubArea.properties = params
        if (!distributionPointTerritorySubArea.validate()) {
            return null
        }
        return distributionPointTerritorySubArea
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return distributionPointTerritorySubAreaService.update(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }
}
