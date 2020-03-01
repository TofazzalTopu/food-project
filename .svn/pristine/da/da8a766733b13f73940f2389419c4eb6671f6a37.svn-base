package com.bits.bdfp.common.bankaccount

import com.bits.bdfp.common.BankAccount
import com.bits.bdfp.common.BankAccountService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createBankAccountAction")
class CreateBankAccountAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  BankAccountService bankAccountService

  public Object preCondition(Object user, Object object) {
    try {
      ApplicationUser applicationUser = (ApplicationUser) user
      BankAccount bankAccount = (BankAccount) object
      bankAccount.userCreated = applicationUser
      bankAccount.dateCreated = new Date()
      if (!bankAccount.validate()) {
        return null
      }
      return bankAccount
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return bankAccountService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}