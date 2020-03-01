package com.bits.bdfp.inventory.setup.vattype

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.setup.VatType
import com.bits.bdfp.inventory.setup.VatTypeService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteVatTypeAction")
class DeleteVatTypeAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  VatTypeService vatTypeService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

   public Object preCondition(Object params, Object object) {
       Boolean isError = false
       try{

           VatType vatType = vatTypeService.read(Long.parseLong(params.id.toString()))
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

           Object result = this.preCondition(params,null)
           if (result instanceof VatType) {
               int noOfRows = (int) vatTypeService.delete(result)
               if (noOfRows > 0) {
                   message = this.getMessage("Vat Type", Message.SUCCESS, this.SUCCESS_DELETE)
               } else {
                   message = this.getMessage("Vat Type", Message.ERROR, this.FAIL_DELETE)
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