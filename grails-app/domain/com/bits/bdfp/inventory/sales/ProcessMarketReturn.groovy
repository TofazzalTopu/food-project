package com.bits.bdfp.inventory.sales

import com.docu.security.ApplicationUser

class ProcessMarketReturn {

    String      mrNo
    String      qcPersonName
    String      mrProcessedBy
    String      qcPersonPin
    String      qcPerformingTime

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    dateUpdated

    static constraints = {
        mrNo(blank: false, nullable: false)
        qcPersonName(blank: true, nullable: true)
        mrProcessedBy(blank: true, nullable: true)
        qcPersonPin(blank: true, nullable: true)
        qcPerformingTime(blank: true, nullable: true)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
        dateCreated(blank: false, nullable: false)
        dateUpdated(blank: true, nullable: true)
    }
}
