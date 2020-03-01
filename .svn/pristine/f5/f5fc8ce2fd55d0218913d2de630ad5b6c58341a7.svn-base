package com.bits.bdfp.customer.customertype

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.customer.CustomerPriority
import com.bits.bdfp.customer.CustomerType
import com.bits.bdfp.customer.CustomerTypeService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateCustomerTypeAction")
class UpdateCustomerTypeAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerTypeService customerTypeService
    @Autowired
    ValidationCheckService validationCheckService
    Message message
    public Object preCondition(Object object, Object params) {
        Boolean isError = false
        try{

        CustomerType customerType = CustomerType.read(Long.parseLong(params?.id?.toString()))
        ApplicationUser applicationUser = (ApplicationUser) object
        customerType.properties = params
        customerType.userUpdated = applicationUser

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

    public Object execute(Object params, Object object) {

        try {
            ApplicationUser applicationUser = (ApplicationUser) object

            Object result = this.preCondition(applicationUser, params)
            if (result instanceof CustomerType) {
                int noOfRows = (int) customerTypeService.update(result)
                if (noOfRows > 0) {
                    message = this.getMessage('Customer Type', Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage('Customer Type', Message.ERROR, this.FAIL_UPDATE)
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
