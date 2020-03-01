package com.bits.bdfp.setup.salestarget.dailysalestargetfinishproduct

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.bits.bdfp.setup.salestarget.DailySalesTargetFinishProduct
import com.bits.bdfp.setup.salestarget.DailySalesTargetFinishProductService

@Component("readDailySalesTargetFinishProductAction")
class ReadDailySalesTargetFinishProductAction extends Action {
    public static final Log log = LogFactory.getLog(ReadDailySalesTargetFinishProductAction.class)

    @Autowired
    DailySalesTargetFinishProductService dailySalesTargetFinishProductService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        DailySalesTargetFinishProduct dailySalesTargetFinishProduct = dailySalesTargetFinishProductService.read(Long.parseLong(params.id))
        Map objectData = ObjectUtil.getPersistenceData(dailySalesTargetFinishProduct)
        return objectData
    }

    public Object postCondition(def params, def object) {
        return null
    }
}