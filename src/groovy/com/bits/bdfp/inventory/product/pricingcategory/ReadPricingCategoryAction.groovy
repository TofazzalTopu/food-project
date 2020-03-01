package com.bits.bdfp.inventory.product.pricingcategory

import com.bits.bdfp.inventory.product.PricingCategory
import com.bits.bdfp.inventory.product.PricingCategoryService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readPricingCategoryAction")
class ReadPricingCategoryAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  PricingCategoryService pricingCategoryService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
        PricingCategory pricingCategory= pricingCategoryService.read(Long.parseLong(params.id))
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(pricingCategory.enterpriseConfiguration.id)
        return [pricingCategory:pricingCategory,enterpriseConfiguration:enterpriseConfiguration]
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