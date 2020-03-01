package com.bits.bdfp.geolocation.territorysubarea

import com.bits.bdfp.geolocation.TerritorySubAreaService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("listTerritorySubAreaAction")
class ListTerritorySubAreaAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    TerritorySubAreaService territorySubAreaService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
//            int id = Integer.parseInt(params.id)
            Long id = Long.parseLong(params.id)
            init(params)
            Map result = [:]
            List objectList = null
            result = territorySubAreaService.getListForGrid(this,id)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityList(objectList, start, id)
            return objList
        }
        catch (Exception ex) {
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

    private List wrapListInGridEntityList(objList, start, id) {
        List instants = []
        int counter = start + 1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id

            obj.cell = [
                "${instance.id ? instance.id : ''}",
                "${instance.countryInfo ? instance.countryInfo.name : ''}",
                "${instance.countryInfo ? instance.countryInfo.id : ''}",
                "${instance.division ? instance.division.name : ''}",
                "${instance.division ? instance.division.id : ''}",
                "${instance.district ? instance.district.name : ''}",
                "${instance.district ? instance.district.id : ''}",
                "${instance.thanaUpazilaPouroshova ? instance.thanaUpazilaPouroshova.name : ''}",
                "${instance.thanaUpazilaPouroshova ? instance.thanaUpazilaPouroshova.id : ''}",
                "${instance.unionInfo ? instance.unionInfo.name : ''}",
                "${instance.unionInfo ? instance.unionInfo.id : ''}",
                "${instance.road ? instance.road : ''}",
                "${instance.paraOrLocality ? instance.paraOrLocality : ''}",
                "${instance.geoLocation ? instance.geoLocation : ''}",
                "${instance.isActive ? true : false}",
//                        "${instance.isActive ? ApplicationConstants.TICK_MARK_STRING : ApplicationConstants.CROSS_STRING}",
                "${instance.isActive ? 'true' : 'false'}"
            ]
            instants << obj
            counter++
            total = counter - 1
        };
        return instants
    }

    public Object postCondition(Object params, Object object) {
        return getResultForUI(object)
    }
}
