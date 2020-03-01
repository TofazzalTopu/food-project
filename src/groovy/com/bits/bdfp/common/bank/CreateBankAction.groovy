package com.bits.bdfp.common.bank

import com.bits.bdfp.common.Bank
import com.bits.bdfp.common.BankService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createBankAction")
class CreateBankAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  BankService bankService

  public Object preCondition(Object user, Object object) {
    try {
      ApplicationUser applicationUser = (ApplicationUser) user
      Bank bank = (Bank) object
      bank.userCreated = applicationUser
      bank.dateCreated = new Date()
      if (!bank.validate()) {
        return null
      }
      return bank
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return bankService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}