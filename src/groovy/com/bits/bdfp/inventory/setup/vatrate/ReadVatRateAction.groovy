package com.bits.bdfp.inventory.setup.vatrate

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.bits.bdfp.inventory.setup.VatRate
import com.bits.bdfp.inventory.setup.VatRateService

@Component("readVatRateAction")
class ReadVatRateAction extends Action {
    public static final Log log = LogFactory.getLog(ReadVatRateAction.class)

    @Autowired
    VatRateService vatRateService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        VatRate vatRate = vatRateService.read(Long.parseLong(params.id))
//        Map objectData = ObjectUtil.getPersistenceData(vatRate)
        return vatRate
    }

    public Object postCondition(def params, def object) {
        return null
    }
}