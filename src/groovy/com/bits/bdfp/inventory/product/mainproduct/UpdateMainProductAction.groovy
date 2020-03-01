package com.bits.bdfp.inventory.product.mainproduct

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.product.MainProduct
import com.bits.bdfp.inventory.product.MainProductService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateMainProductAction")
class UpdateMainProductAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    MainProductService mainProductService
    @Autowired
    ValidationCheckService validationCheckService
    Message message
    public Object preCondition(Object params, Object object) {
        try{
            ApplicationUser applicationUser = (ApplicationUser) object
            MainProduct mainProduct = MainProduct.read(Long.parseLong(params?.id?.toString()))
            mainProduct.properties = params
            mainProduct.userUpdated = applicationUser

            if (!mainProduct.validate()) {
                message = this.getValidationErrorMessage(mainProduct)
                return message
            }
            return mainProduct
        }
        catch (Exception ex) {
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
            Object result = this.preCondition( params,null)
            if (result instanceof MainProduct) {
                int noOfRows = (int) mainProductService.update(result)
                if (noOfRows > 0) {
                    message = this.getMessage(result, Message.SUCCESS, "Main Product updated successfully")
                } else {
                    message = this.getMessage(result, Message.ERROR, "Main Product not update")
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            message= this.getMessage("MainProduct", Message.ERROR, "Exception-${ex.message}")
            return message;
        }

    }
}
