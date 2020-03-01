package com.bits.bdfp.inventory.product.masterproduct

import com.bits.bdfp.inventory.product.MasterProduct
import com.bits.bdfp.inventory.product.MasterProductService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readMasterProductAction")
class ReadMasterProductAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  MasterProductService masterProductService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
        MasterProduct masterProduct= masterProductService.read(Long.parseLong(params.id))
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(masterProduct.enterpriseConfiguration.id)
        return [masterProduct:masterProduct,enterpriseConfiguration:enterpriseConfiguration]
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