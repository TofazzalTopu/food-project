package com.bits.bdfp.settings.businessunitconfiguration

import com.bits.bdfp.ValidationCheckService
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

@Component("updateBusinessUnitConfigurationAction")
class UpdateBusinessUnitConfigurationAction extends Action {
 private static final Log log = LogFactory.getLog(this)
  @Autowired
  BusinessUnitConfigurationService businessUnitConfigurationService
 @Autowired
 ValidationCheckService validationCheckService
  Message message

   public Object preCondition(Object object, Object params) {
    Boolean isError = false
    try{

    ApplicationUser applicationUser = (ApplicationUser) object
    BusinessUnitConfiguration businessUnitConfiguration = businessUnitConfigurationService.read(Long.parseLong(params?.id?.toString()))
    businessUnitConfiguration.properties = params
    businessUnitConfiguration.dateUpdated = new Date()
    businessUnitConfiguration.userUpdated = applicationUser

     String domain = 'business_unit_configuration'
     String id =  businessUnitConfiguration.id

     isError = validationCheckService.validationCheck(domain,id)


     if (!businessUnitConfiguration.validate()) {
      message = this.getValidationErrorMessage(businessUnitConfiguration)
      return message
     }
     else if (isError){
      message = this.getMessage('BusinessUnitConfiguration', Message.ERROR, 'This Business Unit has already been used')
      return message
     }
     else{
      return businessUnitConfiguration
     }
   }
      catch (Exception ex) {
       log.error(ex.message)
       throw ex
      }
  }

   public Object postCondition(Object params, Object object) {
    //not implement
    return null
  }

   public Object execute(Object params, Object object) {
    try {

     ApplicationUser applicationUser = (ApplicationUser) object

     Object result = this.preCondition(applicationUser, params)
     if (result instanceof BusinessUnitConfiguration) {
      int noOfRows = (int) businessUnitConfigurationService.update(result)
      if (noOfRows > 0) {
       message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_UPDATE)
      } else {
       message = this.getMessage(result, Message.ERROR, this.FAIL_UPDATE)
      }
     }

     return message;
    } catch (Exception ex) {
     log.error(ex.message)
     message= this.getMessage("BusinessUnitConfiguration", Message.ERROR, "Exception-${ex.message}")
     return message;
    }
  }
}
