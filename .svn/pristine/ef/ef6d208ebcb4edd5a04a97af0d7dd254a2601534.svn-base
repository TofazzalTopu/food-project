package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.inventory.warehouse.registerfinishgood.ListReceivableOrderAction
import com.bits.bdfp.inventory.warehouse.registerfinishgood.ListReceivableOrderDetailsAction
import com.bits.bdfp.inventory.warehouse.registerfinishgood.ReceiveFinishGoodAction
import com.bits.bdfp.inventory.warehouse.subwarehouse.ListSubWarehouseByWarehouseAction
import com.bits.bdfp.inventory.warehouse.warehouse.ListNonFactoryWarehouseByApplicationUserAction
import com.bits.bdfp.inventory.warehouse.warehouse.ListWarehouseByApplicationUserAction
import com.docu.common.Message
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class RegisterFinishGoodController {
    @Autowired
    ListReceivableOrderAction listReceivableOrderAction
    @Autowired
    ListReceivableOrderDetailsAction listReceivableOrderDetailsAction
    @Autowired
    ListWarehouseByApplicationUserAction listWarehouseByApplicationUserAction
    @Autowired
    ListSubWarehouseByWarehouseAction listSubWarehouseByWarehouseAction
    @Autowired
    ReceiveFinishGoodAction receiveFinishGoodAction
    @Autowired
    ListNonFactoryWarehouseByApplicationUserAction listNonFactoryWarehouseByApplicationUserAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def show = {
        List wareHouseList = (List) listNonFactoryWarehouseByApplicationUserAction.execute(params, null)
        if(!wareHouseList){
            render(view: "/inventory/registerFinishGood/unAuthorized")
            return
        }
        if(wareHouseList.size() == 0){
            render(view: "/inventory/registerFinishGood/unAuthorized")
            return
        }
        render(view: "/inventory/registerFinishGood/show", model: [wareHouseList: wareHouseList])
    }

    def listReceivableOrder = {
        render listReceivableOrderAction.execute(params, null) as JSON
    }

    def listReceivableOrderDetails = {
        render listReceivableOrderDetailsAction.execute(params, null) as JSON
    }

    def listSubWarehouse = {
        render listSubWarehouseByWarehouseAction.execute(params, null) as JSON
    }

    def receive = {
        Message message = (Message) receiveFinishGoodAction.preCondition(params, null)
        if (message.type == Message.SUCCESS) {
            message = receiveFinishGoodAction.execute(params, null)
        }
        render message as JSON
    }
}
