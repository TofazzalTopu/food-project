package com.bits.bdfp.finance.cashreceivedfrombranch

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.bits.bdfp.finance.CashReceivedFromBranch
import com.bits.bdfp.finance.CashReceivedFromBranchService

@Component("readCashReceivedFromBranchAction")
class ReadCashReceivedFromBranchAction extends Action {
    public static final Log log = LogFactory.getLog(ReadCashReceivedFromBranchAction.class)

    @Autowired
    CashReceivedFromBranchService cashReceivedFromBranchService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        CashReceivedFromBranch cashReceivedFromBranch = cashReceivedFromBranchService.read(Long.parseLong(params.id))
        Map objectData = ObjectUtil.getPersistenceData(cashReceivedFromBranch)
        return objectData
    }

    public Object postCondition(def params, def object) {
        return null
    }
}