package com.bits.bdfp.common.district

import com.bits.bdfp.common.CountryInfo
import com.bits.bdfp.common.District
import com.bits.bdfp.common.DistrictService
import com.bits.bdfp.common.Division
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readDistrictAction")
class ReadDistrictAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    DistrictService districtService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            Map result = [:]
            District district = districtService.read(Long.parseLong(params.id))
            Division division = Division.read(district.division.id)
            CountryInfo countryInfo = CountryInfo.read(division.countryInfo.id)

            result.put('district', district)
            result.put('country', countryInfo)

            return result
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }
}