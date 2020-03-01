package com.bits.bdfp.inventory.sales.distributionpoint

import com.bits.bdfp.inventory.sales.DistributionPointService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("listGeolocationInfoAction")
class ListGeolocationInfoAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    DistributionPointService distributionPointService

    public Object preCondition(Object params, Object object) {
        //Not implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            init(params)
            Map result = [:]
            List objectList = null

            result = distributionPointService.getGeoLocationInfo(params)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityList(objectList, start)
            result = this.postCondition(objList, null)
            return result
        }
        catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }


    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        int counter = start + 1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                        "${instance.id ? instance.id : ''}",
                        "${instance.id ? instance.id : ''}",
                        "${instance.geo_location ? instance.geo_location : ''}",
                        "${instance.country_name ? instance.country_name : ''}",
                        "${instance.division_name ? instance.division_name : ''}",
                        "${instance.district_name ? instance.district_name : ''}",
                        "${instance.thana_name ? instance.thana_name : ''}",
                        "${instance.union_name ? instance.union_name : ''}",
                        "${instance.para_or_locality ? instance.para_or_locality : ''}",
                        "${instance.road ? instance.road : ''}",
                        "${instance.addedd_geo_location_id ? instance.addedd_geo_location_id : ''}"

            ]
            instants << obj
            counter++
        };
        return instants
    }

    public Object postCondition(Object objList, Object object) {
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
}