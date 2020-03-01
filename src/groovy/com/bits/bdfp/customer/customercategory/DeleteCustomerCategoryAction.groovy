package com.bits.bdfp.customer.customercategory

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.customer.CustomerCategory
import com.bits.bdfp.customer.CustomerCategoryService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteCustomerCategoryAction")
class DeleteCustomerCategoryAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  CustomerCategoryService customerCategoryService
    @Autowired
    ValidationCheckService validationCheckService
    Message message
   public Object preCondition(Object params, Object object) {
       Boolean isError = false
  try{

    CustomerCategory customerCategory = customerCategoryService.read(Long.parseLong(params.id.toString()))
      String domain = 'customer_category'
      String id =  customerCategory.id

      isError = validationCheckService.validationCheck(domain,id)

      if (!customerCategory.validate()) {
          message = this.getValidationErrorMessage(customerCategory)
          return message
      }
      else if (isError){
          message = this.getMessage('Customer Category', Message.ERROR, 'This Customer Category has already been used')
          return message
      }
      else{
          return customerCategory
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
           Object result = this.preCondition( params,null)
           if (result instanceof CustomerCategory) {
               int noOfRows = (int) customerCategoryService.delete(result)
               if (noOfRows > 0) {
                   message = this.getMessage("Customer Category", Message.SUCCESS, this.SUCCESS_DELETE)
               } else {
                   message = this.getMessage("Customer Category", Message.ERROR, this.FAIL_DELETE)
               }
           }

           return message;
       } catch (Exception ex) {
           log.error(ex.message)
           message= this.getMessage("Customer Category", Message.ERROR, "Exception-${ex.message}")
           return message;
       }


  }

}