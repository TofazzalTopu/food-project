package com.bits.bdfp.inventory.setup

import com.bits.bdfp.inventory.setup.deliverytruck.CreateDeliveryTruckAction
import com.bits.bdfp.inventory.setup.deliverytruck.DeleteDeliveryTruckAction
import com.bits.bdfp.inventory.setup.deliverytruck.ListDeliveryTruckAction
import com.bits.bdfp.inventory.setup.deliverytruck.ListTruckAction
import com.bits.bdfp.inventory.setup.deliverytruck.UpdateDeliveryTruckAction
import com.bits.bdfp.inventory.setup.deliverytruck.ReadDeliveryTruckAction
import com.bits.bdfp.inventory.setup.deliverytruck.SearchDeliveryTruckAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class DeliveryTruckController {

    @Autowired
    private CreateDeliveryTruckAction createDeliveryTruckAction
    @Autowired
    private UpdateDeliveryTruckAction updateDeliveryTruckAction
    @Autowired
    private ListDeliveryTruckAction listDeliveryTruckAction
    @Autowired
    private DeleteDeliveryTruckAction deleteDeliveryTruckAction
    @Autowired
    private ReadDeliveryTruckAction readDeliveryTruckAction
    @Autowired
    private SearchDeliveryTruckAction searchDeliveryTruckAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    @Autowired
    ListTruckAction listTruckAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listDeliveryTruckAction.execute(params, null)
        render listDeliveryTruckAction.postCondition(null, list) as JSON
    }

    def show = {
        DeliveryTruck deliveryTruck = new DeliveryTruck()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [deliveryTruck: deliveryTruck,list: enterpriseList,result:result as JSON])
    }

    def create = {
        DeliveryTruck deliveryTruck = new DeliveryTruck(params)
        ApplicationUser applicationUser=session?.applicationUser
        DeliveryTruck deliveryTruckInstance = createDeliveryTruckAction.preCondition(applicationUser, deliveryTruck)
        Message message = null
        if (deliveryTruckInstance == null) {
            message = createDeliveryTruckAction.getValidationErrorMessage(deliveryTruck)
        } else {
            deliveryTruckInstance = createDeliveryTruckAction.execute(null, deliveryTruckInstance)
            if (deliveryTruckInstance) {
                message = createDeliveryTruckAction.getMessage("Delivery Truck",Message.SUCCESS, createDeliveryTruckAction.SUCCESS_SAVE)
            } else {
                message = createDeliveryTruckAction.getMessage("Delivery Truck",Message.ERROR, createDeliveryTruckAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readDeliveryTruckAction.execute(params, null)
        render result as JSON
    }
    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updateDeliveryTruckAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteDeliveryTruckAction.execute(params, null)
        render message as JSON
    }


    def search = {
        DeliveryTruck deliveryTruck = searchDeliveryTruckAction.execute(params.fieldName, params.fieldValue)
        if (deliveryTruck) {
            render deliveryTruck as JSON
        } else {
            render ''
        }

    }

    def listTruck = {
        List list = listTruckAction.execute(params, null)
        render listTruckAction.postCondition(null, list) as JSON
    }

    def getAjaxDataVal = {
        DeliveryTruck deliveryTruckNumber = DeliveryTruck.findByVehicleNumber(params.vehicleNumber);
        if(deliveryTruckNumber){
            render deliveryTruckNumber as JSON
        }else{
            render ''
        }
    }
}
