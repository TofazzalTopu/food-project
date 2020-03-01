package com.bits.bdfp.customer.customerpaymenttype

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.customer.CustomerContactType
import com.bits.bdfp.customer.CustomerPaymentType
import com.bits.bdfp.customer.CustomerPaymentTypeService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateCustomerPaymentTypeAction")
class UpdateCustomerPaymentTypeAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerPaymentTypeService customerPaymentTypeService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

    public Object preCondition(Object object, Object params) {
        Boolean isError = false
        try {

            ApplicationUser applicationUser = (ApplicationUser) object
            CustomerPaymentType customerPaymentType = CustomerPaymentType.read(Long.parseLong(params?.id?.toString()))
            customerPaymentType.properties = params
            customerPaymentType.userUpdated = applicationUser

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

        }
        catch (Exception ex) {
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
            if (result instanceof CustomerPaymentType) {
                int noOfRows = (int) customerPaymentTypeService.update(result)
                if (noOfRows > 0) {
                    message = this.getMessage("Customer Payment Type", Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage("Customer Payment Type", Message.ERROR, this.FAIL_UPDATE)
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            message= this.getMessage("Customer Payment Type", Message.ERROR, "Exception-${ex.message}")
            return message;
        }


    }
}
