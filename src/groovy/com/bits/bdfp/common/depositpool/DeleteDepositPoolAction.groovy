package com.bits.bdfp.common.depositpool

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.common.DepositPool
import com.bits.bdfp.common.DepositPoolService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteDepositPoolAction")
class DeleteDepositPoolAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  DepositPoolService depositPoolService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

   public Object preCondition(Object params, Object object) {
       Boolean isError = false
       try {


           DepositPool depositPool = depositPoolService.read(Long.parseLong(params.id.toString()))
           String domain = 'deposit_pool'
           String id =  depositPool.id

           isError = validationCheckService.validationCheck(domain,id)

           if (!depositPool.validate()) {
               message = this.getValidationErrorMessage(depositPool)
               return message
           }
           else if (isError){
               message = this.getMessage('DepositPool', Message.ERROR, 'This deposit pool has already been used')
               return message
           }
           else{
               return depositPool
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
           if (result instanceof DepositPool) {
               int noOfRows = (int) depositPoolService.delete(result)
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