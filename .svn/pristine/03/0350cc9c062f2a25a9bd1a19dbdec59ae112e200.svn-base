package com.bits.bdfp.inventory.setup.salescommission

import com.bits.bdfp.inventory.setup.SalesCommission
import com.bits.bdfp.inventory.setup.SalesCommissionService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("listSalesCommissionAction")
class ListSalesCommissionAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    SalesCommissionService salesCommissionService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }
//
//    public Object execute(Object params, Object object) {
//        try {
//            init(params)
//            Map result = [:]
//            List objectList = null
//
//            result = salesCommissionService.getListForGrid(this)
//            if (result) { // in case: normal list
//                objectList = result.objList
//                total = result.count
//            }
//            List objList = this.wrapListInGridEntityList(objectList, start)
//            return objList
//        }
//        catch (Exception ex) {
//            log.error(ex.message)
//            return null
//        }
//    }


    public Object execute(Object params, Object object) {
        try {
            List result = salesCommissionService.listDefaultCustomerByDP(Long.parseLong(params.id))
//            List objList = this.wrapListInGridEntityList(result, start)
//            Map output = [page: 1, total: result.size(), records: result.size(), rows: objList]
            return result;
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
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
            obj.cell = ["${counter}",
                        "${instance.id ? instance.id : ''}",

                        "${instance.countryInfo ? instance.geoCode : ''}",
                        "${instance.name ? instance.name : ''}",
                        "${instance.userCreated ? instance.userCreated : ''}",
                        "${instance.userUpdated ? instance.userUpdated : ''}",
                        "${instance.countryInfo ? instance.countryInfo : ''}",
                        "${instance.dateCreated ? instance.dateCreated : ''}",
                        "${instance.lastUpdated ? instance.lastUpdated : ''}"
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
