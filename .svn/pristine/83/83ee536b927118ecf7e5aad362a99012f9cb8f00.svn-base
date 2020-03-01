package com.bits.bdfp.accounts.mushak

import com.bits.bdfp.accounts.MushakService
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by NZ on 3/14/2016.
 */
@Component("listInvoiceForMushakAction")
class ListInvoiceForMushakAction extends Action {

    public static final Log log = LogFactory.getLog(ReadMushakAction.class)

    @Autowired
    MushakService mushakService

    @Override
    public Object preCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    public Object postCondition(def Object object1,def Object object2) {
        return null
    }

    @Override
    public Object execute(Object object, Object params) {
        return mushakService.fetchInvoice(params)
    }
}
