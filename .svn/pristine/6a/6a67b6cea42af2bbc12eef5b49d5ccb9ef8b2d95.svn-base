package com.bits.bdfp.geolocation.territorysubarea

import com.bits.bdfp.geolocation.TerritorySubAreaService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readTerritorySubAreaAction")
class ReadTerritorySubAreaAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  TerritorySubAreaService territorySubAreaService

  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
       return territorySubAreaService.read(Long.parseLong(params.id))
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