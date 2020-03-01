package com.bits.bdfp.inventory.warehouse.subwarehousetype

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.warehouse.SubWarehouseType
import com.bits.bdfp.inventory.warehouse.SubWarehouseTypeService
import com.bits.bdfp.inventory.warehouse.Warehouse
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateSubWarehouseTypeAction")
class UpdateSubWarehouseTypeAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    SubWarehouseTypeService subWarehouseTypeService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

    public Object preCondition(Object object, Object params) {
        Boolean isError = false
        try{

        SubWarehouseType subWarehouseType = SubWarehouseType.read(Long.parseLong(params?.id?.toString()))
        ApplicationUser applicationUser = (ApplicationUser) object
        subWarehouseType.properties = params
        subWarehouseType.userUpdated = applicationUser
        subWarehouseType.dateUpdated = new Date()


//            String domain = 'sub_warehouse_type'
//            String id =  subWarehouseType.id

//            isError = validationCheckService.validationCheck(domain,id)

            if (!subWarehouseType.validate()) {
                message = this.getValidationErrorMessage(subWarehouseType)
                return message
            }
//            else if (isError){
//                message = this.getMessage('SubWarehouseType', Message.ERROR, 'This Sub warehouse type has already been used')
//                return message
//            }
//            else{
                return subWarehouseType
//            }


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
            ApplicationUser applicationUser = (ApplicationUser) object

            Object result = this.preCondition(applicationUser, params)
            if (result instanceof SubWarehouseType) {
                int noOfRows = (int) subWarehouseTypeService.update(result)
                if (noOfRows > 0) {
                    message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage(result, Message.ERROR, this.FAIL_UPDATE)
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
