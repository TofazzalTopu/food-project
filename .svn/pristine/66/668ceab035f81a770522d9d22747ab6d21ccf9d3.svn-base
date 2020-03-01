package com.bits.bdfp.common.bankbranch

import com.bits.bdfp.common.BankBranch
import com.bits.bdfp.common.BankBranchService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createBankBranchAction")
class CreateBankBranchAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  BankBranchService bankBranchService

  public Object preCondition(Object user, Object object) {
    try {
      ApplicationUser applicationUser = (ApplicationUser) user
      BankBranch bankBranch = (BankBranch) object
      bankBranch.userCreated = applicationUser
      bankBranch.dateCreated = new Date()
      if (!bankBranch.validate()) {
        return null
      }
      return bankBranch
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object execute(Object params, Object object) {
    try {
      return bankBranchService.create(object)
    } catch (Exception ex) {
    log.error(ex.message)
      return null
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}