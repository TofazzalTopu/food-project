package com.bits.bdfp.inventory.setup.vatrate


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.inventory.setup.VatRate
import com.bits.bdfp.inventory.setup.VatRateService


import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService


@Component("createVatRateAction")
class CreateVatRateAction extends Action{
    public static final Log log = LogFactory.getLog(CreateVatRateAction.class)
    private final String MESSAGE_HEADER = 'New Vat Rate'
    private final String MESSAGE_SUCCESS = 'Vat Rate Created Successfully'

    @Autowired
    VatRateService vatRateService
    
    @Autowired
    SpringSecurityService springSecurityService
    

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            VatRate vatRate = new VatRate()
            vatRate.properties = params
            
            vatRate.userCreated = (ApplicationUser) springSecurityService?.getCurrentUser()
            
            if (!vatRate.validate()) {
                this.getValidationErrorMessage(vatRate)
            }
            vatRateService.create(vatRate)
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