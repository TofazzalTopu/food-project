package com.bits.bdfp.inventory.demandorder.secondarydemandorderdetails

import com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetailsService
import com.docu.common.Action
import grails.converters.JSON
import groovy.sql.GroovyRowResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 9/16/2015.
 */
@Component("listDetailsByDemandOrderAction")
class ListDetailsByDemandOrderAction extends Action {

    @Autowired
    SecondaryDemandOrderDetailsService secondaryDemandOrderDetailsService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            Map map = [:]
            List<GroovyRowResult> data = secondaryDemandOrderDetailsService.listDetailsById(params)
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
