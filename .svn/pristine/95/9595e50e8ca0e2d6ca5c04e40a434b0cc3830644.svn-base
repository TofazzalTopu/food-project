package com.bits.bdfp.common.bankaccount

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.common.Bank
import com.bits.bdfp.common.BankAccount
import com.bits.bdfp.common.BankAccountService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteBankAccountAction")
class DeleteBankAccountAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  BankAccountService bankAccountService
    Message message
    @Autowired
    ValidationCheckService validationCheckService
   public Object preCondition(Object params, Object object) {
       Boolean isError = false
  try{
    BankAccount bankAccount =  bankAccountService.read(Long.parseLong(params.id.toString()))


      String domain = 'bank_account'
      String id =  bankAccount.id

      isError = validationCheckService.validationCheck(domain,id)

      if (!bankAccount.validate()) {
          message = this.getValidationErrorMessage(bankAccount)
          return message
      }
      else if (isError){
          message = this.getMessage('Bank Account', Message.ERROR, 'This bank account has already been used')
          return message
      }
      else {
          return bankAccount;
      }
     }catch(Exception ex)
      {
          log.error(ex.message)
          throw ex
      }
  }
   public Object postCondition(Object params, Object object) {
    //not implement
    return null
  }

   public Message execute(Object params, Object object) {
       try {
           Object result = this.preCondition(params, object)
           if (result instanceof BankAccount) {
               int noOfRows = (int) bankAccountService.delete(result)
               if (noOfRows > 0) {
                   message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_DELETE)
               } else {
                   message = this.getMessage(result, Message.ERROR, this.FAIL_DELETE)
               }
           }
           return message;
       } catch (Exception ex) {
           log.error(ex.message)
           message= this.getMessage("BankAccount", Message.ERROR, "Exception-${ex.message}")
           return message;
       }

  }

}