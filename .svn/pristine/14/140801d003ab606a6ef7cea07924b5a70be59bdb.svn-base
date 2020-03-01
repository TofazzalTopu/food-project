package com.bits.bdfp.common.district

import com.bits.bdfp.common.District
import com.bits.bdfp.common.DistrictService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createDistrictAction")
class CreateDistrictAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    DistrictService districtService

    public Object preCondition(Object user, Object object) {
        try {
            ApplicationUser applicationUser=(ApplicationUser)user
            District district = (District) object
            district.userCreated=applicationUser
            if (!district.validate()) {
                return null
            }
            return district
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object execute(Object params, Object object) {
        try {
            return districtService.create(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}