package com.bits.bdfp.settings.businessunitconfiguration

import com.bits.bdfp.settings.BusinessUnitConfiguration
import com.bits.bdfp.settings.BusinessUnitConfigurationService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createBusinessUnitConfigurationAction")
class CreateBusinessUnitConfigurationAction extends Action {
   private static final Log log = LogFactory.getLog(this)
  @Autowired
  BusinessUnitConfigurationService businessUnitConfigurationService
  Message message

  public Message preCondition(Object params, Object object) {
    try {
      BusinessUnitConfiguration businessUnitConfiguration = (BusinessUnitConfiguration) object
      if (!businessUnitConfiguration.validate()) {
        message = this.getValidationErrorMessage(businessUnitConfiguration)
      } else {
        message = this.getMessage(businessUnitConfiguration, Message.SUCCESS, this.SUCCESS_SAVE)
      }
      return message
    } catch (Exception ex) {
      log.error(ex.message)
      throw ex
    }
  }

  public Message execute(Object params, Object object) {
    try {
      ApplicationUser applicationUser = (ApplicationUser) object
      BusinessUnitConfiguration businessUnitConfiguration = new BusinessUnitConfiguration(params)
      businessUnitConfiguration.dateCreated = new Date()
      businessUnitConfiguration.userCreated = applicationUser

      message = this.preCondition(null, businessUnitConfiguration)
      if (message.type == 1) {
        businessUnitConfiguration = businessUnitConfigurationService.create(businessUnitConfiguration)
        if (businessUnitConfiguration) {
          message = this.getMessage(businessUnitConfiguration, Message.SUCCESS, this.SUCCESS_SAVE)
        } else {
          message = this.getMessage(businessUnitConfiguration, Message.ERROR, this.FAIL_SAVE)
        }
      }

      return message;
    } catch (Exception ex) {
      log.error(ex.message)
      message= this.getMessage("BusinessUnitConfiguration", Message.ERROR, "Exception-${ex.message}")
      return message;
    }
  }

  public Object postCondition(Object params, Object object) {
    return null
  }
}