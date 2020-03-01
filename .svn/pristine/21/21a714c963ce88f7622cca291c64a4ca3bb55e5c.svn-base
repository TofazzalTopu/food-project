package com.bits.bdfp.settings.businessday

import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("readHolidayAction")
class ReadHolidayAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    BusinessHolidayService businessHolidayService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return businessHolidayService.read(Long.parseLong(params.id))
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