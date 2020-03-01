package com.bits.bdfp.customer.customerterritorysubarea

import com.bits.bdfp.customer.CustomerTerritorySubAreaService
import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.geolocation.TerritorySubArea
import com.docu.common.Action
import com.docu.common.GridEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("listCustomerTerritorySubAreaAction")
class ListCustomerTerritorySubAreaAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    CustomerTerritorySubAreaService customerTerritorySubAreaService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            init(params)
            Map result = [:]
            List objectList = null

            result = customerTerritorySubAreaService.getListForGrid(this, params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityList(objectList, start)
            return objList
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
            TerritorySubArea territorySubArea = TerritorySubArea.read(instance.territorySubArea.id)
            TerritoryConfiguration territoryConfiguration = TerritoryConfiguration.read(territorySubArea.territoryConfigurationId)
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${instance.id ? instance.id : ''}",
                        "${territorySubArea.id ? territorySubArea.id : ''}",
                        "${territoryConfiguration.name ? territoryConfiguration.name : ''}",
                        "${territorySubArea.geoLocation ? territorySubArea.geoLocation : ''}",
                        "${territorySubArea.paraOrLocality ? territorySubArea.paraOrLocality : ''}",
                        "${territorySubArea.road ? territorySubArea.road : ''}",
                        '<a  href=\"javascript:deleteTerritoryRecords(' + instance.id + ')\" class=\"ui-icon ui-icon-trash\" title=\"Delete\"></a>'
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
