package com.bits.bdfp.inventory.demandorder.writeoff

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.bits.bdfp.inventory.demandorder.WriteOff
import com.bits.bdfp.inventory.demandorder.WriteOffService

@Component("readWriteOffAction")
class ReadWriteOffAction extends Action {
    public static final Log log = LogFactory.getLog(ReadWriteOffAction.class)

    @Autowired
    WriteOffService writeOffService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        WriteOff writeOff = writeOffService.read(Long.parseLong(params.id))
        Map objectData = ObjectUtil.getPersistenceData(writeOff)
        return objectData
    }

    public Object postCondition(def params, def object) {
        return null
    }
}