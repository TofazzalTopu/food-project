package com.bits.bdfp.inventory.product.producttype

import com.bits.bdfp.inventory.product.ProductType
import com.bits.bdfp.inventory.product.ProductTypeService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createProductTypeAction")
class CreateProductTypeAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    ProductTypeService productTypeService

    public Object preCondition(Object user, Object object) {
        try {
            ProductType productType = (ProductType) object
            ApplicationUser applicationUser = (ApplicationUser) user
            productType.userCreated = applicationUser
            productType.dateCreated = new Date()
            if (!productType.validate()) {
                return null
            }
            return productType
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object execute(Object params, Object object) {
        try {
            return productTypeService.create(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}