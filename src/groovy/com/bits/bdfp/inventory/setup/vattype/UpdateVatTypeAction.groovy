package com.bits.bdfp.inventory.setup.vattype

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.setup.VatType
import com.bits.bdfp.inventory.setup.VatTypeService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateVatTypeAction")
class UpdateVatTypeAction extends Action {
 private static final Log log = LogFactory.getLog(this)
  @Autowired
  VatTypeService vatTypeService
 @Autowired
 ValidationCheckService validationCheckService
 Message message
   public Object preCondition(Object object, Object params) {
    Boolean isError = false
    try{

    ApplicationUser applicationUser = (ApplicationUser) object
    VatType vatType = VatType.read(Long.parseLong(params?.id?.toString()))
    vatType.properties=params
    vatType.userUpdated = applicationUser
    vatType.dateUpdated = new Date()

     String domain = 'vat_type'
     String id =  vatType.id

     isError = validationCheckService.validationCheck(domain,id)

     if (!vatType.validate()) {
      message = this.getValidationErrorMessage(vatType)
      return message
     }
     else if (isError){
      message = this.getMessage('Vat Type', Message.ERROR, 'This Vat Type  has already been used')
      return message
     }
     else{
      return vatType
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
     ApplicationUser applicationUser=(ApplicationUser)object
     Object result = this.preCondition(applicationUser,params)
     if (result instanceof VatType) {
      int noOfRows = (int) vatTypeService.update(result)
      if (noOfRows > 0) {
       message = this.getMessage("Vat Type", Message.SUCCESS, this.SUCCESS_UPDATE)
      } else {
       message = this.getMessage("Vat Type", Message.ERROR, this.FAIL_UPDATE)
      }
     }

     return message;
    } catch (Exception ex) {
     log.error(ex.message)
     message= this.getMessage("Vat Type", Message.ERROR, "Exception-${ex.message}")
     return message;
    }
  }
}
