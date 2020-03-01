package com.bits.bdfp.common.thanaupazilapouroshova

import com.bits.bdfp.common.ThanaUpazilaPouroshova
import com.bits.bdfp.common.ThanaUpazilaPouroshovaService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateThanaUpazilaPouroshovaAction")
class UpdateThanaUpazilaPouroshovaAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    ThanaUpazilaPouroshovaService thanaUpazilaPouroshovaService

    public Object preCondition(Object params, Object object) {
        ApplicationUser applicationUser=(ApplicationUser)object
        ThanaUpazilaPouroshova thanaUpazilaPouroshova = ThanaUpazilaPouroshova.read(Long.parseLong(params?.id?.toString()))
        thanaUpazilaPouroshova.properties = params
        thanaUpazilaPouroshova.userUpdated=applicationUser
        if (!thanaUpazilaPouroshova.validate()) {
            return null
        }
        return thanaUpazilaPouroshova
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return thanaUpazilaPouroshovaService.update(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }
}
