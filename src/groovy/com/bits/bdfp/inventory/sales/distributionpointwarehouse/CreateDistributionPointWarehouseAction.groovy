package com.bits.bdfp.inventory.sales.distributionpointwarehouse

import com.bits.bdfp.inventory.sales.DistributionPointWarehouse
import com.bits.bdfp.inventory.sales.DistributionPointWarehouseService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createDistributionPointWarehouseAction")
class CreateDistributionPointWarehouseAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  DistributionPointWarehouseService distributionPointWarehouseService

  public Object preCondition(Object params, Object object) {
    try {
      DistributionPointWarehouse distributionPointWarehouse = (DistributionPointWarehouse) object
      if (!distributionPointWarehouse.validate()) {
        return null
      }
      return distributionPointWarehouse
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return distributionPointWarehouseService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}