package com.bits.bdfp.inventory.product.packagetype

import com.bits.bdfp.inventory.product.PackageType
import com.bits.bdfp.inventory.product.PackageTypeService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readPackageTypeAction")
class ReadPackageTypeAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  PackageTypeService packageTypeService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
        PackageType packageType= packageTypeService.read(Long.parseLong(params.id))
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(packageType.enterpriseConfiguration.id)
        return [packageType:packageType,enterpriseConfiguration:enterpriseConfiguration]
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