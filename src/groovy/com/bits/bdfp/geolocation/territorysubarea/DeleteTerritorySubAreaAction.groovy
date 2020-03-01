package com.bits.bdfp.geolocation.territorysubarea

import com.bits.bdfp.customer.CustomerTerritorySubArea
import com.bits.bdfp.geolocation.TerritorySubArea
import com.bits.bdfp.geolocation.TerritorySubAreaService
import com.bits.bdfp.inventory.sales.DistributionPointTerritorySubArea
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteTerritorySubAreaAction")
class DeleteTerritorySubAreaAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  TerritorySubAreaService territorySubAreaService

   public Object preCondition(Object params, Object object) {
  try{
    return territorySubAreaService.read(Long.parseLong(params.id.toString()))
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
      return territorySubAreaService.delete(object)
    }
    catch (Exception ex) {
    log.error(ex.message)
      return new Integer(0)
    }
  }


    public Object deleteTerritorysubarea(Object params) {
        try {
            TerritorySubArea territorySubArea = TerritorySubArea.read(Long.parseLong(params.id))
            if(territorySubArea){
                if(CustomerTerritorySubArea.countByTerritorySubArea(territorySubArea) > 0){
                    return "Territory is Asssigned to Customer"
                }
                else if(DistributionPointTerritorySubArea.countByTerritorySubArea(territorySubArea) > 0){
                    return "Territory is Asssigned to Distribution Point"
                }
                else{
                    territorySubArea.delete()
                    return "Successfully Deleted"
                }

            }
//           return  territorySubAreaService.deleteTerritorySubArea(Long.parseLong(params.id))
//            return territorySubAreaService.deleteTerritorysubarea(Long.parseLong(params.id))

        }
        catch (Exception ex) {
            log.error(ex.message)
            return new Integer(0)
        }
    }

}