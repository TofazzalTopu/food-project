package com.bits.bdfp.inventory.setup.deliverytruck

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.setup.DeliveryTruck
import com.bits.bdfp.inventory.setup.DeliveryTruckService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateDeliveryTruckAction")
class UpdateDeliveryTruckAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    DeliveryTruckService deliveryTruckService
    @Autowired
    ValidationCheckService validationCheckService
    Message message
    public Object preCondition(Object object, Object params) {
        Boolean isError = false
        try{

        ApplicationUser applicationUser=(ApplicationUser)object
        DeliveryTruck deliveryTruck = DeliveryTruck.read(Long.parseLong(params?.id?.toString()))
        deliveryTruck.properties = params
        deliveryTruck.userUpdated=applicationUser
        deliveryTruck.dateUpdated = new Date()

            String domain = 'delivery_truck'
            String id =  deliveryTruck.id

            isError = validationCheckService.validationCheck(domain,id)

        if (!deliveryTruck.validate()) {
            message = this.getValidationErrorMessage(deliveryTruck)
            return message
        }
        else if (isError){
            message = this.getMessage('Delivery Truck', Message.ERROR, 'This Delivery Truck  has already been used')
            return message
        }
        else{
            return deliveryTruck
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
            if (result instanceof DeliveryTruck) {
                int noOfRows = (int) deliveryTruckService.update(result)
                if (noOfRows > 0) {
                    message = this.getMessage("Delivery Truck", Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage("Delivery Truck", Message.ERROR, this.FAIL_UPDATE)
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            message= this.getMessage("Delivery Truck", Message.ERROR, "Exception-${ex.message}")
            return message;
        }


    }
}
