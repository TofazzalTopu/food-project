package com.bits.bdfp.setup.salestarget.saleshead

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.bits.bdfp.setup.salestarget.SalesHead
import com.bits.bdfp.setup.salestarget.SalesHeadService

@Component("readSalesHeadAction")
class ReadSalesHeadAction extends Action {
    public static final Log log = LogFactory.getLog(ReadSalesHeadAction.class)

    @Autowired
    SalesHeadService salesHeadService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        SalesHead salesHead = salesHeadService.read(Long.parseLong(params.id))
//        Map objectData = ObjectUtil.getPersistenceData(salesHead)
        return salesHead
    }

    public Object postCondition(def params, def object) {
        return null
    }
}