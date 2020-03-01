package com.bits.bdfp.common.bankaccount

import com.bits.bdfp.common.Bank
import com.bits.bdfp.common.BankAccount
import com.bits.bdfp.common.BankAccountService
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

@Component("readBankAccountAction")
class ReadBankAccountAction extends Action {
     private static final Log log = LogFactory.getLog(this)
  @Autowired
  BankAccountService bankAccountService
    @Autowired
    BankService bankService
    @Autowired
    BankBranchService bankBranchService
    @Autowired
    EnterpriseConfigurationService  enterpriseConfigurationService

  public Object preCondition(Object params, Object object) {
      //Not implement
      return null
  }

   public Object execute(Object params, Object object) {
    try {
        BankAccount bankAccount= bankAccountService.read(Long.parseLong(params.id))
        Bank bank=bankService.read(bankAccount.bank.id)
        BankBranch bankBranch= bankBranchService.read(bankAccount.bankBranch.id)
        EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(bank.enterpriseConfiguration.id)
        return [bankAccount:bankAccount,bank:bank,bankBranch:bankBranch,enterpriseConfiguration:enterpriseConfiguration]
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