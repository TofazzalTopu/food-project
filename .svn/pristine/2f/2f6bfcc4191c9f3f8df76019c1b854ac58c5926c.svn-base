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
 * Created by NZ on 3/2/2016.
 */
@Component("listTerritoryAction")
class ListTerritoryAction extends Action {

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
        try{
            init(params)
            Map result = [:]
            List objectList = null

            objectList = onePercentBonusService.listTerritory(params)
            return this.wrapListInGridEntityList(objectList, start)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    private Object wrapListInGridEntityList(objList, start) {
        try{
            List instants = []
            int counter = start + 1;
            objList.each { instance ->
                GridEntity obj = new GridEntity()
                obj.id = instance.id
                obj.cell = ["${instance.id ? instance.id : ''}",
                            "",
                            "${instance.name? instance.name : ''}"
                ]
                instants << obj
                counter++
            }
            return instants
        } catch (Exception ex){
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }

    Map getResultForUI(List objList) {
        int pageCount = 1
        if (total > resultPerPage) {
            pageCount = Math.ceil(total / resultPerPage)
        }
        Map output = [page: pageNum, total: pageCount, records: total, rows: objList]
        return output;
    }
}
