package com.bits.bdfp.customer

import com.bits.bdfp.common.Designation
import com.bits.bdfp.inventory.product.PricingCategory
import com.bits.bdfp.settings.Department
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.commons.Gender
import com.docu.security.ApplicationUser

class CustomerMaster {
    MasterType              masterType
    EnterpriseConfiguration enterpriseConfiguration
    CustomerTitle           customerTitle
    String                  firstName
    String                  middleName
    String                  lastName
    String                  name
    String                  alternativeName
    Gender                  gender
    String                  code
    String                  legacyId
    CustomerType            customerType
    CustomerCategory        category
    PricingCategory         pricingCategory //parnerType
    CustomerPriority        customerPriority
    CustomerSalesChannel    customerSalesChannel
    String                  contactPerson
    String                  contactNo
    CustomerContactType     customerContactType

    Designation             designation
    Department              department
    CustomerMaster          customerMaster   // Supervisor for Employee
    String                  mobile
    String                  email
    String                  vehicleNo
    String                  remarks
    //Address section
    String                  presentAddress
    String                  permanentAddress

    //Financial Information Part
    CustomerPaymentType     customerPaymentType
    Float                   customerCreditLimit
    Integer                 creditPeriodInDays
    Boolean                 isImmediate
    CustomerStatus          customerStatus

    CustomerLevel           customerLevel

    ApplicationUser         userCreated
    ApplicationUser         userUpdated
    Date                    dateCreated
    Date                    lastUpdated

    static constraints = {
        masterType(nullable: false)
        enterpriseConfiguration(nullable: false)
        customerTitle(nullable: true)
        firstName(nullable: true, blank: true)
        middleName(nullable: true, blank: true)
        lastName(nullable: true, blank: true)
        name(blank: false, nullable: false)
        gender(nullable: true)
        alternativeName(nullable: true, blank: true)
        code(nullable: false, blank: false, unique: 'enterpriseConfiguration')
        legacyId(nullable: true, blank: true)  // New Added. Will be Not Nullable
        customerType(nullable: true)
        category(nullable: true)
        pricingCategory (nullable: true)
        customerPriority(nullable: true)
        customerSalesChannel(nullable: true)
        contactPerson(nullable: true, blank: true)
        contactNo(nullable: true, blank: true)
        customerContactType(nullable: true)
        mobile(blank: true, nullable: true)
        email(blank: true, nullable: true)
        vehicleNo(blank: true, nullable: true)
        remarks(blank: true, nullable: true)
        designation(nullable: true)
        department(nullable: true)
        customerMaster(nullable: true)
        presentAddress(nullable: true, blank: true)
        permanentAddress(nullable: true, blank: true)
        customerPaymentType(nullable: true)
        customerCreditLimit(nullable: true)
        creditPeriodInDays(nullable: true)

        isImmediate(nullable: true)
        customerStatus(blank: false, nullable: false)
        customerLevel(blank: false, nullable: false)
        userCreated(nullable: false)
        userUpdated(nullable: true)
    }
    @Override
    public String toString() {
        return code + '-' + name
    }
}
