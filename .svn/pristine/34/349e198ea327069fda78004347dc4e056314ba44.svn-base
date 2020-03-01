package com.bits.bdfp.setup.salestarget.saleshead

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.setup.salestarget.SalesHead
import com.bits.bdfp.setup.salestarget.SalesHeadService

@Component("deleteSalesHeadAction")
class DeleteSalesHeadAction extends Action {
    public static final Log log = LogFactory.getLog(DeleteSalesHeadAction.class)
    private final String MESSAGE_HEADER = 'Delete Sales Head'
    private final String MESSAGE_SUCCESS = 'Sales Head deleted successfully'
    private final String MESSAGE_FAILURE = 'Sales Head delete failed'

    @Autowired
    SalesHeadService salesHeadService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            SalesHead salesHead = salesHeadService.read(Long.parseLong(params.id))
            salesHeadService.delete(salesHead)
            return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, MESSAGE_FAILURE)
        }
    }

    public Object postCondition(def params, def object) {
        return null
    }
}