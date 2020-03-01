package com.bits.bdfp.inventory.setup.chargetype

import com.bits.bdfp.inventory.setup.ChargeType
import com.bits.bdfp.inventory.setup.ChargeTypeService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createChargeTypeAction")
class CreateChargeTypeAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    ChargeTypeService chargeTypeService

    public Object preCondition(Object user, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) user
            ChargeType chargeType = (ChargeType) object
            chargeType.userCreated = applicationUser
            chargeType.dateCreated = new Date()
            println(chargeType.dateCreated.toString())
            log.error(chargeType);
            if (!chargeType.validate()) {
                return null
            }
            return chargeType
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object execute(Object params, Object object) {
        try {
            return chargeTypeService.create(object)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}