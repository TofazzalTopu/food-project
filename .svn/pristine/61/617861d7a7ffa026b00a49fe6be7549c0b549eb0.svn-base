package com.bits.bdfp.bill

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.geolocation.TerritorySubArea

class CreateBillTemp {

    //TerritoryConfiguration      territory
    //TerritorySubArea            territoryGeoLocation
    //CustomerCategory            customerCategory

    Integer                     customerMasterId

    String                      invoiceNumber
    Date                        deliveryDate
    Double                      receivableAmount


    //Integer                     customerId

    String                      billNumber
    Date                        billGenerationDate

    String                      purchaseOrderNumber
    Date                        purchaseOrderDate

    String                      vatChallanNumber
    Date                        vatChallanDate

    Double                      totalAmount

    static constraints = {
        invoiceNumber(nullable: true)
        deliveryDate(nullable: true)
        receivableAmount(nullable: true)
        totalAmount(nullable: true)

        customerMasterId(nullable: true)
        /*territory(nullable: true)
        territoryGeoLocation(blank: true, nullable: true)
        territoryGeoLocation(blank: true, nullable: true)*/

        //customerId(blank: true,nullable: true)
        //customerCategory(blank: true,nullable: true)
        billNumber(nullable: true)
        billGenerationDate(nullable: true)
        purchaseOrderNumber(nullable: true)
        purchaseOrderDate(nullable: true)
        vatChallanNumber(nullable: true)
        vatChallanDate(nullable: true)

    }

    static mapping = {
        id(generator: 'org.hibernate.id.enhanced.SequenceStyleGenerator',
                params: [sequence_name: 'CreateBillTemp_seq', initial_value: 1])
    }

    @Override
    public String toString() {
        return id;
    }

}
