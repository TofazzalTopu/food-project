package com.bits.bdfp.common.bankpaymentmethod

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.common.BankPaymentMethod
import com.bits.bdfp.common.BankPaymentMethodService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteBankPaymentMethodAction")
class DeleteBankPaymentMethodAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  BankPaymentMethodService bankPaymentMethodService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

   public Object preCondition(Object params, Object object) {
       Boolean isError = false
       try {

           BankPaymentMethod bankPaymentMethod = bankPaymentMethodService.read(Long.parseLong(params.id.toString()))
      String domain = 'bank_payment_method'
      String id =  bankPaymentMethod.id


      isError = validationCheckService.validationCheck(domain,id)

      if (!bankPaymentMethod.validate()) {
          message = this.getValidationErrorMessage(bankPaymentMethod)
          return message
      }
      else if (isError){
          message = this.getMessage('BankPaymentMethod', Message.ERROR, 'This Bank Payment Method has already been used')
          return message
      }
      else{
          return bankPaymentMethod
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

           Object result = this.preCondition(params,null)
           if (result instanceof BankPaymentMethod) {
               int noOfRows = (int) bankPaymentMethodService.delete(result)
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