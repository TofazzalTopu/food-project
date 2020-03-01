package com.bits.bdfp.common.division

import com.bits.bdfp.common.Division
import com.bits.bdfp.common.DivisionService
import com.docu.common.Action
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("createDivisionAction")
class CreateDivisionAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    DivisionService divisionService

    public Object preCondition(Object user, Object object) {
        try {
            ApplicationUser applicationUser=(ApplicationUser)user
            Division division = (Division) object
            division.userCreated=applicationUser
            if (!division.validate()) {
                return null
            }
            return division
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object execute(Object params, Object object) {
        try {
            return divisionService.create(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}