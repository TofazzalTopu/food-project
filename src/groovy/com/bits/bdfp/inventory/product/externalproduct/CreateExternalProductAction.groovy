package com.bits.bdfp.inventory.product.externalproduct

import com.bits.bdfp.inventory.product.ExternalProductService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdtofazzal.hossain on 1/15/2019.
 */

@Component("createExternalProductAction")
class CreateExternalProductAction extends Action{

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    ExternalProductService externalProductService

    public Object preCondition(Object user, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) user
            ExternalProduct externalProduct = (ExternalProduct) object
            externalProduct.userCreated = applicationUser
            externalProduct.dateCreated = new Date()
            if (!externalProduct.validate()) {
                return null
            }
            return externalProduct
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object execute(Object params, Object object) {
        try {
            return externalProductService.create(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}
