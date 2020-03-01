package com.bits.bdfp.setup.salestarget.monthlysalestargetbyamount

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.bits.bdfp.setup.salestarget.MonthlySalesTargetByAmount
import com.bits.bdfp.setup.salestarget.MonthlySalesTargetByAmountService

@Component("readMonthlySalesTargetByAmountAction")
class ReadMonthlySalesTargetByAmountAction extends Action {
    public static final Log log = LogFactory.getLog(ReadMonthlySalesTargetByAmountAction.class)

    @Autowired
    MonthlySalesTargetByAmountService monthlySalesTargetByAmountService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        MonthlySalesTargetByAmount monthlySalesTargetByAmount = monthlySalesTargetByAmountService.read(Long.parseLong(params.id))
        Map objectData = ObjectUtil.getPersistenceData(monthlySalesTargetByAmount)
        return objectData
    }

    public Object postCondition(def params, def object) {
        return null
    }
}