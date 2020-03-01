package com.bits.bdfp.inventory.sales.visitplan

import com.bits.bdfp.setup.salestarget.SalesHeadService
import com.bits.bdfp.setup.salestarget.VisitPlan
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.common.Message
import com.docu.commons.ObjectUtil
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 9/29/15.
 */
@Component("searchVisitPlanAction")
class SearchVisitPlanAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    Message message
    @Autowired
    SalesHeadService salesHeadService

    public Object preCondition(Object params, Object object) {
            return null
    }

    public Object execute(Object params, Object object) {
        try {
            init(params)
            Map result = [:]
            List objectList = null

            result = salesHeadService.getListForVisitPlanGrid(params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityList(objectList, start)
            return objList
        }
        catch (Exception ex) {
            log.error(ex.message)
            return  null
        }

    }

    public Object searchList(Object params, Object object) {
        try {
            ApplicationUser applicationUser=(ApplicationUser) object
            return salesHeadService.employeeNameList(params,applicationUser)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }

    }

    public Object popupsearchList(Object params, Object object) {
        try {
            ApplicationUser applicationUser=(ApplicationUser) object
            return salesHeadService.popupemployeeNameList(params,applicationUser)
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }

    }
    Map getResultForUI(List objList) {
        int pageCount = 1
        if (resultPerPage < 0) {
            pageCount = 1
            resultPerPage = total
        } else {
            if (total > resultPerPage) {
                pageCount = Math.ceil(total / resultPerPage)
            }
        }
        Map output = [page: pageNum, total: pageCount, records: total, rows: objList]
        return output;
    }

    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        int counter = start + 1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = [
                        "${instance.id ? instance.id : ''}",
                        "${instance.place_of_visit ? instance.place_of_visit : ''}",
                        "${instance.date_from ? instance.date_from : ''}",
                        "${instance.date_to ? instance.date_to : ''}",
                        "${instance.time_from ? instance.time_from : ''}",
                        "${instance.time_to ? instance.time_to : ''}",
                        "${instance.purpose ? instance.purpose : ''}",
                        "${instance.remarks ? instance.remarks : ''}"
            ]
            instants << obj
            counter++
        };
        return instants
    }
    public Object postCondition(Object params, Object object) {
        return getResultForUI(object)
    }
}