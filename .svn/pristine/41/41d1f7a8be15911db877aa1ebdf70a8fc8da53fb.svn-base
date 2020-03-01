package com.bits.bdfp.common.thanaupazilapouroshova

import com.bits.bdfp.common.ThanaUpazilaPouroshovaService
import com.docu.common.Action
import com.docu.common.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("deleteThanaUpazilaPouroshovaAction")
class DeleteThanaUpazilaPouroshovaAction extends Action {

    private static final Log log = LogFactory.getLog(this)
    @Autowired
    ThanaUpazilaPouroshovaService thanaUpazilaPouroshovaService

    public Object preCondition(Object params, Object object) {
        try {
            return thanaUpazilaPouroshovaService.read(Long.parseLong(params.id.toString()))
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            return thanaUpazilaPouroshovaService.delete(object)
        }
        catch (Exception ex) {
            log.error(ex.message)
            return new Integer(0)
        }
    }

}