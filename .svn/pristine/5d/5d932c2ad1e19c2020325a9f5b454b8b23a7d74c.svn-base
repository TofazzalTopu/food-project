package com.bits.bdfp.inventory.setup.quotation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.bits.bdfp.inventory.setup.Quotation
import com.bits.bdfp.inventory.setup.QuotationService

@Component("readQuotationAction")
class ReadQuotationAction extends Action {
    public static final Log log = LogFactory.getLog(ReadQuotationAction.class)

    @Autowired
    QuotationService quotationService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        Quotation quotation = quotationService.read(Long.parseLong(params.id))
        Map objectData = ObjectUtil.getPersistenceData(quotation)
        return objectData
    }

    public Object postCondition(def params, def object) {
        return null
    }
}