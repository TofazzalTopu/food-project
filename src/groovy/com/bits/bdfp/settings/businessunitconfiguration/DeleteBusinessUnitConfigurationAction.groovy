package com.bits.bdfp.settings.businessunitconfiguration

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.settings.BusinessUnitConfiguration
import com.bits.bdfp.settings.BusinessUnitConfigurationService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteBusinessUnitConfigurationAction")
class DeleteBusinessUnitConfigurationAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  BusinessUnitConfigurationService businessUnitConfigurationService
    @Autowired
    ValidationCheckService validationCheckService
    Message message
   public Object preCondition(Object params, Object object) {
       Boolean isError = false
       try{

           BusinessUnitConfiguration businessUnitConfiguration = businessUnitConfigurationService.read(Long.parseLong(params?.id?.toString()))

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
           Object result = this.preCondition(params, object)
           if (result instanceof BusinessUnitConfiguration) {
               int noOfRows = (int) businessUnitConfigurationService.delete(result)
               if (noOfRows > 0) {
                   message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_DELETE)
               } else {
                   message = this.getMessage(result, Message.ERROR, this.FAIL_DELETE)
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