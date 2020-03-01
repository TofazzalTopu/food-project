package com.bits.bdfp.common.bankbranch

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.common.BankBranch
import com.bits.bdfp.common.BankBranchService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteBankBranchAction")
class DeleteBankBranchAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  BankBranchService bankBranchService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

   public Object preCondition(Object params, Object object) {
       Boolean isError = false
       try {
           BankBranch bankBranch = bankBranchService.read(Long.parseLong(params.id.toString()))
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

           Object result = this.preCondition(params,null)
           if (result instanceof BankBranch) {
               int noOfRows = (int) bankBranchService.delete(result)
               if (noOfRows > 0) {
                   message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_DELETE)
               } else {
                   message = this.getMessage(result, Message.ERROR, this.FAIL_DELETE)
               }
           }

           return message;

       } catch (Exception ex) {
           log.error(ex.message)
           return null
       }
  }

}