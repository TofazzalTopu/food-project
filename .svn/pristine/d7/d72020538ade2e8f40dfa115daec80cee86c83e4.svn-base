package com.bits.bdfp.inventory.product.productpackage

import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.product.FinishProductService
import com.bits.bdfp.inventory.product.PackageType
import com.bits.bdfp.inventory.product.PackageTypeService
import com.bits.bdfp.inventory.product.ProductPackage
import com.bits.bdfp.inventory.product.ProductPackageService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.bits.bdfp.settings.MeasureUnitConfiguration
import com.bits.bdfp.settings.MeasureUnitConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readProductPackageAction")
class ReadProductPackageAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  ProductPackageService productPackageService
    @Autowired
  EnterpriseConfigurationService enterpriseConfigurationService
    @Autowired
  FinishProductService finishProductService
    @Autowired
  PackageTypeService packageTypeService
    @Autowired
  MeasureUnitConfigurationService measureUnitConfigurationService

  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
       ProductPackage productPackage = productPackageService.read(Long.parseLong(params.id))
       FinishProduct finishProduct = finishProductService.read(productPackage.finishProduct.id)
       PackageType packageType = packageTypeService.read(productPackage.packageType.id)
       MeasureUnitConfiguration measureUnitConfiguration = measureUnitConfigurationService.read(productPackage.measureUnitConfiguration.id)
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(productPackage.enterpriseConfiguration.id)
        return [productPackage:productPackage,finishProduct:finishProduct,packageType:packageType,
                enterpriseConfiguration:enterpriseConfiguration,measureUnitConfiguration:measureUnitConfiguration]
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