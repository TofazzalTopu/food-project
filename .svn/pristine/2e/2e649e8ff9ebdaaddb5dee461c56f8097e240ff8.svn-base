package com.bits.bdfp.customer.customersaleschannel

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.customer.CustomerSalesChannel
import com.bits.bdfp.customer.CustomerSalesChannelService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateCustomerSalesChannelAction")
class UpdateCustomerSalesChannelAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerSalesChannelService customerSalesChannelService
    @Autowired
    ValidationCheckService validationCheckService
    Message message
    public Object preCondition(Object object, Object params) {
        Boolean isError = false
        try{

        CustomerSalesChannel customerSalesChannel = CustomerSalesChannel.read(Long.parseLong(params?.id?.toString()))
        ApplicationUser applicationUser = (ApplicationUser) object
        customerSalesChannel.userUpdated = applicationUser
        customerSalesChannel.lastUpdated=new Date()
        customerSalesChannel.properties = params

            String domain = 'customer_sales_channel'
            String id =  customerSalesChannel.id

            isError = validationCheckService.validationCheck(domain,id)

            if (!customerSalesChannel.validate()) {
                message = this.getValidationErrorMessage(customerSalesChannel)
                return message
            }
            else if (isError){
                message = this.getMessage('Customer Sales Channel', Message.ERROR, 'This Customer Sales Channel has already been used')
                return message
            }
            else{
                return customerSalesChannel
            }


        }catch (Exception ex) {
            log.error(ex.message)
            throw ex
        }
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Message execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object

            Object result = this.preCondition(applicationUser, params)
            if (result instanceof CustomerSalesChannel) {
                int noOfRows = (int) customerSalesChannelService.update(result)
                if (noOfRows > 0) {
                    message = this.getMessage("Customer Sales Channel", Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage("Customer Sales Channel", Message.ERROR, this.FAIL_UPDATE)
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            message= this.getMessage("Customer Sales Channel", Message.ERROR, "Exception-${ex.message}")
            return message;
        }

    }
}
