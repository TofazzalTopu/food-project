package com.bits.bdfp.inventory.product.productcategory

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.product.ProductCategory
import com.bits.bdfp.inventory.product.ProductCategoryService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateProductCategoryAction")
class UpdateProductCategoryAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    ProductCategoryService productCategoryService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

    public Object preCondition(Object object, Object params) {
        Boolean isError = false
        try{

        ProductCategory productCategory = ProductCategory.read(Long.parseLong(params?.id?.toString()))
        ApplicationUser applicationUser = (ApplicationUser) object
        productCategory.properties = params
        productCategory.userUpdated = applicationUser


            String domain = 'product_category'
            String id =  productCategory.id

            isError = validationCheckService.validationCheck(domain,id)

            if (!productCategory.validate()) {
                message = this.getValidationErrorMessage(productCategory)
                return message
            }
            else if (isError){
                message = this.getMessage('Product Category', Message.ERROR, 'This Product Category has already been used')
                return message
            }
            else{
                return productCategory
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
            if (result instanceof ProductCategory) {
                int noOfRows = (int) productCategoryService.update(result)
                if (noOfRows > 0) {
                    message = this.getMessage("Product Category", Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage("Product Category", Message.ERROR, this.FAIL_UPDATE)
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            message= this.getMessage("Product Category", Message.ERROR, "Exception-${ex.message}")
            return message;
        }

    }


}
