package com.bits.bdfp.inventory.product.finishproduct

import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.product.FinishProductService
import com.bits.bdfp.settings.MeasureUnitConfiguration
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createFinishProductAction")
class CreateFinishProductAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    FinishProductService finishProductService
    Message message

    public Object preCondition(Object params, Object object) {
        try {
            FinishProduct finishProduct = (FinishProduct) object
            if (!finishProduct.validate()) {
                message = this.getValidationErrorMessage(finishProduct)
            } else {
                message = this.getMessage(finishProduct, Message.SUCCESS, this.SUCCESS_SAVE)
            }

            FinishProduct sequenceNumber = FinishProduct.findBySequenceNumber(params.sequenceNumber)
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object execute(Object params, Object object) {
        try {

            ApplicationUser applicationUser = (ApplicationUser) object
            FinishProduct finishProduct = new FinishProduct(params)
            finishProduct.dateCreated = new Date()
            finishProduct.userCreated = applicationUser


            if(params.qtyInLtr == ''){
                finishProduct.qtyInLtr = 0.0
            }

            finishProduct.code = CodeGenerationUtil.instance.getCode(finishProduct.enterpriseConfiguration, "PRODUCT_CODE", finishProduct?.enterpriseConfiguration?.code, finishProduct?.businessUnitConfiguration?.code,
                    finishProduct?.productCategory?.code, finishProduct?.masterProduct?.code, finishProduct?.mainProduct?.code, finishProduct?.productType?.code, null, null, null, null)
            message = this.preCondition(params, finishProduct)


            if (message.type == 1) {
                finishProduct = finishProductService.create(finishProduct)
                if (finishProduct) {
                    message = this.getMessage(finishProduct, Message.SUCCESS, this.SUCCESS_SAVE)
                } else {
                    message = this.getMessage(finishProduct, Message.ERROR, this.FAIL_SAVE)
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}