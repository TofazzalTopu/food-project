package com.bits.bdfp.customer.customercategory

import com.bits.bdfp.customer.CustomerCategory
import com.bits.bdfp.customer.CustomerCategoryService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readCustomerCategoryAction")
class ReadCustomerCategoryAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  CustomerCategoryService customerCategoryService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
       CustomerCategory customerCategory= customerCategoryService.read(Long.parseLong(params.id))
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(customerCategory.enterpriseConfiguration.id)
       return [customerCategory:customerCategory,enterpriseConfiguration:enterpriseConfiguration]
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