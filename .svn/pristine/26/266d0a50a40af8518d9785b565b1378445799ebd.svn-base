package com.bits.bdfp.customer.customersettlement

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.docu.commons.ObjectUtil
import com.bits.bdfp.customer.CustomerSettlement
import com.bits.bdfp.customer.CustomerSettlementService

@Component("readCustomerSettlementAction")
class ReadCustomerSettlementAction extends Action {
    public static final Log log = LogFactory.getLog(ReadCustomerSettlementAction.class)

    @Autowired
    CustomerSettlementService customerSettlementService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        CustomerSettlement customerSettlement = customerSettlementService.read(Long.parseLong(params.id))
        Map objectData = ObjectUtil.getPersistenceData(customerSettlement)
        return objectData
    }

    public Object postCondition(def params, def object) {
        return null
    }
}