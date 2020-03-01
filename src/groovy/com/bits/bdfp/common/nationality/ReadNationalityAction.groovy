package com.bits.bdfp.common.nationality

import com.bits.bdfp.common.NationalityService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("readNationalityAction")
class ReadNationalityAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    NationalityService nationalityService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return nationalityService.read(Long.parseLong(params.id))
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