package com.bits.bdfp.inventory.setup

import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.security.ApplicationUser

class Quotation {

    EnterpriseConfiguration     enterpriseConfiguration
    String                      quotationNo
    Date                        quotationDate
    String                      refNo
    Date                        refDate
    Date                        validFrom
    Date                        validTo
    String                      customerName
    String                      customerId
    String                      contactNumber
    String                      address
    Float                       shipmentCharge
    Float                       vatCharge
    Float                       handlingCharge
    Float                       otherCharge
    String                      paymentTerm
    String                      remarks
    String                      salesTermsAndConditions
    Boolean                     isActive

    ApplicationUser             userCreated
    ApplicationUser             userUpdated
    Date                        dateCreated
    Date                        lastUpdated

    static constraints = {
        quotationNo(nullable: false, blank: false)
        quotationDate(nullable: false, blank: false)
        refNo(nullable: true, blank: true)
        refDate(nullable: true, blank: true)
        validFrom(nullable: false, blank: false)
        validTo(nullable: false, blank: false)
        customerName(nullable: false, blank: false)
        customerId(nullable: false, blank: false)
        contactNumber(nullable: false, blank: false)
        address(nullable: false, blank: false)
        shipmentCharge(nullable: true, blank: true)
        vatCharge(nullable: true, blank: true)
        handlingCharge(nullable: true, blank: true)
        otherCharge(nullable: true, blank: true)
        paymentTerm(nullable: true, blank: true)
        remarks(nullable: true, blank: true)
        salesTermsAndConditions(nullable: true, blank: true)
        isActive(nullable: false, blank: false)
        userCreated(nullable: false, blank: false)
        userUpdated(nullable: true, blank: true)
        dateCreated(nullable: false, blank: false)
        lastUpdated(nullable: true, blank: true)
    }
}
