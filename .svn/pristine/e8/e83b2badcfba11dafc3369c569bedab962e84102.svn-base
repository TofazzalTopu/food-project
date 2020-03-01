package com.bits.bdfp.inventory.warehouse.warehouse

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.warehouse.Warehouse
import com.bits.bdfp.inventory.warehouse.WarehouseService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
 import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteWarehouseAction")
class DeleteWarehouseAction extends Action {

  private static final Log log = LogFactory.getLog(this)
  @Autowired
  WarehouseService warehouseService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

   public Object preCondition(Object params, Object object) {
//       Boolean isError = false
       try{

           Warehouse warehouse = warehouseService.read(Long.parseLong(params.id.toString()))
           String domain = 'warehouse'
           String id =  warehouse.id

//           isError = validationCheckService.validationCheck(domain,id)

           if (!warehouse.validate()) {
               message = this.getValidationErrorMessage(warehouse)
               return message
           }
//           else if (isError){
//               message = this.getMessage('Warehouse', Message.ERROR, 'This warehouse has already been used')
//               return message
//           }
           else{
               return warehouse
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
           ApplicationUser applicationUser = (ApplicationUser) object

           Object result = this.preCondition( params,null)
           if (result instanceof Warehouse) {
               int noOfRows = (int) warehouseService.delete(result)
               if (noOfRows > 0) {
                   message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_DELETE)
               } else {
                   message = this.getMessage(result, Message.ERROR, this.FAIL_DELETE)

               }
           }

           return message;
       } catch (Exception ex) {
           log.error(ex.message)
           //message= this.getMessage("Warehouse", Message.ERROR, "Exception-${ex.message}")
           message = this.getMessage('Warehouse', Message.ERROR, 'This warehouse has already been used')
           return message;
       }

  }

}