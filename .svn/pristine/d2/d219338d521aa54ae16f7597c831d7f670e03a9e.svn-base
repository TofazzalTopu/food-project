package com.bits.bdfp.inventory.product.masterproduct

import com.bits.bdfp.inventory.product.MasterProduct
import com.bits.bdfp.inventory.product.MasterProductService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createMasterProductAction")
class CreateMasterProductAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    MasterProductService masterProductService

    public Object preCondition(Object user, Object object) {
        try {
            MasterProduct masterProduct = (MasterProduct) object
            ApplicationUser applicationUser = (ApplicationUser) user
            masterProduct.isActive = true
            masterProduct.userCreated = applicationUser;
            masterProduct.dateCreated = new Date()
            if (!masterProduct.validate()) {
                return null
            }
            return masterProduct
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object execute(Object params, Object object) {
        try {
            return masterProductService.create(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}