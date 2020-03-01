package com.bits.bdfp.inventory.warehouse.subwarehouse

import com.bits.bdfp.inventory.warehouse.SubWarehouse
import com.bits.bdfp.inventory.warehouse.SubWarehouseService
import com.bits.bdfp.inventory.warehouse.Warehouse
import com.bits.bdfp.inventory.warehouse.WarehouseService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readSubWarehouseAction")
class ReadSubWarehouseAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  SubWarehouseService subWarehouseService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
    @Autowired
    WarehouseService warehouseService

  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
        SubWarehouse subWarehouse= subWarehouseService.read(Long.parseLong(params.id))
        Warehouse warehouse=warehouseService.read(subWarehouse.warehouse.id)
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(warehouse.enterpriseConfiguration.id)
        return [subWarehouse:subWarehouse,warehouse:warehouse,enterpriseConfiguration:enterpriseConfiguration]
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