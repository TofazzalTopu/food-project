package com.bits.bdfp.common.countryinfo

import com.bits.bdfp.common.CountryInfo
import com.bits.bdfp.common.CountryInfoService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createCountryInfoAction")
class CreateCountryInfoAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CountryInfoService countryInfoService

    public Object preCondition(Object user, Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) user
            CountryInfo countryInfo = (CountryInfo) object
            countryInfo.dateCreated = new Date()
            countryInfo.userCreated = applicationUser
            if (!countryInfo.validate()) {
                return null
            }
            return countryInfo
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object execute(Object params, Object object) {
        try {
            return countryInfoService.create(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}