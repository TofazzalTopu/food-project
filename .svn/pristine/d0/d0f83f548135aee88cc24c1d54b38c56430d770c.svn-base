package com.bits.bdfp.settings.measureunitconfiguration

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.settings.MeasureUnitConfiguration
import com.bits.bdfp.settings.MeasureUnitConfigurationService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteMeasureUnitConfigurationAction")
class DeleteMeasureUnitConfigurationAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  MeasureUnitConfigurationService measureUnitConfigurationService
    @Autowired
    ValidationCheckService validationCheckService
    Message message
   public Object preCondition(Object params, Object object) {
       Boolean isError = false
       try{

      MeasureUnitConfiguration measureUnitConfiguration = measureUnitConfigurationService.read(Long.parseLong(params.id.toString()))

           String domain = 'measure_unit_configuration'
           String id =  measureUnitConfiguration.id

           isError = validationCheckService.validationCheck(domain,id)

      if (!measureUnitConfiguration.validate()) {
          message = this.getValidationErrorMessage(measureUnitConfiguration)
          return message
      }
      else if (isError){
          message = this.getMessage('MeasureUnitConfiguration', Message.ERROR, 'This Measurement Unit has already been used')
          return message
      }
      else{
          return measureUnitConfiguration
      }


  }catch (Exception ex) {
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
           Object result = this.preCondition( params,null)
           if (result instanceof MeasureUnitConfiguration) {
               int noOfRows = (int) measureUnitConfigurationService.delete(result)
               if (noOfRows > 0) {
                   message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_DELETE)
               } else {
                   message = this.getMessage(result, Message.ERROR, this.FAIL_DELETE)
               }
           }

           return message;
       } catch (Exception ex) {
           log.error(ex.message)
           message= this.getMessage("MeasureUnitConfiguration", Message.ERROR, "Exception-${ex.message}")
           return message;
       }

  }

}