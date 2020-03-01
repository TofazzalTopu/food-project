package com.bits.bdfp.inventory.product.productcategory

import com.bits.bdfp.inventory.product.ProductCategory
import com.bits.bdfp.inventory.product.ProductCategoryService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readProductCategoryAction")
class ReadProductCategoryAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  ProductCategoryService productCategoryService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
        ProductCategory productCategory= productCategoryService.read(Long.parseLong(params.id))
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(productCategory.enterpriseConfiguration.id)
        return [productCategory:productCategory,enterpriseConfiguration:enterpriseConfiguration]
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