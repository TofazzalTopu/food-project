package com.bits.bdfp.accounts.mushak

import com.bits.bdfp.accounts.MushakService
import com.docu.common.Action
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 3/16/2016.
 */
@Component("listDistributionPointForMushakAction")
class ListDistributionPointForMushakAction extends Action {
    public static final Log log = LogFactory.getLog(ReadMushakAction.class)

    @Autowired
    MushakService mushakService

    Message message

    @Override
    public Object preCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    public Object postCondition(Object params, Object object) {
        return null
    }

    @Override
    public Object execute(Object params, Object Object) {
        try {
            List objectList = mushakService.fetchDp(params)
            return objectList
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Exception", Message.ERROR, ex.message)
        }
    }
}
