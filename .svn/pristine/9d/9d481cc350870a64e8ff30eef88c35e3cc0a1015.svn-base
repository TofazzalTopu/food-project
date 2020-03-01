package com.bits.bdfp.customer.customercontacttype

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.customer.CustomerCategory
import com.bits.bdfp.customer.CustomerContactType
import com.bits.bdfp.customer.CustomerContactTypeService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteCustomerContactTypeAction")
class DeleteCustomerContactTypeAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  CustomerContactTypeService customerContactTypeService
    @Autowired
    ValidationCheckService validationCheckService
    Message message
   public Object preCondition(Object params, Object object) {
       Boolean isError = false
  try{
    CustomerContactType customerContactType = customerContactTypeService.read(Long.parseLong(params.id.toString()))

      String domain = 'customer_contact_type'
      String id =  customerContactType.id

      isError = validationCheckService.validationCheck(domain,id)

      if (!customerContactType.validate()) {
          message = this.getValidationErrorMessage(customerContactType)
          return message
      }
      else if (isError){
          message = this.getMessage('Customer Contact Type', Message.ERROR, 'This Customer Contact Type has already been used')
          return message
      }
      else{
          return customerContactType
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

   public Message execute(Object params, Object object) {

       try {
           Object result = this.preCondition( params,null)
           if (result instanceof CustomerContactType) {
               int noOfRows = (int) customerContactTypeService.delete(result)
               if (noOfRows > 0) {
                   message = this.getMessage("Customer Contact Type", Message.SUCCESS, this.SUCCESS_DELETE)
               } else {
                   message = this.getMessage("Customer Contact Type", Message.ERROR, this.FAIL_DELETE)
               }
           }

           return message;
       } catch (Exception ex) {
           log.error(ex.message)
           message= this.getMessage("Customer Contact Type", Message.ERROR, "Exception-${ex.message}")
           return message;
       }

  }

}