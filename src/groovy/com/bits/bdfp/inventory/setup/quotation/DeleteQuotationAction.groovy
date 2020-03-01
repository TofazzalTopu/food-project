package com.bits.bdfp.inventory.setup.quotation

import com.bits.bdfp.inventory.setup.QuotationDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.inventory.setup.Quotation
import com.bits.bdfp.inventory.setup.QuotationService

@Component("deleteQuotationAction")
class DeleteQuotationAction extends Action {
    public static final Log log = LogFactory.getLog(DeleteQuotationAction.class)
    private final String MESSAGE_HEADER = 'Delete Quotation Detail'
    private final String MESSAGE_SUCCESS = 'Quotation Detail Deleted Successfully.'
    private final String MESSAGE_FAILURE = 'Quotation Detail Delete Failed.'

    @Autowired
    QuotationService quotationService

    public Object preCondition(def params, def object) {
        return null
    }

    public Object execute(def params, def object) {
        try {
            QuotationDetails quotationDetails = QuotationDetails.read(Long.parseLong(params.id))
            quotationService.delete(quotationDetails)
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