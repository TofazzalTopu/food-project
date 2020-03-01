package com.bits.bdfp.customer.customerpriority

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.customer.CustomerPriority
import com.bits.bdfp.customer.CustomerPriorityService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateCustomerPriorityAction")
class UpdateCustomerPriorityAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerPriorityService customerPriorityService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

    public Object preCondition(Object object, Object params) {
        Boolean isError = false
        try{

        ApplicationUser applicationUser = (ApplicationUser) object
        CustomerPriority customerPriority = CustomerPriority.read(Long.parseLong(params?.id?.toString()))
        customerPriority.properties = params
        customerPriority.userUpdated = applicationUser

            String domain = 'customer_priority'
            String id =  customerPriority.id

            isError = validationCheckService.validationCheck(domain,id)


            if (!customerPriority.validate()) {
                message = this.getValidationErrorMessage(customerPriority)
                return message
            }
            else if (isError){
                message = this.getMessage('Customer Priority', Message.ERROR, 'This Customer Priority has already been used')
                return message
            }
            else{
                return customerPriority
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
            ApplicationUser applicationUser = (ApplicationUser) object

            Object result = this.preCondition(applicationUser, params)
            if (result instanceof CustomerPriority) {
                int noOfRows = (int) customerPriorityService.update(result)
                if (noOfRows > 0) {
                    message = this.getMessage("Customer Priority", Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage("Customer Priority", Message.ERROR, this.FAIL_UPDATE)
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            message= this.getMessage("Customer Priority", Message.ERROR, "Exception-${ex.message}")
            return message;
        }

    }
}
