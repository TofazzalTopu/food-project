package com.bits.bdfp.inventory.sales

import com.bits.bdfp.inventory.setup.DeliveryTruck
import com.docu.security.ApplicationUser

class LoadingSlip {
    String                  loadingSlipNo
    Date                    dateSlipDate
    DeliveryTruck           deliveryTruck
    Integer                 printCount
    Boolean                 isDelivered

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    dateUpdated

    static constraints = {
        loadingSlipNo(nullable: false,blank: false,unique: true)
        deliveryTruck(nullable: false)
        isDelivered(nullable: true)
        dateSlipDate(nullable: false)
        printCount(nullable: false)
        userCreated(nullable: false,blank: false)
        dateCreated(nullable: false,blank: false)
        userUpdated(nullable: true,blank: true)
        dateUpdated(nullable: true,blank: true)
    }
}
