package com.bits.bdfp.customer.customercontacttype

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.customer.CustomerCategory
import com.bits.bdfp.customer.CustomerContactType
import com.bits.bdfp.customer.CustomerContactTypeService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateCustomerContactTypeAction")
class UpdateCustomerContactTypeAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerContactTypeService customerContactTypeService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

    public Object preCondition(Object object, Object params) {
        Boolean isError = false
        try{

            ApplicationUser applicationUser = (ApplicationUser) object
            CustomerContactType customerContactType = CustomerContactType.read(Long.parseLong(params?.id?.toString()))
            customerContactType.properties = params
            customerContactType.userUpdated = applicationUser
            customerContactType.lastUpdated = new Date()

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
            if (result instanceof CustomerContactType) {
                int noOfRows = (int) customerContactTypeService.update(result)
                if (noOfRows > 0) {
                    message = this.getMessage("Customer Contact Type", Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage("Customer Contact Type", Message.ERROR, this.FAIL_UPDATE)
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
