package com.bits.bdfp.inventory.demandorder.writeoff


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import com.docu.commons.CommonConstants
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.inventory.demandorder.WriteOff
import com.bits.bdfp.inventory.demandorder.WriteOffService


import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService


@Component("updateWriteOffAction")
class UpdateWriteOffAction extends Action {
    public static final Log log = LogFactory.getLog(UpdateWriteOffAction.class)
    private final String MESSAGE_HEADER = 'Update Write Off'
    private final String MESSAGE_SUCCESS = 'Write Off Updated Successfully'

    @Autowired
    WriteOffService writeOffService
    
    @Autowired
    SpringSecurityService springSecurityService
    

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            WriteOff writeOff = writeOffService.read(Long.parseLong(params.id))
            writeOff.properties = params
            
            
            if(Long.parseLong(params.version) > writeOff.version) {
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, CommonConstants.ALREADY_UPDATED)
            }
            
            
            writeOff.userUpdated = (ApplicationUser) springSecurityService?.getCurrentUser()
            
            if (!writeOff.validate()) {
                this.getValidationErrorMessage(writeOff)
            }
            writeOffService.update(writeOff)
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
