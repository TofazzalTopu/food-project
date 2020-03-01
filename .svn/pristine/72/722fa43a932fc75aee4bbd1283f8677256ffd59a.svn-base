package com.bits.bdfp.finance.cashreceivedfrombranch


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import com.docu.commons.CommonConstants
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.finance.CashReceivedFromBranch
import com.bits.bdfp.finance.CashReceivedFromBranchService


import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService


@Component("updateCashReceivedFromBranchAction")
class UpdateCashReceivedFromBranchAction extends Action {
    public static final Log log = LogFactory.getLog(UpdateCashReceivedFromBranchAction.class)
    private final String MESSAGE_HEADER = 'Update Cash Received From Branch'
    private final String MESSAGE_SUCCESS = 'Cash Received From Branch Updated Successfully'

    @Autowired
    CashReceivedFromBranchService cashReceivedFromBranchService
    
    @Autowired
    SpringSecurityService springSecurityService
    

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            CashReceivedFromBranch cashReceivedFromBranch = cashReceivedFromBranchService.read(Long.parseLong(params.id))
            cashReceivedFromBranch.properties = params
            
            
            if(Long.parseLong(params.version) > cashReceivedFromBranch.version) {
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, CommonConstants.ALREADY_UPDATED)
            }
            
            
            cashReceivedFromBranch.userUpdated = (ApplicationUser) springSecurityService?.getCurrentUser()
            
            if (!cashReceivedFromBranch.validate()) {
                this.getValidationErrorMessage(cashReceivedFromBranch)
            }
            cashReceivedFromBranchService.update(cashReceivedFromBranch)
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
