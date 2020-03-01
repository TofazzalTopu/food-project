package com.bits.bdfp.inventory.warehouse.registerfinishgood

import com.bits.bdfp.inventory.warehouse.RegisterFinishGoodService
import com.docu.common.Action
import com.docu.common.GridEntity
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 11/28/15.
 */
@Component("listReceivableOrderDetailsAction")
class ListReceivableOrderDetailsAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    RegisterFinishGoodService registerFinishGoodService

    public Object preCondition(Object params, Object object) {
        //not need to implement
        return null
    }

    public Object execute(Object params, Object object) {
        try{
            Map result = [:]
            List objectList = null

            objectList = registerFinishGoodService.listReceivableOrderDetails(params)
            List objList = this.wrapListInGridEntityList(objectList, 0)
            return [page: 1, total: 1, records: objectList.size(), rows: objList]
        }
        catch (Exception ex) {
            log.error(ex.message)
            return  null
        }
    }

    private List wrapListInGridEntityList(objList, start) {
        List instants = []
        int counter = start + 1;
        objList.each { instance ->
            GridEntity obj = new GridEntity()
            obj.id = instance.id
            obj.cell = ["${counter}",
                    "${instance.item? instance.item : ''}",
                    "${instance.quantity? instance.quantity : ''}",
                    "${instance.amount? instance.amount : ''}"
            ]
            instants << obj
            counter++
        };
        return instants
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}
