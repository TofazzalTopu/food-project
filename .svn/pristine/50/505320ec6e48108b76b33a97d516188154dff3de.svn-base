package com.bits.bdfp.inventory.sales

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.warehouse.SubWarehouse
import com.bits.bdfp.inventory.warehouse.Warehouse
import com.docu.security.ApplicationUser

/**
 * Created by NZ on 1/6/2016.
 */
class MarketReturn {

    Date date
    String spotTime
    String temperature
    String loader
    String returnedBy
    String receivedBy
    String authorizedPerson
    String checkedBy

    Boolean isDpCustomer
    DistributionPoint sourceDistributionPoint
    DistributionPoint destinationDistributionPoint
    Warehouse warehouse
    SubWarehouse subWarehouse
    CustomerMaster primaryCustomer
    CustomerMaster secondaryCustomer
    String mrNo
    String mrStatus

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    dateUpdated

    static constraints = {
        destinationDistributionPoint(blank: false, nullable: false)
        isDpCustomer(blank: false, nullable: false)
        primaryCustomer(nullable: false,blank: false)
        mrNo(blank: false, nullable: false, unique: true)
        mrStatus(nullable: false,blank: false)
        warehouse(nullable: true,blank: true)
        subWarehouse(nullable: true,blank: true)
        secondaryCustomer(blank: true, nullable: true)
        sourceDistributionPoint(blank: true, nullable: true)
        date(blank: true, nullable: true)
        spotTime(blank: true, nullable: true)
        temperature(blank: true, nullable: true)
        loader(blank: true, nullable: true)
        returnedBy(blank: true, nullable: true)
        receivedBy(blank: true, nullable: true)
        authorizedPerson(blank: true, nullable: true)
        checkedBy(blank: true, nullable: true)
        userCreated(blank: false, nullable: false)
        userUpdated(blank: true, nullable: true)
        dateCreated(blank: false, nullable: false)
        dateUpdated(blank: true, nullable: true)
    }
    @Override
    public String toString() {
        return null;
    }
}
