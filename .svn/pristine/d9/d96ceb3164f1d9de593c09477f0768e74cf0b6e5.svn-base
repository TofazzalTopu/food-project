package com.bits.bdfp.common.countryinfo

import com.bits.bdfp.common.CountryInfo
import com.bits.bdfp.common.CountryInfoService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateCountryInfoAction")
class UpdateCountryInfoAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CountryInfoService countryInfoService

    public Object preCondition(Object params, Object object) {
        ApplicationUser applicationUser = (ApplicationUser) object
        CountryInfo countryInfo = CountryInfo.read(Long.parseLong(params?.id?.toString()))
        countryInfo.properties = params
        countryInfo.userUpdated = applicationUser
        countryInfo.lastUpdated = new Date()
        if (!countryInfo.validate()) {
            return null
        }
        return countryInfo
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return countryInfoService.update(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }
}
