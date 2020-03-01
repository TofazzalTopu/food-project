package com.bits.bdfp.customer.customerpaymenttype

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.customer.CustomerContactType
import com.bits.bdfp.customer.CustomerPaymentType
import com.bits.bdfp.customer.CustomerPaymentTypeService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteCustomerPaymentTypeAction")
class DeleteCustomerPaymentTypeAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  CustomerPaymentTypeService customerPaymentTypeService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

   public Object preCondition(Object params, Object object) {
       Boolean isError = false
  try{
    CustomerPaymentType customerPaymentType = customerPaymentTypeService.read(Long.parseLong(params.id.toString()))

      String domain = 'customer_payment_type'
      String id =  customerPaymentType.id

      isError = validationCheckService.validationCheck(domain,id)

      if (!customerPaymentType.validate()) {
          message = this.getValidationErrorMessage(customerPaymentType)
          return message
      }
      else if (isError){
          message = this.getMessage('Customer Payment Type', Message.ERROR, 'This Customer Payment Type has already been used')
          return message
      }
      else{
          return customerPaymentType
      }
  }catch(Exception ex)
  {
      log.error(ex.message)
      throw ex;
  }
  }
   public Object postCondition(Object params, Object object) {
    //not implement
    return null
  }

   public Object execute(Object params, Object object) {

       try {
           Object result = this.preCondition( params,null)
           if (result instanceof CustomerPaymentType) {
               int noOfRows = (int) customerPaymentTypeService.delete(result)
               if (noOfRows > 0) {
                   message = this.getMessage("Customer Payment Type", Message.SUCCESS, this.SUCCESS_DELETE)
               } else {
                   message = this.getMessage("Customer Payment Type", Message.ERROR, this.FAIL_DELETE)
               }
           }

           return message;
       } catch (Exception ex) {
           log.error(ex.message)
           message= this.getMessage("CustomerPaymentType", Message.ERROR, "Exception-${ex.message}")
           return message;
       }


  }

}