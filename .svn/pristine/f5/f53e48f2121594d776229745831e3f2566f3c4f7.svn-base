package com.bits.bdfp.geolocation.territorysubarea

import com.bits.bdfp.geolocation.TerritorySubAreaService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 9/10/15.
 */
@Component("listTerritorySubAreaByTerritoryAction")
class ListTerritorySubAreaByTerritoryAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    TerritorySubAreaService territorySubAreaService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            List result = territorySubAreaService.listTerritorySubAreaByTerritory(params)
            List objList = this.wrapListInGridEntityList(result, start)
            Map output = [page: 1, total: result.size(), records: result.size(), rows: objList]
            return output;
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

    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        int counter = start + 1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id

            obj.cell = [
                    "${instance.id ? instance.id : ''}",
                    "${instance.geoLocation ? instance.geoLocation : ''}",
                    "${instance.country ? instance.country : ''}",
                    "${instance.division ? instance.division : ''}",
                    "${instance.district ? instance.district : ''}",
                    "${instance.thana ? instance.thana : ''}",
                    "${instance.union ? instance.union : ''}",
                    "${instance.paraOrLocality ? instance.paraOrLocality : ''}",
                    "${instance.road ? instance.road : ''}"
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
