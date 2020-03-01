package com.bits.bdfp.inventory.product.productpricingtype

import com.bits.bdfp.inventory.product.ProductPricingType
import com.bits.bdfp.inventory.product.ProductPricingTypeService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readProductPricingTypeAction")
class ReadProductPricingTypeAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  ProductPricingTypeService productPricingTypeService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
        ProductPricingType productPricingType= productPricingTypeService.read(Long.parseLong(params.id))
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(productPricingType.enterpriseConfiguration.id)
        return [productPricingType:productPricingType,enterpriseConfiguration:enterpriseConfiguration]
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