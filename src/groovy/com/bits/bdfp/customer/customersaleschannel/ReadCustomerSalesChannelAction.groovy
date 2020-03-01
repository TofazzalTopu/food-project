package com.bits.bdfp.customer.customersaleschannel

import com.bits.bdfp.customer.CustomerSalesChannel
import com.bits.bdfp.customer.CustomerSalesChannelService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readCustomerSalesChannelAction")
class ReadCustomerSalesChannelAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  CustomerSalesChannelService customerSalesChannelService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
        CustomerSalesChannel customerSalesChannel= customerSalesChannelService.read(Long.parseLong(params.id))
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(customerSalesChannel.enterpriseConfiguration.id)
        return [customerSalesChannel:customerSalesChannel,enterpriseConfiguration:enterpriseConfiguration]
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