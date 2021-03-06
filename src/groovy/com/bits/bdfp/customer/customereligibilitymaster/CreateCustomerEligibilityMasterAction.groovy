package com.bits.bdfp.customer.customereligibilitymaster


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.customer.CustomerEligibilityMaster
import com.bits.bdfp.customer.CustomerEligibilityMasterService



@Component("createCustomerEligibilityMasterAction")
class CreateCustomerEligibilityMasterAction extends Action{
    public static final Log log = LogFactory.getLog(CreateCustomerEligibilityMasterAction.class)
    private final String MESSAGE_HEADER = 'New Customer Eligibility Master'
    private final String MESSAGE_SUCCESS = 'Customer Eligibility Master Created Successfully'

    @Autowired
    CustomerEligibilityMasterService customerEligibilityMasterService
    

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            CustomerEligibilityMaster customerEligibilityMaster = new CustomerEligibilityMaster()
            customerEligibilityMaster.properties = params
            
            if (!customerEligibilityMaster.validate()) {
                this.getValidationErrorMessage(customerEligibilityMaster)
            }
            customerEligibilityMasterService.create(customerEligibilityMaster)
            return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, ex.message)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}