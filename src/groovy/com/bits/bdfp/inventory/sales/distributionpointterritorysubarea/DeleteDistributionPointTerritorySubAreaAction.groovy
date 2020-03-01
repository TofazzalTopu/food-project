package com.bits.bdfp.inventory.sales.distributionpointterritorysubarea

import com.bits.bdfp.inventory.sales.DistributionPointTerritorySubAreaService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteDistributionPointTerritorySubAreaAction")
class DeleteDistributionPointTerritorySubAreaAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  DistributionPointTerritorySubAreaService distributionPointTerritorySubAreaService

   public Object preCondition(Object params, Object object) {
  try{
    return distributionPointTerritorySubAreaService.read(Long.parseLong(params.id.toString()))
     }catch(Exception ex)
      {
        log.error(ex.getMessage());
      }
  }
   public Object postCondition(Object params, Object object) {
    //not implement
    return null
  }

   public Object execute(Object params, Object object) {
    try {
      return distributionPointTerritorySubAreaService.delete(object)
    }
    catch (Exception ex) {
    log.error(ex.message)
      return new Integer(0)
    }
  }

}