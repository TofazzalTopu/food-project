package com.bits.bdfp.inventory.warehouse

import com.docu.security.ApplicationUser

class FinishGoodWarehouse {
    String          transactionNo
    String          batchNo
    Boolean         isBatchControl
    Date            dateTransaction
    String          timeTransaction
    Warehouse       warehouse
    SubWarehouse    subWarehouse

    ApplicationUser userCreated
    ApplicationUser userUpdated
    Date            dateCreated
    Date            dateUpdated

    static constraints = {
        transactionNo(nullable: false,blank: false)
        batchNo(nullable: true,blank: true)
        isBatchControl(nullable: false,blank: false)
        dateTransaction(nullable: true,blank: true)
        timeTransaction(nullable: true,blank: true)
        warehouse(nullable: false,blank: false)
        subWarehouse(nullable: false,blank: false)
        userCreated(nullable: false,blank: false)
        dateCreated(nullable: false,blank: false)
        userUpdated(nullable: true,blank: true)
        dateUpdated(nullable: true,blank: true)
    }
}
