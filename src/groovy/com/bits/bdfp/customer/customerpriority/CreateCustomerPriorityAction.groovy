package com.bits.bdfp.customer.customerpriority

import com.bits.bdfp.customer.CustomerPriority
import com.bits.bdfp.customer.CustomerPriorityService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createCustomerPriorityAction")
class CreateCustomerPriorityAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerPriorityService customerPriorityService

    public Object preCondition(Object user, Object object) {
        try {
            CustomerPriority customerPriority = (CustomerPriority) object
            ApplicationUser applicationUser = (ApplicationUser) user
            customerPriority.userCreated = applicationUser
//            customerPriority.dateCreated = new Date()
            if (!customerPriority.validate()) {
                return null
            }
            return customerPriority
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object execute(Object params, Object object) {
        try {
            return customerPriorityService.create(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}