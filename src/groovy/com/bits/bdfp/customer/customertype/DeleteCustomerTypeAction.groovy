package com.bits.bdfp.customer.customertype

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.customer.CustomerType
import com.bits.bdfp.customer.CustomerTypeService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteCustomerTypeAction")
class DeleteCustomerTypeAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  CustomerTypeService customerTypeService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

   public Object preCondition(Object params, Object object) {
       Boolean isError = false
  try{

      CustomerType customerType = customerTypeService.read(Long.parseLong(params.id.toString()))
      String domain = 'customer_type'
      String id =  customerType.id

      isError = validationCheckService.validationCheck(domain,id)

      if (!customerType.validate()) {
          message = this.getValidationErrorMessage(customerType)
          return message
      }
      else if (isError){
          message = this.getMessage('Customer Type', Message.ERROR, 'This Customer Type has already been used')
          return message
      }
      else{
          return customerType
      }


  }catch (Exception ex) {
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
           Object result = this.preCondition(params, null)
           if (result instanceof CustomerType) {
               int noOfRows = (int) customerTypeService.delete(result)
               if (noOfRows > 0) {
                   message = this.getMessage('Customer Type', Message.SUCCESS, this.SUCCESS_DELETE)
               } else {
                   message = this.getMessage('Customer Type', Message.ERROR, this.FAIL_DELETE)
               }
           }

           return message;
       } catch (Exception ex) {
           log.error(ex.message)
           message= this.getMessage("Customer Type", Message.ERROR, "Exception-${ex.message}")
           return message;
       }

  }

}