package com.bits.bdfp.common.district

import com.bits.bdfp.common.District
import com.bits.bdfp.common.DistrictService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateDistrictAction")
class UpdateDistrictAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    DistrictService districtService

    public Object preCondition(Object params, Object object) {
        ApplicationUser applicationUser = (ApplicationUser) object
        District district = District.read(Long.parseLong(params?.id?.toString()))
        district.properties = params
        district.userUpdated = applicationUser
        if (!district.validate()) {
            return null
        }
        return district
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return districtService.update(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }
}
