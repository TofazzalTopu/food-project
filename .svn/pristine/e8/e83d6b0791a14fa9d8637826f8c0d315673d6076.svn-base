package com.bits.bdfp.common.bankbranch

import com.bits.bdfp.common.Bank
import com.bits.bdfp.common.BankBranch
import com.bits.bdfp.common.BankBranchService
import com.bits.bdfp.common.BankService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readBankBranchAction")
class ReadBankBranchAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  BankBranchService bankBranchService
    @Autowired
   EnterpriseConfigurationService enterpriseConfigurationService
    @Autowired
    BankService bankService
  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {

       BankBranch bankBranch= bankBranchService.read(Long.parseLong(params.id))
        Bank bank=bankService.read(bankBranch.bank.id)
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(bank.enterpriseConfiguration.id)
        return [bankBranch:bankBranch,bank:bank,enterpriseConfiguration:enterpriseConfiguration]

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