package com.bits.bdfp.inventory.product.packagetype

import com.bits.bdfp.inventory.product.PackageType
import com.bits.bdfp.inventory.product.PackageTypeService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createPackageTypeAction")
class CreatePackageTypeAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    PackageTypeService packageTypeService

    public Object preCondition(Object user, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) user
            PackageType packageType = (PackageType) object
            packageType.userCreated = applicationUser
            packageType.dateCreated = new Date()
            if (!packageType.validate()) {
                return null
            }
            return packageType
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object execute(Object params, Object object) {
        try {
            return packageTypeService.create(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}