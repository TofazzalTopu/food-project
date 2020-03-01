package com.bits.bdfp.customer.customermaster

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.CustomerMasterService
import com.bits.bdfp.customer.CustomerShippingAddress
import com.bits.bdfp.customer.CustomerShippingAddressService
import com.bits.bdfp.customer.CustomerTerritorySubArea
import com.bits.bdfp.customer.CustomerTerritorySubAreaService
import com.bits.bdfp.geolocation.TerritorySubArea
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Action
import com.docu.common.Message
import com.docu.commons.ObjectUtil
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateCustomerMasterAction")
class UpdateCustomerMasterAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerMasterService customerMasterService
    @Autowired
    CustomerShippingAddressService customerShippingAddressService
    @Autowired
    CustomerTerritorySubAreaService customerTerritorySubAreaService

    Message message

    public Object preCondition(Object params, Object object) {
        try {
            CustomerMaster customerMaster = object.get('customerMaster')
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

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            Map map = [:]
            int length = 0
            ApplicationUser applicationUser = (ApplicationUser) object
            CustomerMaster customerMaster = CustomerMaster.read(Long.parseLong(params.id))
            customerMaster.properties = params
            customerMaster.userUpdated = applicationUser
            customerMaster.lastUpdated = new Date()
            if (params.deletedEntriesCount) {
                length = Integer.parseInt(params.deletedEntriesCount)
                String[] deletedEnterpriseIds = params.deletedEntries.split(',')
                for (int i = 0; i < length; i++) {
                    if (deletedEnterpriseIds[i]) {
                        CustomerShippingAddress customerShippingAddress = CustomerShippingAddress.read(Long.parseLong(deletedEnterpriseIds[i]))
                        customerShippingAddressService.delete(customerShippingAddress)
                    }
                }
            }

            List<CustomerTerritorySubArea> customerTerritorySubAreas = ObjectUtil.instantiateObjects(params.items2, CustomerTerritorySubArea.class)
            for (int i = 0; i < customerTerritorySubAreas.size(); i++) {
                    customerTerritorySubAreas[i].customerMaster = customerMaster
                    customerTerritorySubAreas[i].userCreated = applicationUser
                    customerTerritorySubAreas[i].dateCreated = new Date()
            }

            length = Integer.parseInt(params.shipSize)
            CustomerShippingAddress[] customerShippingAddresses = new CustomerShippingAddress[length]
            params.items.each { key, val ->
                for (int i = 0; i < length; i++) {
                    if (key == "shipToAddress[" + i + "]") {
//                            customerShippingAddresses[i] = CustomerShippingAddress.read(Long.parseLong(val.id))
//                            customerShippingAddresses[i].address = val.address
//                            customerShippingAddresses[i].customerMaster = customerMaster
//                            customerShippingAddresses[i].isActive = true
//                            customerShippingAddresses[i].userUpdated = applicationUser
//                            customerShippingAddresses[i].lastUpdated = new Date()
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
            map.put('customerTerritorySubAreas', customerTerritorySubAreas)
            map.put('customerShippingAddresses', customerShippingAddresses)
            message = this.preCondition(null, map)
            if (message.type == 1) {
                int updateCount = customerMasterService.update(map)
                if (updateCount == 1) {
                    message = this.getMessage(customerMaster, Message.SUCCESS, "Customer information updated successfully for Code: " + customerMaster.code)
                } else {
                    message = this.getMessage(customerMaster, Message.ERROR, this.FAIL_SAVE)
                }
            }
            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
