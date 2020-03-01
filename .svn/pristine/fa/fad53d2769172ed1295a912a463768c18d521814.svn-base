package com.bits.bdfp.inventory.warehouse.subwarehouse

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.warehouse.SubWarehouse
import com.bits.bdfp.inventory.warehouse.SubWarehouseService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteSubWarehouseAction")
class DeleteSubWarehouseAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  SubWarehouseService subWarehouseService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

   public Object preCondition(Object params, Object object) {
//       Boolean isError = false
       try{

           SubWarehouse subWarehouse = subWarehouseService.read(Long.parseLong(params.id.toString()))

           String domain = 'sub_warehouse'
           String id =  subWarehouse.id

//           isError = validationCheckService.validationCheck(domain,id)

           if (!subWarehouse.validate()) {
               message = this.getValidationErrorMessage(subWarehouse)
               return message
           }
//           else if (isError){
//               message = this.getMessage('SubWarehouse', Message.ERROR, 'This Sub warehouse has already been used')
//               return message
//           }
           else{
               return subWarehouse
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
           if (result instanceof SubWarehouse) {
               int noOfRows = (int) subWarehouseService.delete(result)
               if (noOfRows > 0) {
                   message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_DELETE)
               } else {
                   message = this.getMessage(result, Message.ERROR, this.FAIL_DELETE)
               }
           }

           return message;
       } catch (Exception ex) {
           log.error(ex.message)
           //message= this.getMessage("SubWarehouse", Message.ERROR, "Exception-${ex.message}")
           message = this.getMessage('SubWarehouse', Message.ERROR, 'This Sub warehouse has already been used')
           return message;
       }
  }

}