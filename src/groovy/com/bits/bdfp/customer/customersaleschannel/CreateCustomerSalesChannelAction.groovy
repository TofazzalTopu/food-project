package com.bits.bdfp.customer.customersaleschannel

import com.bits.bdfp.customer.CustomerSalesChannel
import com.bits.bdfp.customer.CustomerSalesChannelService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createCustomerSalesChannelAction")
class CreateCustomerSalesChannelAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerSalesChannelService customerSalesChannelService

    public Object preCondition(Object user, Object object) {
        try {
            CustomerSalesChannel customerSalesChannel = (CustomerSalesChannel) object
            ApplicationUser applicationUser = (ApplicationUser) user
            customerSalesChannel.userCreated = applicationUser
            customerSalesChannel.dateCreated=new Date()
            if (!customerSalesChannel.validate()) {
                return null
            }
            return customerSalesChannel
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object execute(Object params, Object object) {
        try {
            return customerSalesChannelService.create(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}