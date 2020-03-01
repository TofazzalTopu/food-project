package com.bits.bdfp.customer.customerpaymenttype

import com.bits.bdfp.customer.CustomerPaymentType
import com.bits.bdfp.customer.CustomerPaymentTypeService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createCustomerPaymentTypeAction")
class CreateCustomerPaymentTypeAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerPaymentTypeService customerPaymentTypeService

    public Object preCondition(Object user, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) user
            CustomerPaymentType customerPaymentType = (CustomerPaymentType) object
            customerPaymentType.userCreated = applicationUser
            customerPaymentType.dateCreated = new Date()
            if (!customerPaymentType.validate()) {
                return null
            }
            return customerPaymentType
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object execute(Object params, Object object) {
        try {
            return customerPaymentTypeService.create(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}