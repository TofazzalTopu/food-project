package com.bits.bdfp.bill.createbill

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.bits.bdfp.bill.CreateBill
import com.bits.bdfp.bill.CreateBillService

@Component("readCreateBillAction")
class ReadCreateBillAction extends Action {
    public static final Log log = LogFactory.getLog(ReadCreateBillAction.class)

    @Autowired
    CreateBillService createBillService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        CreateBill createBill = createBillService.read(Long.parseLong(params.id))
        Map objectData = ObjectUtil.getPersistenceData(createBill)
        return objectData
    }

    public Object postCondition(def params, def object) {
        return null
    }
}