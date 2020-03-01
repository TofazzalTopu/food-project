package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.inventory.warehouse.transferproducts.ListReceiveInventoryToInventoryTransferAction
import com.bits.bdfp.inventory.warehouse.transferproducts.ReceiveInventoryToSubInventoryTransferAction
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class ReceiveInventoryToInventoryTransferController {
    @Autowired
    ListReceiveInventoryToInventoryTransferAction listReceiveInventoryToInventoryTransferAction
    @Autowired
    ReceiveInventoryToSubInventoryTransferAction receiveInventoryToSubInventoryTransferAction

    static defaultAction = "show"

    def list = {
        ApplicationUser applicationUser = session?.applicationUser
        render listReceiveInventoryToInventoryTransferAction.execute(params, applicationUser) as JSON
    }

    def show = {
        render(view: "show")
    }

    def transferReceive = {
        ApplicationUser applicationUser = session?.applicationUser
        render receiveInventoryToSubInventoryTransferAction.execute(params,applicationUser) as JSON
    }
}
