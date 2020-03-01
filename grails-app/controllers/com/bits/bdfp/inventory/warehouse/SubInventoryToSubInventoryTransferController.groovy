package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.inventory.warehouse.transferproducts.CreateSubInventoryToSubInventoryTransferAction
import com.bits.bdfp.inventory.warehouse.transferproducts.UtilSubInventoryToSubInventoryTransferAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class SubInventoryToSubInventoryTransferController {
    @Autowired
    UtilSubInventoryToSubInventoryTransferAction utilSubInventoryToSubInventoryTransferAction
    @Autowired
    CreateSubInventoryToSubInventoryTransferAction createSubInventoryToSubInventoryTransferAction

    def show = {
        ApplicationUser applicationUser = session?.applicationUser

        //List list = utilSubInventoryToSubInventoryTransferAction.getInventoryListByUserId(applicationUser.customerMasterId)
        List disList = utilSubInventoryToSubInventoryTransferAction.getDistributionListByUserId(applicationUser.id)

        render(view: "show", model: [ disList:disList as JSON, distSize:disList?disList.size():0])
    }

    def transferProduct = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createSubInventoryToSubInventoryTransferAction.execute(params,applicationUser)
        render message as JSON
    }

    def getInventoryByDistributionPointId={
        ApplicationUser applicationUser = session?.applicationUser
        render utilSubInventoryToSubInventoryTransferAction.getInventoryListByUserId(Long.parseLong(params.distId)) as JSON
    }
    def getSubInventoryListByInventoryId = {
        render utilSubInventoryToSubInventoryTransferAction.getSubInventoryListByInventoryId(Long.parseLong(params.id)) as JSON
    }
    def getSubInventoryListByInventoryIdForTo = {
       render utilSubInventoryToSubInventoryTransferAction.getSubInventoryToListByInventoryId(Long.parseLong(params.id),Long.parseLong(params.inventoryId), params) as JSON
        //render utilSubInventoryToSubInventoryTransferAction.getSubInventoryToListByInventoryId(params) as JSON
    }

    def getAllProductListBySubInventoryId = {
        render utilSubInventoryToSubInventoryTransferAction.getAllProductListBySubInventoryId(Long.parseLong(params.id), Long.parseLong(params.dpId)) as JSON
    }
}
