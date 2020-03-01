package com.bits.bdfp.inventory.warehouse

import com.bits.bdfp.common.BankPaymentMethod
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder
import com.docu.security.ApplicationUser

class FinishedProductBooked {
    PrimaryDemandOrder  primaryDemandOrder
    String              invoiceNo
    String              otherChargeName
    Double              actualOtherChargeValue
    String              discountName
    Double              actualDiscountValue
    String              vatTaxName
    Double              actualVatValue
    BankPaymentMethod   paymentMethod
    Double              paymentReceived
    String              remarks
    Boolean             loadingSlipCreated
    ApplicationUser     userCreated
    ApplicationUser     userUpdated
    Date                dateCreated
    Date                dateUpdated

    static mapping = {
        paymentReceived defaultValue: '0.00'
        actualOtherChargeValue defaultValue: '0.00'
        actualDiscountValue defaultValue: '0.00'
        actualVatValue defaultValue: '0.00'
        loadingSlipCreated defaultValue: '0'
    }

    static constraints = {
        primaryDemandOrder(nullable: false,blank:false)
        invoiceNo(nullable: false,blank: false)
        otherChargeName(nullable: true,blank: true)
        actualOtherChargeValue(nullable: true,blank: true)
        discountName(nullable: true,blank: true)
        actualDiscountValue(nullable: true,blank: true)
        vatTaxName(nullable: true,blank: true)
        actualVatValue(nullable: true,blank: true)
        paymentMethod(nullable: true,blank: true)
        paymentReceived(nullable: true,blank: true)
        remarks(nullable: true,blank: true)
        loadingSlipCreated(blank: true, nullable: true)
        userCreated(nullable: false,blank: false)
        dateCreated(nullable: false,blank: false)
        userUpdated(nullable: true,blank: true)
        dateUpdated(nullable: true,blank: true)
    }
}
