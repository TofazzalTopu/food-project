package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.retailorder.RetailOrder
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.docu.security.ApplicationUser

/**
 * Created by abhijit.majumder on 11/24/2015.
 */
class Invoice {
    String                  code
    DistributionPoint       distributionPoint
    PrimaryDemandOrder      primaryDemandOrder
    SecondaryDemandOrder    secondaryDemandOrder
    RetailOrder             retailOrder
    Float                   vat       // actualVatValue for Direct Invoice
    Float                   ait
    Float                   discount  // actualDiscountValue for Direct Invoice
    Float                   serviceCharge  // actualOtherChargeValue for Direct Invoice
    Float                   invoiceAmount
    Float                   paidAmount
    CustomerMaster          defaultCustomer
    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    lastUpdated
    boolean                 isActive
    boolean                 isDirectInvoice

    // Data for Direct invoice
    String                  otherChargeName
    String                  discountName
    String                  vatTaxName

    //FOR POS CUSTOMER USE
    String                  externalCustomerName
    String                  externalCustomerMobile
    String                  externalCustomerAddress

    //FOR PAYMENT MOTHOD
    Date                    transactionDate
    String                  transactionNo
    String                  reference


    //FOR COMMISSION CALCULATION USE
    Boolean                 commissionCalculated
    Boolean                 isBill = Boolean.FALSE
    Boolean                 isIncentiveCalculated  = Boolean.FALSE

    static mapping = {
        isActive defaultValue: '1'
        isDirectInvoice defaultValue: '0'
        isBill defaultValue: '0'
        isIncentiveCalculated defaultValue: '0'
    }

    static constraints = {
        code(nullable: false, unique: true)
        distributionPoint(nullable: true, blank: true)
        primaryDemandOrder(nullable: true, blank:true)
        secondaryDemandOrder(nullable: true, blank:true)
        retailOrder(nullable: true, blank:true)
        discount(nullable: true, blank:true)
        serviceCharge(nullable: true,blank:true)
        invoiceAmount(blank: false,nullable: false)
        defaultCustomer(nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
        dateCreated(nullable:false, blank:false)
        lastUpdated(nullable: true, blank:true)
        externalCustomerName(nullable: true, blank:true)
        externalCustomerMobile(nullable: true, blank:true)
        externalCustomerAddress(nullable: true, blank:true)
        transactionDate(nullable: true, blank:true)
        transactionNo(nullable: true, blank:true)
        reference(nullable: true, blank:true)
        commissionCalculated(nullable: true, blank:true)
        isIncentiveCalculated(nullable: true, blank:true)
        // Data for Direct invoice
        otherChargeName(nullable: true, blank:true)
        discountName(nullable: true, blank:true)
        vatTaxName(nullable: true, blank:true)
        isBill(nullable: true)
    }
}
