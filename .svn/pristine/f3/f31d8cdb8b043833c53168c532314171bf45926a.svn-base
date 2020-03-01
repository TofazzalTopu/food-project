package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.inventory.workflow.Workflow
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class DemandOrderApprovalWorkflow {
    EnterpriseConfiguration enterpriseConfiguration
    Workflow            workflow
    Boolean             isApprove
    Boolean             isReject
    ApplicationUser     userApprovedReject
    Date                dateApprovedReject

    ApplicationUser userCreated
    ApplicationUser userUpdated
    Date            dateCreated
    Date            lastUpdated
    static constraints = {
        workflow(nullable: false,unique: 'enterpriseConfiguration')
        isApprove(nullable: true)
        isReject(nullable: true)
        userApprovedReject(nullable: false)
        dateApprovedReject(nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        lastUpdated(nullable: true)
    }
}
