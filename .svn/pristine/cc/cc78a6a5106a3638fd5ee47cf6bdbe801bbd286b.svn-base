package com.bits.bdfp.inventory.demandorder.adjustusingho

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.bits.bdfp.inventory.demandorder.AdjustUsingHo
import com.bits.bdfp.inventory.demandorder.AdjustUsingHoService

@Component("readAdjustUsingHoAction")
class ReadAdjustUsingHoAction extends Action {
    public static final Log log = LogFactory.getLog(ReadAdjustUsingHoAction.class)

    @Autowired
    AdjustUsingHoService adjustUsingHoService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        AdjustUsingHo adjustUsingHo = adjustUsingHoService.read(Long.parseLong(params.id))
        Map objectData = ObjectUtil.getPersistenceData(adjustUsingHo)
        return objectData
    }

    public Object postCondition(def params, def object) {
        return null
    }
}