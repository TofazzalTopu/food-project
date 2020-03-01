package com.bits.bdfp.inventory.product.producttype

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.product.ProductPricingType
import com.bits.bdfp.inventory.product.ProductType
import com.bits.bdfp.inventory.product.ProductTypeService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateProductTypeAction")
class UpdateProductTypeAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    ProductTypeService productTypeService
    @Autowired
    ValidationCheckService validationCheckService
    Message message
    public Object preCondition(Object object, Object params) {
        Boolean isError = false
        try{
            Map map = [:]
        ApplicationUser applicationUser = (ApplicationUser) object
        ProductType productType = ProductType.read(Long.parseLong(params?.id?.toString()))
        productType.properties = params
        productType.userUpdated = applicationUser
        productType.dateUpdated = new Date()
            map.put('domain', 'product_type')
            map.put('id', productType.id)
            map.put('field', 'product_type')
//            isError = validationCheckService.validationCheck(map)

            if (!productType.validate()) {
                message = this.getValidationErrorMessage(productType)
                return message
            }
//            else if (isError){
//                message = this.getMessage('ProductType', Message.ERROR, 'This Product Type has already been used')
//                return message
//            }
            else{
                return productType
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
            if (result instanceof ProductType) {
                int noOfRows = (int) productTypeService.update(result)
                if (noOfRows > 0) {
                    message = this.getMessage("Product Type", Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage("Product Type", Message.ERROR, this.FAIL_UPDATE)
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            message= this.getMessage("ProductType", Message.ERROR, "Exception-${ex.message}")
            return message;
        }


    }
}
