package com.bits.bdfp.inventory.setup.chargetype

import com.bits.bdfp.inventory.setup.ChargeType
import com.bits.bdfp.inventory.setup.ChargeTypeService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readChargeTypeAction")
class ReadChargeTypeAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  ChargeTypeService chargeTypeService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
       ChargeType chargeType = chargeTypeService.read(Long.parseLong(params.id))
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(chargeType.enterpriseConfiguration.id)
        return [chargeType:chargeType,enterpriseConfiguration:enterpriseConfiguration]
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