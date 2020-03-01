package com.bits.bdfp.inventory.setup.vatrate


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import com.docu.commons.CommonConstants
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.inventory.setup.VatRate
import com.bits.bdfp.inventory.setup.VatRateService


import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService


@Component("updateVatRateAction")
class UpdateVatRateAction extends Action {
    public static final Log log = LogFactory.getLog(UpdateVatRateAction.class)
    private final String MESSAGE_HEADER = 'Update Vat Rate'
    private final String MESSAGE_SUCCESS = 'Vat Rate Updated Successfully'

    @Autowired
    VatRateService vatRateService
    
    @Autowired
    SpringSecurityService springSecurityService
    

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            VatRate vatRate = vatRateService.read(Long.parseLong(params.id))
            vatRate.properties = params


            if(Long.parseLong(params.version) > vatRate.version) {
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, CommonConstants.ALREADY_UPDATED)
            }


            vatRate.userUpdated = (ApplicationUser) springSecurityService?.getCurrentUser()
            
            if (!vatRate.validate()) {
                this.getValidationErrorMessage(vatRate)
            }
            vatRateService.update(vatRate)
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
