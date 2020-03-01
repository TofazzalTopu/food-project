package com.bits.bdfp.customer.customereligibilitymaster


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import com.docu.commons.CommonConstants
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.customer.CustomerEligibilityMaster
import com.bits.bdfp.customer.CustomerEligibilityMasterService



@Component("updateCustomerEligibilityMasterAction")
class UpdateCustomerEligibilityMasterAction extends Action {
    public static final Log log = LogFactory.getLog(UpdateCustomerEligibilityMasterAction.class)
    private final String MESSAGE_HEADER = 'Update Customer Eligibility Master'
    private final String MESSAGE_SUCCESS = 'Customer Eligibility Master Updated Successfully'

    @Autowired
    CustomerEligibilityMasterService customerEligibilityMasterService
    

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            CustomerEligibilityMaster customerEligibilityMaster = customerEligibilityMasterService.read(Long.parseLong(params.id))
            customerEligibilityMaster.properties = params
            
            
            if(Long.parseLong(params.version) > customerEligibilityMaster.version) {
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, CommonConstants.ALREADY_UPDATED)
            }
            
            
            if (!customerEligibilityMaster.validate()) {
                this.getValidationErrorMessage(customerEligibilityMaster)
            }
            customerEligibilityMasterService.update(customerEligibilityMaster)
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
