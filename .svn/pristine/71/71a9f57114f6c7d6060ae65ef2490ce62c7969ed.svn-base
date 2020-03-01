package com.bits.bdfp.geolocation.territoryconfiguration

import com.bits.bdfp.geolocation.TerritoryConfigurationService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 10/25/2015.
 */
@Component("fetchListForDropDownAction")
class FetchListForDropDownAction extends Action {

    @Autowired
    TerritoryConfigurationService territoryConfigurationService

    @Override
    protected Object preCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    protected Object postCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    protected Object execute(Object params, Object object2) {
        try {
            List list
            if (params.sl == '1') {
                list = territoryConfigurationService.fetchDivisionList(params.id)
            } else if (params.sl == '2') {
                list = territoryConfigurationService.fetchDistrictList(params.id)
            } else if (params.sl == '3') {
                list = territoryConfigurationService.fetchThanaList(params.id)
            } else if (params.sl == '4') {
                list = territoryConfigurationService.fetchUnionList(params.id)
            }
            return list
        }catch(Exception e){

        }
    }
}
