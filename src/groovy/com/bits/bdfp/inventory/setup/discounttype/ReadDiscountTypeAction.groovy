package com.bits.bdfp.inventory.setup.discounttype

import com.bits.bdfp.inventory.setup.DiscountType
import com.bits.bdfp.inventory.setup.DiscountTypeService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readDiscountTypeAction")
class ReadDiscountTypeAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  DiscountTypeService discountTypeService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService

  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
       DiscountType discountType = discountTypeService.read(Long.parseLong(params.id))
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(discountType.enterpriseConfiguration.id)
        return [discountType:discountType,enterpriseConfiguration:enterpriseConfiguration]
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