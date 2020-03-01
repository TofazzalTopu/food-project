package com.bits.bdfp.bonus.onepercentbonus

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.bits.bdfp.bonus.OnePercentBonus
import com.bits.bdfp.bonus.OnePercentBonusService

@Component("readOnePercentBonusAction")
class ReadOnePercentBonusAction extends Action {
    public static final Log log = LogFactory.getLog(ReadOnePercentBonusAction.class)

    @Autowired
    OnePercentBonusService onePercentBonusService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        OnePercentBonus onePercentBonus = onePercentBonusService.read(Long.parseLong(params.id))
        Map objectData = ObjectUtil.getPersistenceData(onePercentBonus)
        return objectData
    }

    public Object postCondition(def params, def object) {
        return null
    }
}