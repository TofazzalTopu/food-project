package com.bits.bdfp.settings.enterpriseconfiguration

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.accounts.ChartOfAccountService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.EnterpriseConfigurationService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateEnterpriseConfigurationAction")
class UpdateEnterpriseConfigurationAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    EnterpriseConfigurationService enterpriseConfigurationService
    @Autowired
    ValidationCheckService validationCheckService
    @Autowired
    ChartOfAccountService chartOfAccountService
    Message message

    public Object preCondition(Object object, Object params) {
        Boolean isError = false
        try {
            ApplicationUser applicationUser = (ApplicationUser) object

            EnterpriseConfiguration enterpriseConfiguration = enterpriseConfigurationService.read(Long.parseLong(params?.id?.toString()))
            int layer = enterpriseConfiguration.noOfLayers
            int code = enterpriseConfiguration.codeLength
            enterpriseConfiguration.properties = params
            enterpriseConfiguration.dateUpdated = new Date()
            enterpriseConfiguration.userUpdated = applicationUser

            String domain = 'enterprise_configuration'
            String id = enterpriseConfiguration.id

            isError = validationCheckService.validationCheck(domain, id)

            if (!enterpriseConfiguration.validate()) {
                message = this.getValidationErrorMessage(enterpriseConfiguration)
                return message
            } else if (isError) {
                message = this.getMessage('EnterpriseConfiguration', Message.ERROR, 'This Enterprise has already been used')
                return message
            } else {
                if (layer != enterpriseConfiguration.noOfLayers || code != enterpriseConfiguration.codeLength) {
                    chartOfAccountService.deleteLayersOfEnterprise(enterpriseConfiguration.id)
                }
                return enterpriseConfiguration
            }
        } catch (Exception ex) {
            log.error(ex.message)
            throw ex
        }
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Message execute(Object params, Object object) {

        try {

            ApplicationUser applicationUser = (ApplicationUser) object

            Object result = this.preCondition(applicationUser, params)
            if (result instanceof EnterpriseConfiguration) {
                int noOfRows = (int) enterpriseConfigurationService.update(result)
                if (noOfRows > 0) {
                    message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage(result, Message.ERROR, this.FAIL_UPDATE)
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("EnterpriseConfiguration", Message.ERROR, "Exception-${ex.message}")
            return message;
        }
    }
}
