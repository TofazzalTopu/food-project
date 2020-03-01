package com.bits.bdfp.inventory.product.finishproduct

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.product.FinishProductService
import com.bits.bdfp.settings.MeasureUnitConfiguration
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateFinishProductAction")
class UpdateFinishProductAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    FinishProductService finishProductService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

    public Object preCondition(Object params, Object object) {
        Boolean isError = false
        try {

            ApplicationUser applicationUser = (ApplicationUser) object
            FinishProduct finishProduct = finishProductService.read(Long.parseLong(params?.id?.toString()))
            finishProduct.properties = params
            finishProduct.dateUpdated = new Date()
            finishProduct.userUpdated = applicationUser


            MeasureUnitConfiguration muc= MeasureUnitConfiguration.findById(Long.parseLong(params.measureUnitConfiguration.id))
            if(muc.name=="ml"){
                finishProduct.qtyInLtr = (Integer.parseInt(params.packSize))/1000
            } else{
                finishProduct.qtyInLtr = 0.0
            }

            /*
            String domain = 'finish_product'
            String id =  finishProduct.id

            isError = validationCheckService.validationCheck(domain,id)
            */
            if (!finishProduct.validate()) {
                message = this.getValidationErrorMessage(finishProduct)
                return message
            }

//            else if (isError){
//                message = this.getMessage('FinishProduct', Message.ERROR, 'This Product has already been used')
//                return message
//            }
            return finishProduct
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {

            ApplicationUser applicationUser = (ApplicationUser) object

            Object result = this.preCondition(params, applicationUser)
            if (result instanceof FinishProduct) {
                int noOfRows = (int) finishProductService.update(result)
                if (noOfRows > 0) {
                    message = this.getMessage("Product Setup", Message.SUCCESS, "Product Setup updated Successfully")
                } else {
                    message = this.getMessage(result, Message.ERROR, this.FAIL_UPDATE)
                }
            } else {
                message = result
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
