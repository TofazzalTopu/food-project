package com.bits.bdfp.common.nationality

import com.bits.bdfp.common.Nationality
import com.bits.bdfp.common.NationalityService
import com.docu.common.Action
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createNationalityAction")
class CreateNationalityAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    NationalityService nationalityService

    public Object preCondition(Object params, Object object) {
        try {
            Nationality nationality = (Nationality) object
            if (!nationality.validate()) {
                return null
            }
            return nationality
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object execute(Object params, Object object) {
        try {
            return nationalityService.create(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}