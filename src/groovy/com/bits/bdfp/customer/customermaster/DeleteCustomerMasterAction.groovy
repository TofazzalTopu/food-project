package com.bits.bdfp.customer.customermaster

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.customer.CustomerMasterService
import com.bits.bdfp.customer.CustomerShippingAddress
import com.bits.bdfp.customer.CustomerShippingAddressService
import com.bits.bdfp.customer.CustomerTerritorySubArea
import com.bits.bdfp.customer.CustomerTerritorySubAreaService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteCustomerMasterAction")
class DeleteCustomerMasterAction extends Action {

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerMasterService customerMasterService
    @Autowired
    CustomerTerritorySubAreaService customerTerritorySubAreaService
    @Autowired
    CustomerShippingAddressService customerShippingAddressService

    Message message

    public Object preCondition(Object params, Object object) {
        try {
            CustomerMaster customerMaster = customerMasterService.read(Long.parseLong(params.id.toString()))
            return customerMaster
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("CustomerMaster", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            Object result = this.preCondition(params, object)
            int x = 1
            while (x == 1) {
                CustomerTerritorySubArea customerTerritorySubArea = customerTerritorySubAreaService.search('customerMaster', params.id)
                CustomerShippingAddress customerShippingAddress = customerShippingAddressService.search('customerMaster', params.id)
                if(customerTerritorySubArea) {
                    customerTerritorySubAreaService.delete(customerTerritorySubArea)
                }
                if(customerShippingAddress){
                    customerShippingAddressService.delete(customerShippingAddress)
                }
                if(!customerTerritorySubArea && !customerShippingAddress){
                    x = 0
                }
            }
            if (result instanceof CustomerMaster) {
                int noOfRows = (int) customerMasterService.delete(result)
                if (noOfRows > 0) {
                    message = this.getMessage(result, Message.SUCCESS, "Customer deleted successfully.")
                } else {
                    message = this.getMessage(result, Message.ERROR, this.FAIL_DELETE)
                }
            }
            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            this.getMessage("CustomerMaster", Message.ERROR, "Exception-${ex.message}")
            return message;
        }
    }

}