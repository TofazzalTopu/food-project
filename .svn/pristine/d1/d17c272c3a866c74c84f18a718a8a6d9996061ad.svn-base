package com.bits.bdfp.inventory.product.finishproduct

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.product.FinishProductService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteFinishProductAction")
class DeleteFinishProductAction extends Action {

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    FinishProductService finishProductService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

    public Object preCondition(Object params, Object object) {
//        Boolean isError = false
        try{

            FinishProduct finishProduct = finishProductService.read(Long.parseLong(params?.id?.toString()))


            String domain = 'finish_product'
            String id =  finishProduct.id

//            isError = validationCheckService.validationCheck(domain,id)

            if (!finishProduct.validate()) {
                message = this.getValidationErrorMessage(finishProduct)
                return message
            }
//            else if (isError){
//                message = this.getMessage('FinishProduct', Message.ERROR, 'This Product has already been used')
//                return message
//            }
            else {
                return finishProduct;
            }
        } catch (Exception ex) {
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
            Object result = this.preCondition(params, object)
            if (result instanceof FinishProduct) {
                int noOfRows = (int) finishProductService.delete(result)
                if (noOfRows > 0) {
                    message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_DELETE)
                } else {
                    message = this.getMessage(result, Message.ERROR, this.FAIL_DELETE)
                }
            }
            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            //message = this.getMessage("Product", Message.ERROR, "Exception-${ex.message}")
            message = this.getMessage('FinishProduct', Message.ERROR, 'This Product has already been used')
            return message;
        }
    }

}