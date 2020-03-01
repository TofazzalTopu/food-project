package com.bits.bdfp.inventory.workflow

import com.docu.security.ApplicationUser

class WorkflowUserMapping {
    Workflow            workflow
    ApplicationUser     applicationUser
    Integer             prioritySequence
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
        applicationUser(nullable: false, unique: 'workflow')
        prioritySequence(nullable: true, unique: 'workflow')
        isActive(nullable: true)
        userCreated(blank: false,nullable: false)
        userUpdated(blank: true,nullable: true)
        dateCreated(blank: false,nullable: false)
        dateUpdated(blank: true,nullable: true)
    }
}
