package com.bits.bdfp.inventory.product.externalproductstock

import com.bits.bdfp.inventory.product.ExternalProductStockService
import com.bits.bdfp.inventory.product.externalproduct.ExternalProductStock
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
@Component('createExternalProductStockAction')
class CreateExternalProductStockAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    private static final ALREADY_EXIST = 'Selected product already exist.'
    @Autowired
    ExternalProductStockService externalProductStockService
    Message message;
    public Object preCondition(Object user, Object object) {
        try {
            ExternalProductStock existProductStock = ExternalProductStock.findByExternalProduct(object.externalProduct)
            if(existProductStock){
                message = this.getMessage(existProductStock, Message.ERROR, ALREADY_EXIST)
                return message;
            }
            ApplicationUser applicationUser = (ApplicationUser) user
            ExternalProductStock externalProductStock = (ExternalProductStock) object
            externalProductStock.userCreated = applicationUser
            externalProductStock.outQuantity = 0.0
            externalProductStock.dateCreated = new Date()
            if (!externalProductStock.validate()) {
                return null
            }
            return externalProductStock
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object execute(Object params, Object object) {
        try {
            return externalProductStockService.create(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}