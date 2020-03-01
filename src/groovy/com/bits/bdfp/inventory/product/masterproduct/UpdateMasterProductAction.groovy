package com.bits.bdfp.inventory.product.masterproduct

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.product.MasterProduct
import com.bits.bdfp.inventory.product.MasterProductService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateMasterProductAction")
class UpdateMasterProductAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    MasterProductService masterProductService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

    public Object preCondition(Object object, Object params) {
        try{
            MasterProduct masterProduct = MasterProduct.read(Long.parseLong(params?.id?.toString()))
            ApplicationUser applicationUser = (ApplicationUser) object
            masterProduct.properties = params
            masterProduct.userUpdated = applicationUser

            if (!masterProduct.validate()) {
                message = this.getValidationErrorMessage(masterProduct)
                return message
            }
            return masterProduct
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
            ApplicationUser applicationUser = (ApplicationUser) object

            Object result = this.preCondition(applicationUser, params)
            if (result instanceof MasterProduct) {
                int noOfRows = (int) masterProductService.update(result)
                if (noOfRows > 0) {
                    message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage(result, Message.ERROR, this.FAIL_UPDATE)
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            message= this.getMessage("MasterProduct", Message.ERROR, "Exception-${ex.message}")
            return message;
        }

    }
}
