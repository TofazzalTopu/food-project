package com.bits.bdfp.inventory.demandorder.writeoff

import com.bits.bdfp.inventory.demandorder.WriteOffService
import com.docu.common.Action
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 3/28/2016.
 */
@Component("listTerritoryForWriteOffAction")
class ListTerritoryForWriteOffAction extends Action{

    @Autowired
    WriteOffService writeOffService

    Message message

    @Override
    public Object preCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    public Object postCondition(Object params, Object object) {
        return null
    }

    @Override
    public Object execute(Object params, Object Object) {
        try {
            List objectList = writeOffService.fetchTerritory(params)
            return objectList
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }
}
