package com.bits.bdfp.inventory.warehouse

import com.docu.security.ApplicationUser

class SubWarehouse {
    Warehouse        warehouse
    SubWarehouseType subWarehouseType
    String           name


    ApplicationUser userCreated
    ApplicationUser userUpdated
    Date            dateCreated
    Date            dateUpdated
    static constraints = {
        warehouse(nullable: false)
        subWarehouseType(nullable: false, unique: 'warehouse')
        name(blank: false,nullable: false,unique: 'warehouse')
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
        dateCreated(blank: false, nullable: false)
        dateUpdated(blank: true, nullable: true)
    }
    @Override
    public String toString() {
        return name ;
    }
}
