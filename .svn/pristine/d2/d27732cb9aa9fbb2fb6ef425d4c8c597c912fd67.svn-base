package com.bits.bdfp.settings.enterpriseconfiguration

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createEnterpriseConfigurationAction")
class CreateEnterpriseConfigurationAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
    Message message

    protected Message preCondition(Object object, Object params) {
        try {
            EnterpriseConfiguration enterpriseConfiguration = (EnterpriseConfiguration) object
            if (!enterpriseConfiguration.validate()) {
                message = this.getValidationErrorMessage(enterpriseConfiguration)
            } else {
                message = this.getMessage(enterpriseConfiguration, Message.SUCCESS, this.SUCCESS_SAVE)
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("EnterpriseConfiguration", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Message execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            EnterpriseConfiguration enterpriseConfiguration = new EnterpriseConfiguration(params)
            enterpriseConfiguration.layerUsed = false
            enterpriseConfiguration.coaUsed = 0
            enterpriseConfiguration.dateCreated = new Date()
            enterpriseConfiguration.userCreated = applicationUser

            message = this.preCondition(enterpriseConfiguration, null)
            if (message.type == 1) {
                enterpriseConfiguration = enterpriseConfigurationService.create(enterpriseConfiguration)
                if (enterpriseConfiguration) {
                    message = this.getMessage(enterpriseConfiguration, Message.SUCCESS, this.SUCCESS_SAVE)
                } else {
                    message = this.getMessage(enterpriseConfiguration, Message.ERROR, this.FAIL_SAVE)
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            throw ex
        }
    }

    protected Object postCondition(Object params, Object object) {
        return null
    }
}