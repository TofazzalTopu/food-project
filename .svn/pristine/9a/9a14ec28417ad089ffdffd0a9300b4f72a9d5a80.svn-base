package com.bits.bdfp.inventory.setup.poscustomer

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.setup.PosCustomer
import com.bits.bdfp.inventory.setup.PosCustomerService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updatePosCustomerAction")
class UpdatePosCustomerAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    PosCustomerService posCustomerService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

    public Object preCondition(Object object, Object params) {
        Boolean isError = false
        try{

        ApplicationUser applicationUser=(ApplicationUser)object
        PosCustomer posCustomer = PosCustomer.read(Long.parseLong(params?.id?.toString()))
        posCustomer.properties = params
        posCustomer.userUpdated = applicationUser
        posCustomer.dateUpdated = new Date()

            String domain = 'pos_customer'
            String id =  posCustomer.id

            isError = validationCheckService.validationCheck(domain,id)

            if (!posCustomer.validate()) {
                message = this.getValidationErrorMessage(posCustomer)
                return message
            }
            else if (isError){
                message = this.getMessage('PosCustomer', Message.ERROR, 'This Pos Customer  has already been used')
                return message
            }
            else{
                return posCustomer
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

    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser=(ApplicationUser)object
            Object result = this.preCondition(applicationUser,params)
            if (result instanceof PosCustomer) {
                int noOfRows = (int) posCustomerService.update(result)
                if (noOfRows > 0) {
                    message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage(result, Message.ERROR, this.FAIL_UPDATE)
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            message= this.getMessage("PosCustomer", Message.ERROR, "Exception-${ex.message}")
            return message;
        }

    }
}
