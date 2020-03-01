package com.bits.bdfp.promotion

import com.bits.bdfp.common.CountryInfo
import com.bits.bdfp.common.CountryInfoService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("updatePromotionInfoAction")
class UpdatePromotionInfoAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CreatePromotionService createPromotionService

    public Object preCondition(Object params, Object object) {
        ApplicationUser applicationUser = (ApplicationUser) object
        Promotion promotion = Promotion.read(Long.parseLong(params?.id?.toString()))
        promotion.properties = params
        promotion.name = params.promotionName
        promotion.userUpdated = applicationUser
        promotion.dateUpdated = new Date()
        if (!promotion.validate()) {
            return null
        }
        return promotion
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return createPromotionService.update(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object preConditionInactive(Object params, Object object) {
        ApplicationUser applicationUser = (ApplicationUser) object
        Promotion promotion = Promotion.read(Long.parseLong(params?.id?.toString()))
        promotion.isActive = false
        promotion.userUpdated = applicationUser
        promotion.dateUpdated = new Date()
        if (!promotion.validate()) {
            return null
        }
        return promotion
    }
    public Object executeInactive(Object params, Object object) {
        try {
            return createPromotionService.updateInactive(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

}
