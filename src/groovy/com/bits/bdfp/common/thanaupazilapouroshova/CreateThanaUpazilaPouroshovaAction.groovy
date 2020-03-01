package com.bits.bdfp.common.thanaupazilapouroshova

import com.bits.bdfp.common.ThanaUpazilaPouroshova
import com.bits.bdfp.common.ThanaUpazilaPouroshovaService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createThanaUpazilaPouroshovaAction")
class CreateThanaUpazilaPouroshovaAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    ThanaUpazilaPouroshovaService thanaUpazilaPouroshovaService

    public Object preCondition(Object user, Object object) {
        try {
            ApplicationUser applicationUser=(ApplicationUser)user
            ThanaUpazilaPouroshova thanaUpazilaPouroshova = (ThanaUpazilaPouroshova) object
            thanaUpazilaPouroshova.userCreated=applicationUser
            if (!thanaUpazilaPouroshova.validate()) {
                return null
            }
            return thanaUpazilaPouroshova
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object execute(Object params, Object object) {
        try {
            return thanaUpazilaPouroshovaService.create(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}