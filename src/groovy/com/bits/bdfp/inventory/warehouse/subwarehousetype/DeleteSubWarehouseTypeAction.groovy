package com.bits.bdfp.inventory.warehouse.subwarehousetype

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.warehouse.SubWarehouseType
import com.bits.bdfp.inventory.warehouse.SubWarehouseTypeService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteSubWarehouseTypeAction")
class DeleteSubWarehouseTypeAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  SubWarehouseTypeService subWarehouseTypeService
    @Autowired
    ValidationCheckService validationCheckService
    Message message
   public Object preCondition(Object params, Object object) {
       Boolean isError = false
       try{

           SubWarehouseType subWarehouseType = subWarehouseTypeService.read(Long.parseLong(params.id.toString()))
           String domain = 'sub_warehouse_type'
           String id =  subWarehouseType.id

           isError = validationCheckService.validationCheck(domain,id)

           if (!subWarehouseType.validate()) {
               message = this.getValidationErrorMessage(subWarehouseType)
               return message
           }
           else if (isError){
               message = this.getMessage('SubWarehouseType', Message.ERROR, 'This Sub warehouse type has already been used')
               return message
           }
           else{
               return subWarehouseType
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

   public Message execute(Object params, Object object) {

       try {
           Object result = this.preCondition( params,null)
           if (result instanceof SubWarehouseType) {
               int noOfRows = (int) subWarehouseTypeService.delete(result)
               if (noOfRows > 0) {
                   message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_DELETE)
               } else {
                   message = this.getMessage(result, Message.ERROR, this.FAIL_DELETE)
               }
           }

           return message;
       } catch (Exception ex) {
           log.error(ex.message)
           message= this.getMessage("SubWarehouseType", Message.ERROR, "Exception-${ex.message}")
           return message;
       }
  }

}