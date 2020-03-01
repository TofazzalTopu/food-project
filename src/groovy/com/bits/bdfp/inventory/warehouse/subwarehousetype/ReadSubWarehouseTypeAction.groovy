package com.bits.bdfp.inventory.warehouse.subwarehousetype

import com.bits.bdfp.inventory.warehouse.SubWarehouseType
import com.bits.bdfp.inventory.warehouse.SubWarehouseTypeService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readSubWarehouseTypeAction")
class ReadSubWarehouseTypeAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  SubWarehouseTypeService subWarehouseTypeService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
        SubWarehouseType subWarehouseType= subWarehouseTypeService.read(Long.parseLong(params.id))
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(subWarehouseType.enterpriseConfiguration.id)
        return [subWarehouseType:subWarehouseType,enterpriseConfiguration:enterpriseConfiguration]
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