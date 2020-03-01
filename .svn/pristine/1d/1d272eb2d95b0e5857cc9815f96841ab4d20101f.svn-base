package com.bits.bdfp.inventory.sales.distributionpoint

import com.bits.bdfp.inventory.sales.DistributionPointService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Action
import com.docu.common.GridEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("listDistributionPointAction")
class ListDistributionPointAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    DistributionPointService distributionPointService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            init(params)
            Map result = [:]
            List objectList = null

            result = distributionPointService.getListForGrid(this)
            if (result) { // in case: normal list
                objectList = result.objList
                total = result.count
            }
            List objList = this.wrapListInGridEntityList(objectList, start)
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

    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        int counter = start + 1;
        EnterpriseConfiguration enterpriseConfiguration
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            if(instance.enterpriseConfiguration){
                 enterpriseConfiguration=instance.enterpriseConfiguration
            }

            obj.cell = ["${counter}",
                        "${instance.id ? instance.id : ''}",

                        "${enterpriseConfiguration ? enterpriseConfiguration.name : ''}",
                        "${instance.name ? instance.name : ''}",
                        "${instance.isFactory? ApplicationConstants.TICK_MARK_STRING : ApplicationConstants.CROSS_STRING}",
                        "${instance.code ? instance.code : ''}",
                        "${instance.address ? instance.address : ''}",
                        "${instance.email ? instance.email : ''}",
                        "${instance.mobile ? instance.mobile : ''}",
                        "${instance.userCreated ? instance.userCreated : ''}",
                        "${instance.userUpdated ? instance.userUpdated : ''}",
                        "${instance.dateCreated ? instance.dateCreated : ''}",
                        "${instance.dateUpdated ? instance.dateUpdated : ''}"
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
