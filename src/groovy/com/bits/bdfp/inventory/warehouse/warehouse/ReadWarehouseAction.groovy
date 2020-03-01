package com.bits.bdfp.inventory.warehouse.warehouse

import com.bits.bdfp.inventory.warehouse.Warehouse
import com.bits.bdfp.inventory.warehouse.WarehouseService
import com.bits.bdfp.settings.BusinessUnitConfiguration
import com.bits.bdfp.settings.BusinessUnitConfigurationService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readWarehouseAction")
class ReadWarehouseAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  WarehouseService warehouseService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
    @Autowired
    BusinessUnitConfigurationService businessUnitConfigurationService

  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
        Warehouse warehouse= warehouseService.read(Long.parseLong(params.id))
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(warehouse.enterpriseConfiguration.id)
        BusinessUnitConfiguration businessUnitConfiguration= businessUnitConfigurationService.read(warehouse.businessUnitConfiguration.id)
        return [warehouse:warehouse,enterpriseConfiguration:enterpriseConfiguration,businessUnitConfiguration:businessUnitConfiguration]
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