package com.bits.bdfp.inventory.sales.marketreturn

import com.bits.bdfp.inventory.sales.MarketReturnService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 1/10/2016.
 */
@Component("fetchCustomerByGeoLocationAction")
class FetchCustomerByGeoLocationAction extends Action {

    @Autowired
    MarketReturnService marketReturnService

    @Override
    protected Object preCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    protected Object postCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    public Object execute(Object params, Object object) {
        ApplicationUser applicationUser = (ApplicationUser) object
        params.put('appUser', applicationUser.id)
        params.put('appCustomer', applicationUser.customerMasterId)
        return marketReturnService.fetchCustomerByGeo(params);
    }
}
