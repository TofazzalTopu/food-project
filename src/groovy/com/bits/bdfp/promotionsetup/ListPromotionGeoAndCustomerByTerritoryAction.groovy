package com.bits.bdfp.promotionsetup

import com.bits.bdfp.inventory.setup.IncentiveSetupService
import com.docu.common.Action
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 7/31/16.
 */
@Component("listPromotionGeoAndCustomerByTerritoryAction")
class ListPromotionGeoAndCustomerByTerritoryAction extends Action {
    private static final Log log = LogFactory.getLog(this)

    @Autowired
    PromotionSetupService promotionSetupService
    Message message

    public Object preCondition(Object params, Object object) {
        return null
    }

    public Object execute(Object params, Object user) {
        return promotionSetupService.getGeoAndCustomersListByTerritory(params)
    }

    public Object postCondition(Object params, Object object) {
        return  null
    }
}
