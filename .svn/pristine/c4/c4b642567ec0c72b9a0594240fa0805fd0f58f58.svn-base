package com.bits.bdfp.bill

import com.docu.security.ApplicationUser

class CreateBill {

    Integer                     customerMasterId

    String                      invoiceNumber
    Date                        deliveryDate
    Double                      receivableAmount

    String                      billNumber
    Date                        billGenerationDate

    String                      purchaseOrderNumber
    Date                        purchaseOrderDate

    String                      vatChallanNumber
    Date                        vatChallanDate

    Double                      totalAmount
    Boolean                     isActive

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        invoiceNumber(nullable: true)
        deliveryDate(nullable: true)
        receivableAmount(nullable: true)
        totalAmount(nullable: true)

        customerMasterId(nullable: true)

        billNumber(nullable: true)
        billGenerationDate(nullable: true)
        purchaseOrderNumber(nullable: true)
        purchaseOrderDate(nullable: true)
        vatChallanNumber(nullable: true)
        vatChallanDate(nullable: true)
        isActive(nullable: true, defaultValue: true)

        userCreated(nullable: true)
        userUpdated(nullable: true)
        dateCreated(nullable: true)
        lastUpdated(nullable: true)
    }
    static mapping = {
        id(generator: 'org.hibernate.id.enhanced.SequenceStyleGenerator',
               params: [sequence_name: 'CreateBill_seq', initial_value: 1])
    }

    @Override
    public String toString() {
        return id;
    }
}
