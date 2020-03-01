package com.bits.bdfp.common.division

import com.bits.bdfp.common.Division
import com.bits.bdfp.common.DivisionService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updateDivisionAction")
class UpdateDivisionAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    DivisionService divisionService

    public Object preCondition(Object params, Object object) {
        ApplicationUser applicationUser=(ApplicationUser)object
        Division division = Division.read(Long.parseLong(params?.id?.toString()))
        division.properties = params
        division.userUpdated=applicationUser
        if (!division.validate()) {
            return null
        }
        return division
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return divisionService.update(object)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }
}
