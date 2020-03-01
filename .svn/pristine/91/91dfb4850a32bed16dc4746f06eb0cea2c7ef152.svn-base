package com.bits.bdfp.inventory.product.finishproduct

import com.bits.bdfp.inventory.product.FinishProductService
import com.bits.bdfp.inventory.product.ProductCategoryService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("enterpriseProductCategoryAction")
class EnterpriseProductCategoryAction extends Action {
  private static final Log log = LogFactory.getLog(this)
  @Autowired
  FinishProductService finishProductService

   public Object preCondition(Object params, Object object) {
    //not need to implement
    return null
  }

   public Object execute(Object params, Object object) {
    try{

    Map objList = finishProductService.productTypeCategoryForEnterprise(params);
    return objList
    }
    catch (Exception ex) {
    log.error(ex.message)
      return  null
    }
  }
   public Object postCondition(Object params, Object object) {
    return null
  }
}
