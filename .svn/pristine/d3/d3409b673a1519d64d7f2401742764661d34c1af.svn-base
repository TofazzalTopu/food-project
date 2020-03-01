package com.bits.bdfp.inventory.setup.vattype

import com.bits.bdfp.inventory.setup.VatType
import com.bits.bdfp.inventory.setup.VatTypeService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readVatTypeAction")
class ReadVatTypeAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  VatTypeService vatTypeService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
       VatType vatType = vatTypeService.read(Long.parseLong(params.id))
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(vatType.enterpriseConfiguration.id)
        return [vatType:vatType,enterpriseConfiguration:enterpriseConfiguration]
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