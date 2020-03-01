package com.bits.bdfp.inventory.product.externalproduct

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.product.ExternalProductService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdtofazzal.hossain on 1/16/2019.
 */
@Component("updateExternalProductAction")
class UpdateExternalProductAction extends Action {
    private static final Log log = LogFactory.getLog(this)

    @Autowired
    ExternalProductService externalProductService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

    @Override
    public Object preCondition(Object params, Object object) {
        Boolean isError = false
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            ExternalProduct externalProduct = externalProductService.read(Long.parseLong(params.id))
            externalProduct.properties = params
            externalProduct.userUpdated = applicationUser
            externalProduct.dateUpdated = new Date()

            String domain = 'external_product'
            String id = externalProduct.id
            isError = validationCheckService.validationCheck(domain, id)

            if (!externalProduct.validate()) {
                message = this.getValidationErrorMessage(externalProduct)
                return message
            } else if (isError) {
                message = this.getValidationErrorMessage('ExternalProduct', Message.ERROR, 'This product is already in use.')
                return message
            } else {
                return externalProduct
            }
        } catch (Exception e) {
            log.error(e.message)
            throw new RuntimeException(e.message)
        }
    }

    @Override
    public Object execute(Object params, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object
            Object result = this.preCondition(params, applicationUser)
            if (result instanceof ExternalProduct) {
                int noOfRows = externalProductService.update(result)
                if (noOfRows > 0) {
                    message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage(result, Message.ERROR, this.FAIL_UPDATE)
                }
            }
            return message
        } catch (Exception e) {
            log.error(e.message)
            message = this.getMessage('ExternalProduct', Message.ERROR, "Exception- ${e.message}")
            return message;
        }
    }

    @Override
    public Object postCondition(Object params, Object object) {
        //Not implemented
        return null
    }

}
