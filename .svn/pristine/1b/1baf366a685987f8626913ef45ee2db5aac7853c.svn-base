package com.bits.bdfp.common.thanaupazilapouroshova

import com.bits.bdfp.common.CountryInfo
import com.bits.bdfp.common.District
import com.bits.bdfp.common.Division
import com.bits.bdfp.common.ThanaUpazilaPouroshova
import com.bits.bdfp.common.ThanaUpazilaPouroshovaService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readThanaUpazilaPouroshovaAction")
class ReadThanaUpazilaPouroshovaAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    ThanaUpazilaPouroshovaService thanaUpazilaPouroshovaService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            Map result = [:]
            ThanaUpazilaPouroshova thanaUpazilaPouroshova = thanaUpazilaPouroshovaService.read(Long.parseLong(params.id))
            District district = District.read(thanaUpazilaPouroshova.district.id)
            Division division = Division.read(district.division.id)
            CountryInfo countryInfo = CountryInfo.read(division.countryInfo.id)

            result.put('thana', thanaUpazilaPouroshova)
            result.put('division', division)
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