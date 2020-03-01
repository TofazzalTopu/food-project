package com.bits.bdfp.accounts

import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class Mushak {

    Invoice                         invoice
    String                          name
    String                          address
    String                          customerTin
    String                          finalDestination
    String                          vehicleInfo
    EnterpriseConfiguration         enterpriseConfiguration
    String                          enterpriseAddress
    String                          enterpriseTin
    String                          challanNo
    Date                            challanHandoverDate
    String                          challanHandoverTime
    Date                            deliveryDate
    String                          mushakNo
    Float                           totalMushakAmount
    DistributionPoint               distributionPoint

    ApplicationUser userCreated
    ApplicationUser userUpdated
    Date            dateCreated
    Date            lastUpdated

    static constraints = {
        invoice(blank: false,nullable: false)
        name(blank: false,nullable: false)
        address(blank: false,nullable: false)
        customerTin(blank: true,nullable: true)
        finalDestination(blank: false,nullable: false)
        vehicleInfo(blank: false,nullable: false)
        enterpriseConfiguration(blank: false,nullable: false)
        enterpriseAddress(blank: false,nullable: false)
        enterpriseTin(blank: false,nullable: false)
        challanNo(blank: false,nullable: false)
        challanHandoverDate(blank: false,nullable: false)
        challanHandoverTime(blank: false,nullable: false)
        deliveryDate(blank: false,nullable: false)
        mushakNo(blank: false,nullable: false)
        totalMushakAmount(blank: false,nullable: false)
        distributionPoint(blank: false,nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        lastUpdated(nullable: true)
    }
}
