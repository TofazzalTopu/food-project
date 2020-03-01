package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.inventory.workflow.Workflow
import com.docu.security.ApplicationUser

class PrimaryDemandOrderApprovalStatus {
    PrimaryDemandOrder  primaryDemandOrder
    ApplicationUser     userApproved
    Workflow            workflow
    DemandOrderStatus   demandOrderStatus
    Boolean             isApproved
    Boolean             isReject
    String              remarks
    String              rejectionCause
    Date                dateCreated
    Date                lastUpdated

    static constraints = {
        primaryDemandOrder(nullable: false)
        userApproved(nullable: true)
        workflow(nullable: false)
        demandOrderStatus(nullable: false)
        isApproved(nullable: true)
        isReject(nullable: true)
        rejectionCause(nullable: true)
        remarks(blank: true, nullable: true)
    }
}
