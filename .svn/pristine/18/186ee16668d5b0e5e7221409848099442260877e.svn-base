package com.bits.bdfp.inventory.product.productpackage

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.product.ProductPackage
import com.bits.bdfp.inventory.product.ProductPackageService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateProductPackageAction")
class UpdateProductPackageAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    ProductPackageService productPackageService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

    public Object preCondition(Object params, Object object) {
        Boolean isError = false
        try{
            
        ProductPackage productPackage = ProductPackage.read(Long.parseLong(params?.id?.toString()))
        productPackage.properties = params

            String domain = 'product_package'
            String id =  productPackage.id

            isError = validationCheckService.validationCheck(domain,id)

        if (!productPackage.validate()) {
            message = this.getValidationErrorMessage(productPackage)
            return message
        }
        else if (isError){
            message = this.getMessage('ProductPackage', Message.ERROR, 'This Product Package has already been used')
            return message
        }
        else{
            return productPackage
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
            Object result = this.preCondition(params, null)
            if (result instanceof ProductPackage) {
                int noOfRows = (int) productPackageService.update(result)
                if (noOfRows > 0) {
                    message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage(result, Message.ERROR, this.FAIL_UPDATE)
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            message= this.getMessage("ProductPackage", Message.ERROR, "Exception-${ex.message}")
            return message;
        }

    }
}
