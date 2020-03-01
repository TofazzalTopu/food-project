package com.bits.bdfp.customer.employee

import com.bits.bdfp.customer.CustomerMasterService
import com.docu.common.Action
import com.docu.common.GridEntity
import grails.converters.JSON
import groovy.sql.GroovyRowResult
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 9/8/15.
 */
@Component("listEmployeeAction")
class ListEmployeeAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerMasterService customerMasterService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            Map map = [:]
            List<GroovyRowResult> data= customerMasterService.getEmployeeList()
            map.put("aaData", data)
            return map as JSON
        }
        catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object postCondition(Object objList, Object object) {
        return null
    }
}
