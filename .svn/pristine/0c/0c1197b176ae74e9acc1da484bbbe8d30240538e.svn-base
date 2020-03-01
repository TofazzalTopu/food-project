package com.bits.bdfp.customer.customermaster

import com.bits.bdfp.customer.CustomerMasterService
import com.bits.bdfp.settings.BusinessUnitConfigurationService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by prianka.adhikary on 9/10/2015.
 */
@Component("listCustomerByEnterpriseAction")
class ListCustomerByEnterpriseAction extends Action{
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerMasterService customerMasterService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return customerMasterService.listCustomerByEnterprise(params)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }


}
