package com.bits.bdfp.common.cashpool

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.common.CashPool
import com.bits.bdfp.common.CashPoolService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteCashPoolAction")
class DeleteCashPoolAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  CashPoolService cashPoolService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

   public Object preCondition(Object params, Object object) {
       Boolean isError = false
       try {
           ApplicationUser applicationUser = (ApplicationUser) object

           CashPool cashPool = cashPoolService.read(Long.parseLong(params.id.toString()))
           String domain = 'cash_pool'
           String id =  cashPool.id

           isError = validationCheckService.validationCheck(domain,id)

           if (!cashPool.validate()) {
               message = this.getValidationErrorMessage(cashPool)
               return message
           }
           else if (isError){
               message = this.getMessage('CashPool', Message.ERROR, 'This cash pool has already been used')
               return message
           }
           else{
               return cashPool
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

           Object result = this.preCondition(params, null)
           if (result instanceof CashPool) {
               int noOfRows = (int) cashPoolService.delete(result)
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