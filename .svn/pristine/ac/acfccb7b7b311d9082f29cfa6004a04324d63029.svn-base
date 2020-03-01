package com.bits.bdfp.inventory.sales.distributionpointterritorysubarea

import com.bits.bdfp.inventory.sales.DistributionPointTerritorySubArea
import com.bits.bdfp.inventory.sales.DistributionPointTerritorySubAreaService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createDistributionPointTerritorySubAreaAction")
class CreateDistributionPointTerritorySubAreaAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  DistributionPointTerritorySubAreaService distributionPointTerritorySubAreaService

  public Object preCondition(Object params, Object object) {
    try {
      DistributionPointTerritorySubArea distributionPointTerritorySubArea = (DistributionPointTerritorySubArea) object
      if (!distributionPointTerritorySubArea.validate()) {
        return null
      }
      return distributionPointTerritorySubArea
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return distributionPointTerritorySubAreaService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}