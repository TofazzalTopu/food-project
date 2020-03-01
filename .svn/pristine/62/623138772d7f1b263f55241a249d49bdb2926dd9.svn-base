package com.bits.bdfp.inventory.warehouse.warehouse

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.product.ProductType
import com.bits.bdfp.inventory.warehouse.Warehouse
import com.bits.bdfp.inventory.warehouse.WarehouseService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateWarehouseAction")
class UpdateWarehouseAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    WarehouseService warehouseService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

    public Object preCondition(Object object, Object params) {
        try {

            Warehouse warehouse = Warehouse.read(Long.parseLong(params?.id?.toString()))
            ApplicationUser applicationUser = (ApplicationUser) object
            warehouse.properties = params
            warehouse.userUpdated = applicationUser
            warehouse.dateUpdated = new Date()

            String domain = 'warehouse'
            String id = warehouse.id

            if (!warehouse.validate()) {
                message = this.getValidationErrorMessage(warehouse)
                return message
            } else {
                return warehouse
            }

        } catch (Exception ex) {
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
            if (result instanceof Warehouse) {
                int noOfRows = (int) warehouseService.update(result)
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
