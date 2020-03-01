package com.bits.bdfp.geolocation.territorysubarea

import com.bits.bdfp.geolocation.TerritorySubArea
import com.bits.bdfp.geolocation.TerritorySubAreaService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createTerritorySubAreaAction")
class CreateTerritorySubAreaAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  TerritorySubAreaService territorySubAreaService

  public Object preCondition(Object params, Object object) {
    try {
      TerritorySubArea territorySubArea = (TerritorySubArea) object
      if (!territorySubArea.validate()) {
        return null
      }
      return territorySubArea
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return territorySubAreaService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}