package com.bits.bdfp.promotion

import com.bits.bdfp.common.CountryInfoService
import com.docu.common.Action
import groovy.sql.GroovyRowResult
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("readPromotionInfoAction")
class ReadPromotionInfoAction extends Action {

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CreatePromotionService createPromotionService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return createPromotionService.read(Long.parseLong(params.id))
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }
   /* public GroovyRowResult execute(Object params, Object object) {
        try {
            return createPromotionService.read(Long.parseLong(params.id))
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }*/

    public Object postCondition(Object params, Object object) {
        //Not implement
        return null
    }
}