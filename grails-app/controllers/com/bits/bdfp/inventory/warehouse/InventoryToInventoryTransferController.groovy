package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.inventory.warehouse.transferproducts.CreateInventoryToInventoryTransferAction
import com.bits.bdfp.inventory.warehouse.transferproducts.UtilInventoryToInventoryTransferAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class InventoryToInventoryTransferController {
    @Autowired
    UtilInventoryToInventoryTransferAction utilInventoryToInventoryTransferAction
    @Autowired
    CreateInventoryToInventoryTransferAction createInventoryToInventoryTransferAction

    def show = {
        ApplicationUser applicationUser = session?.applicationUser

        Map mapSenderDpInventory = utilInventoryToInventoryTransferAction.getDpInfoByApplicationUser(applicationUser.id)

        if(mapSenderDpInventory.dpInfo){
            List receiverDp = utilInventoryToInventoryTransferAction.getReceiverDpList(mapSenderDpInventory.dpInfo.id)
            render(view: "show", model: [map:mapSenderDpInventory,receiverDp:receiverDp])
        }else{
            render(view: "unAuthorized")
        }
    }

    def getTcNo = {
        int tcNo = Math.abs(new Random().nextInt() % 6000) + 1
        InventoryToInventoryTransfer inventoryToInventoryTransfer = InventoryToInventoryTransfer.findByTransferChallanNumber("TC-"+tcNo)

        if(inventoryToInventoryTransfer){
            tcNo = Math.abs(new Random().nextInt() % 6000) + 1
        }
        render tcNo
    }

    def getDataAndSubInventoryListByInventoryId = {
        render utilInventoryToInventoryTransferAction.getDataAndSubInventoryListByInventoryId(Long.parseLong(params.id)) as JSON
    }

    def getAllProductListBySubInventoryId = {
        render utilInventoryToInventoryTransferAction.getAllProductListBySubInventoryId(Long.parseLong(params.id), Long.parseLong(params.dpId)) as JSON
    }

    def getReceiverDpInfo = {
        render utilInventoryToInventoryTransferAction.getReceiverDpInfo(Long.parseLong(params.id)) as JSON
    }

    def getReceiverInventoryInfo = {
        render utilInventoryToInventoryTransferAction.getReceiverInventoryInfo(Long.parseLong(params.id)) as JSON
    }

    def transferChallan = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createInventoryToInventoryTransferAction.execute(params,applicationUser)
        render message as JSON
    }
}
