package com.bits.bdfp.bonus.onepercentbonus

import com.bits.bdfp.bonus.OnePercentBonusService
import com.docu.common.Action
import com.docu.common.GridEntity
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 3/8/2016.
 */
@Component("listBranchByTerritoryAction")
class ListBranchByTerritoryAction extends Action{
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    OnePercentBonusService onePercentBonusService

    @Override
    public Object preCondition(Object params, Object object) {
        return null
    }

    @Override
    public Object postCondition(Object params, Object object) {
        return getResultForUI(object)
    }

    @Override
    public Object execute(Object params, Object object) {
        try {
            init(params)
            Map result = [:]
            List objectList = null
            if(params.rpt){
                if(params.rpt == '1'){
                    objectList = onePercentBonusService.fetchDefaultBranch(params.userId)
                    return objectList
                }else if(params.rpt == '2'){
                    objectList = onePercentBonusService.listSalesmanByBranch(params)
                    return objectList
                }
            }else {
                objectList = onePercentBonusService.listTotalSaleForPrimary(params)
                return this.wrapListInGridEntityList(objectList, start)
            }
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    private Object wrapListInGridEntityList(objList, start) {
        try {
            List instants = []
            int counter = start + 1;
            objList.each { instance ->
                //if(instance.total_sale) {
                if(instance) {
                    GridEntity obj = new GridEntity()
                    obj.id = instance.cid
                    obj.cell = ["${instance.cid ? instance.cid : ''}",
                                "",
                                "${instance.name ? instance.name : ''}",
                                "${instance.cmCode ? instance.cmCode : ''}",
                                "${instance.total_sale ? instance.total_sale : '0'}",
                                "${instance.percentage ? instance.total_sale * instance.percentage / 100 : '0'}",
                                "${instance.total_receivable ? instance.total_receivable : '0'}"
                    ]
                    instants << obj
                    counter++
                }
            }
            return instants
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    public Map getResultForUI(List objList) {
        Map output = [page: 1, total: 1, records: objList.size(), rows: objList]
        return output;
    }
}
