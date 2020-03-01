package com.bits.bdfp.common.bankbranch

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.common.BankBranch
import com.bits.bdfp.common.BankBranchService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateBankBranchAction")
class UpdateBankBranchAction extends Action {
 private static final Log log = LogFactory.getLog(this)
  @Autowired
  BankBranchService bankBranchService
 @Autowired
 ValidationCheckService validationCheckService
 Message message

   public Object preCondition(Object object, Object params) {
    Boolean isError = false
    try {
     ApplicationUser applicationUser = (ApplicationUser) object

    BankBranch bankBranch = BankBranch.read(Long.parseLong(params?.id?.toString()))
    bankBranch.properties=params
    bankBranch.userUpdated = applicationUser
    bankBranch.lastUpdated = new Date()
     String domain = 'bank_branch'
     String id =  bankBranch.id


     isError = validationCheckService.validationCheck(domain,id)

     if (!bankBranch.validate()) {
      message = this.getValidationErrorMessage(bankBranch)
      return message
     }
     else if (isError){
      message = this.getMessage('BankBranch', Message.ERROR, 'This Bank Branch has already been used')
      return message
     }
     else{
      return bankBranch
     }
    } catch (Exception ex) {
     log.error(ex.message)
     throw ex
    }
  }

   public Object postCondition(Object params, Object object) {
    //not implement
    return null
  }

   public Object execute(Object params, Object object) {

    try {
     ApplicationUser applicationUser = (ApplicationUser) object

     Object result = this.preCondition(applicationUser, params)
     if (result instanceof BankBranch) {
      int noOfRows = (int) bankBranchService.update(result)
      if (noOfRows > 0) {
       message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_UPDATE)
      } else {
       message = this.getMessage(result, Message.ERROR, this.FAIL_UPDATE)
      }
     }

     return message;

    } catch (Exception ex) {
     log.error(ex.message)
     return null
    }

      }
}
