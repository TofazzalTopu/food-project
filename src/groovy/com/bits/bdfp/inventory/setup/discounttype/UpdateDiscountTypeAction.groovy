package com.bits.bdfp.inventory.setup.discounttype

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.setup.DiscountType
import com.bits.bdfp.inventory.setup.DiscountTypeService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateDiscountTypeAction")
class UpdateDiscountTypeAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    DiscountTypeService discountTypeService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

    public Object preCondition(Object object, Object params) {
        Boolean isError = false
        try{
            
        ApplicationUser applicationUser = (ApplicationUser) object
        DiscountType discountType = DiscountType.read(Long.parseLong(params?.id?.toString()))
        discountType.userUpdated = applicationUser
        discountType.dateUpdated = new Date()
        discountType.properties = params


            String domain = 'discount_type'
            String id =  discountType.id

            isError = validationCheckService.validationCheck(domain,id)

            if (!discountType.validate()) {
                message = this.getValidationErrorMessage(discountType)
                return message
            }
            else if (isError){
                message = this.getMessage('Discount Type', Message.ERROR, 'This Discount Type  has already been used')
                return message
            }
            else{
                return discountType
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
            if (result instanceof DiscountType) {
                int noOfRows = (int) discountTypeService.update(result)
                if (noOfRows > 0) {
                    message = this.getMessage("Discount Type", Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage("Discount Type", Message.ERROR, this.FAIL_UPDATE)
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            message= this.getMessage("Discount Type", Message.ERROR, "Exception-${ex.message}")
            return message;
        }

    }
}
