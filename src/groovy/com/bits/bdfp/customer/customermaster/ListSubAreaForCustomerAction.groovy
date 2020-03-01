package com.bits.bdfp.customer.customermaster

import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.geolocation.TerritorySubArea
import com.bits.bdfp.geolocation.TerritorySubAreaService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 10/26/2015.
 */
@Component("listSubAreaForCustomerAction")
class ListSubAreaForCustomerAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    TerritorySubAreaService territorySubAreaService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            init(params)
            List objectList = null
            objectList = territorySubAreaService.fetchTerritorySubArea(params.id)
//            if (result) { // in case: normal list
//                objectList = result.objList
            if(object != null){
                return objectList
            }
            total = objectList.size()
//            String[] ids = params.added.split(',')
//            }
            List objList = this.wrapListInGridEntityList(objectList, start, null)
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
        Map output = [page: 1, total: 1, records: total, rows: objList]
        return output;
    }

    private List wrapListInGridEntityList(objList, start, String[] ids) {
        List instants = []
        int counter = start + 1;
        objList.each { instance ->
//            String selected = "false"
            GridEntity obj = new GridEntity()
            obj.id = instance.id
//            yo: for(int i = 0; i < ids.length; i++){
//                if(ids[i] && instance.id == Integer.parseInt(ids[i])){
//                    ids[i] = ""
//                    selected = "true"
//                    break yo;
//                }
//            }
            obj.cell = [
                    "${instance.id ? instance.id : ''}",
                    "${instance.id ? instance.id : ''}",
                    "${instance.name ? instance.name : ''}",
                    "${instance.geo_location ? instance.geo_location : ''}",
                    "${instance.para_or_locality ? instance.para_or_locality : ''}",
                    "${instance.road ? instance.road : ''}",
                    '<input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" ' +
                            'value="Add" onclick="addTerritoryRecords(' + instance.id + ');"/>'
            ]
            instants << obj
            counter++
        };
//        for (int i = 0; i < ids.length; i++) {
//            if (ids[i]) {
//                GridEntity obj = new GridEntity()
//                obj.id = ids[i]
//                TerritorySubArea territorySubArea = TerritorySubArea.read(Long.parseLong(ids[i]))
//                TerritoryConfiguration territoryConfiguration = TerritoryConfiguration.read(territorySubArea.territoryConfigurationId)
//                obj.cell = [
//                        "${territorySubArea.id ? territorySubArea.id : ''}",
//                        "${territorySubArea.id ? territorySubArea.id : ''}",
//                        "${territoryConfiguration.name ? territoryConfiguration.name : ''}",
//                        "${territorySubArea.geoLocation ? territorySubArea.geoLocation : ''}",
//                        "${territorySubArea.paraOrLocality ? territorySubArea.paraOrLocality : ''}",
//                        "${territorySubArea.road ? territorySubArea.road : ''}",
//                        "true"
//                ]
//                instants << obj
//                counter++
//            }
//        }
//        total = counter - 1
        return instants
    }

    public Object postCondition(Object params, Object object) {
        return getResultForUI(object)
    }
}
