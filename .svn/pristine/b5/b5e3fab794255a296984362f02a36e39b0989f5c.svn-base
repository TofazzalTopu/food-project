package com.bits.bdfp.inventory.sales.distributionpointwarehouse

import com.bits.bdfp.inventory.sales.DistributionPointWarehouseService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteDistributionPointWarehouseAction")
class DeleteDistributionPointWarehouseAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  DistributionPointWarehouseService distributionPointWarehouseService

   public Object preCondition(Object params, Object object) {
  try{
    return distributionPointWarehouseService.read(Long.parseLong(params.id.toString()))
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
      return distributionPointWarehouseService.delete(object)
    }
    catch (Exception ex) {
    log.error(ex.message)
      return new Integer(0)
    }
  }

}