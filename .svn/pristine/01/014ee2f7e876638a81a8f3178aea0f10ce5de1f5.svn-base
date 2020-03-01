package com.bits.bdfp.common.bankpaymentmethod

import com.bits.bdfp.common.BankPaymentMethod
import com.bits.bdfp.common.BankPaymentMethodService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readBankPaymentMethodAction")
class ReadBankPaymentMethodAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  BankPaymentMethodService bankPaymentMethodService
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
        BankPaymentMethod bankPaymentMethod= bankPaymentMethodService.read(Long.parseLong(params.id))
//        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(bankPaymentMethod.enterpriseConfiguration.id)
//        return [bankPaymentMethod:bankPaymentMethod,enterpriseConfiguration:enterpriseConfiguration]
        return [bankPaymentMethod:bankPaymentMethod]
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