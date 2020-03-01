package com.bits.bdfp.customer.customermaster

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.CustomerMasterService
import com.bits.bdfp.customer.CustomerShippingAddress
import com.bits.bdfp.customer.CustomerStatus
import com.bits.bdfp.customer.CustomerTerritorySubArea
import com.bits.bdfp.customer.MasterType
import com.bits.bdfp.geolocation.TerritorySubArea
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.ObjectUtil
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createCustomerMasterAction")
class CreateCustomerMasterAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerMasterService customerMasterService

    Message message

    public Object preCondition(Object params, Object object) {
        try {
            CustomerMaster customerMaster = (CustomerMaster) object
            if (!customerMaster.validate()) {
                return this.getValidationErrorMessage(customerMaster)
            } else if(!customerMaster.legacyId||!customerMaster.customerType||!customerMaster.category||!customerMaster.pricingCategory
                    ||!customerMaster.customerPriority||!customerMaster.customerSalesChannel
                    ||!customerMaster.contactPerson||!customerMaster.contactNo||!customerMaster.customerContactType
                    ||!customerMaster.presentAddress||!customerMaster.permanentAddress||!customerMaster.customerPaymentType){
                return this.getMessage(customerMaster, Message.ERROR, "All Required field is not available")
            } else if (customerMaster.customerPaymentType == ApplicationConstants.CUSTOMER_PAYMENT_TYPE_CREDIT_ID ) {
                // Credit Type Payment
                if(customerMaster.customerCreditLimit <= 0 ){
                    return this.getMessage(customerMaster, Message.ERROR, "Credit Limit must be grater than Zero")
                }else if(customerMaster.creditPeriodInDays <= 0 ){
                    return this.getMessage(customerMaster, Message.ERROR, "Credit Period must be grater than Zero")
                }
            } else if (customerMaster.customerPaymentType == ApplicationConstants.CUSTOMER_PAYMENT_TYPE_CASH_ID ) {
                // Cash Type Payment
                customerMaster.customerCreditLimit = 0
                customerMaster.creditPeriodInDays = 0
                return this.getMessage(customerMaster, Message.SUCCESS, this.SUCCESS_SAVE)
            } else {
                return this.getMessage(customerMaster, Message.SUCCESS, this.SUCCESS_SAVE)
            }
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("CustomerMaster", Message.ERROR, "Exception-${ex.message}")
        }
    }

    public Object execute(Object params, Object object) {
        try {
            Map map = [:]
            CustomerMaster customerMaster = new CustomerMaster(params)
            customerMaster.masterType = MasterType.findByName('Customer')
            customerMaster.customerStatus = CustomerStatus.ACTIVE
            ApplicationUser applicationUser = (ApplicationUser) object
            customerMaster.userCreated = applicationUser
            customerMaster.dateCreated = new Date()
            customerMaster.code = "AUTO"
            message = this.preCondition(null, customerMaster)
            if (message.type != 1){
                return message
            }
            customerMaster.code = CodeGenerationUtil.instance.getCode(customerMaster.enterpriseConfiguration, ApplicationConstants.CUSTOMER_KEY, customerMaster.enterpriseConfiguration.code, "", "", "", "", "", "", "", "", "")

            List<CustomerTerritorySubArea> customerTerritorySubAreaList = ObjectUtil.instantiateObjects(params.items2, CustomerTerritorySubArea.class)
            for (int i = 0; i < customerTerritorySubAreaList.size(); i++) {
                customerTerritorySubAreaList[i].customerMaster = customerMaster
                customerTerritorySubAreaList[i].userCreated = applicationUser
                customerTerritorySubAreaList[i].dateCreated = new Date()
            }

            int length = Integer.parseInt(params.shipSize)
            CustomerShippingAddress [] customerShippingAddresses = new CustomerShippingAddress[length]
            params.items.each { key, val ->
                for (int i = 0; i < length; i++) {
                    if (key == "shipToAddress[" + i + "]") {
                        customerShippingAddresses[i] = new CustomerShippingAddress()
                        customerShippingAddresses[i].address = val.address
                        customerShippingAddresses[i].customerMaster = customerMaster
                        customerShippingAddresses[i].isActive = true
                        customerShippingAddresses[i].userCreated = applicationUser
                        customerShippingAddresses[i].dateCreated = new Date()
                    }
                }
            }

            map.put('customerMaster', customerMaster)
            map.put('customerTerritorySubAreaList', customerTerritorySubAreaList)
            map.put('customerShippingAddresses', customerShippingAddresses)

            customerMaster = customerMasterService.create(map)
            if (customerMaster) {
                message = this.getMessage(customerMaster, Message.SUCCESS, 'Customer saved successfully for Code: ' + customerMaster.code)
            } else {
                message = this.getMessage(customerMaster, Message.ERROR, this.FAIL_SAVE)
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}