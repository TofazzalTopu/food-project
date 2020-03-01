package com.bits.bdfp.customer.customercontacttype

import com.bits.bdfp.customer.CustomerContactType
import com.bits.bdfp.customer.CustomerContactTypeService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createCustomerContactTypeAction")
class CreateCustomerContactTypeAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerContactTypeService customerContactTypeService

    public Object preCondition(Object user, Object object) {
        try {
            CustomerContactType customerContactType = (CustomerContactType) object
            ApplicationUser applicationUser = (ApplicationUser) user
            customerContactType.userCreated = applicationUser
            customerContactType.dateCreated = new Date()
            if (!customerContactType.validate()) {
                return null
            }
            return customerContactType
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object execute(Object params, Object object) {
        try {
            return customerContactTypeService.create(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}