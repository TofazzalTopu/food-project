package com.bits.bdfp.customer

import com.docu.security.ApplicationUser

class CustomerShippingAddress {
    CustomerMaster  customerMaster
    String          address
    Boolean         isActive
    ApplicationUser userCreated
    ApplicationUser userUpdated
    Date            dateCreated
    Date            lastUpdated


    static constraints = {
        customerMaster(nullable: false)
        address(nullable: false,blank: false)
        isActive(nullable: true)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable: false)
        lastUpdated(nullable: true)
    }
    @Override
    public String toString() {
        return address;
    }
}
