package com.bits.bdfp.inventory.demandorder.adjustusingho


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import com.docu.commons.CommonConstants
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.inventory.demandorder.AdjustUsingHo
import com.bits.bdfp.inventory.demandorder.AdjustUsingHoService


import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService


@Component("updateAdjustUsingHoAction")
class UpdateAdjustUsingHoAction extends Action {
    public static final Log log = LogFactory.getLog(UpdateAdjustUsingHoAction.class)
    private final String MESSAGE_HEADER = 'Update Adjust Using Ho'
    private final String MESSAGE_SUCCESS = 'Adjust Using Ho Updated Successfully'

    @Autowired
    AdjustUsingHoService adjustUsingHoService
    
    @Autowired
    SpringSecurityService springSecurityService
    

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            AdjustUsingHo adjustUsingHo = adjustUsingHoService.read(Long.parseLong(params.id))
            adjustUsingHo.properties = params
            
            
            if(Long.parseLong(params.version) > adjustUsingHo.version) {
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, CommonConstants.ALREADY_UPDATED)
            }
            
            
            adjustUsingHo.userUpdated = (ApplicationUser) springSecurityService?.getCurrentUser()
            
            if (!adjustUsingHo.validate()) {
                this.getValidationErrorMessage(adjustUsingHo)
            }
            adjustUsingHoService.update(adjustUsingHo)
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
