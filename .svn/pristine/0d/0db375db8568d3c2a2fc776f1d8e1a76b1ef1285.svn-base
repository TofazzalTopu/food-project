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

@Component("updateSubWarehouseAction")
class UpdateSubWarehouseAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    SubWarehouseService subWarehouseService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

    public Object preCondition(Object object, Object params) {

        try{

        SubWarehouse subWarehouse = SubWarehouse.read(Long.parseLong(params?.id?.toString()))
        ApplicationUser applicationUser = (ApplicationUser) object
        subWarehouse.properties = params
        subWarehouse.userUpdated = applicationUser
        subWarehouse.dateUpdated = new Date()

            String domain = 'sub_warehouse'
            String id =  subWarehouse.id

            if (!subWarehouse.validate()) {
                message = this.getValidationErrorMessage(subWarehouse)
                return message
            }
            else{
                return subWarehouse
            }

        }catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
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
            if (result instanceof SubWarehouse) {
                int noOfRows = (int) subWarehouseService.update(result)
                if (noOfRows > 0) {
                    message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage(result, Message.ERROR, this.FAIL_UPDATE)
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }

    }
}
