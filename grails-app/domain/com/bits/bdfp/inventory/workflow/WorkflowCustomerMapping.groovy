package com.bits.bdfp.inventory.workflow

import com.bits.bdfp.customer.CustomerMaster
import com.docu.security.ApplicationUser

class WorkflowCustomerMapping {

    Workflow            workflow
    CustomerMaster      customerMaster
    Boolean             isActive

    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                dateUpdated

    static mapping = {
        isActive defaultValue: '1'
    }

    static constraints = {
        workflow(nullable: false)
        customerMaster(nullable: false, unique: true)
        isActive(nullable: true)
        userCreated(blank: false,nullable: false)
        userUpdated(blank: true,nullable: true)
        dateCreated(blank: false,nullable: false)
        dateUpdated(blank: true,nullable: true)
    }
}
