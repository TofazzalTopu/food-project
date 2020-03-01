package com.bits.bdfp.inventory.demandorder.primarydemandorder

import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdtofazzal.hossain on 7/30/2018.
 */
@Component("getAuthorizedEmployeeInfoAction")
class GetAuthorizedEmployeeInfoAction extends Action{
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    PrimaryDemandOrderService primaryDemandOrderService


    @Override
    protected Object preCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    protected Object postCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    protected Object execute(def Object object1,def Object object2) {
        return null
    }

    public List getAuthorizedEmployeeInfo() {
        try {
            return primaryDemandOrderService.getAuthorizedEmployeeInfo()
        } catch (Exception ex) {
            log.error(ex.message)
            return new RuntimeException(ex.message)
        }
    }
}
